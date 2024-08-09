package com.sa.organicStore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.databinding.FragmentHomeBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.CartViewModel
import com.sa.organicStore.viewmodel.ProductViewModel
import com.sa.organicStore.viewmodel.SaveViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var popularPackAdapter: HomeAdapter
    private lateinit var newItemAdapter: HomeAdapter
    private var userId by Delegates.notNull<Int>()
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val saveViewModel: SaveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        userId = UserPrefs(requireContext()).getUser()!!.userId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchInitialProducts()
        collectStateFlows()
        setClickListeners()
    }

    private fun fetchInitialProducts() {
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        productViewModel.fetchInitialProducts(userId)
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            productViewModel.popularProducts.collect { productList ->
                setPopularPackRecyclerView(productList)
            }
        }

        lifecycleScope.launch {
            productViewModel.newProducts.collect { productList ->
                setOurNewItemRecyclerView(productList)
            }
        }
    }

    private fun setPopularPackRecyclerView(productList: List<ProductEntity>) {
        popularPackAdapter = HomeAdapter(ArrayList(productList), object : HomeAdapter.OnItemClickListener {
            override fun onSaveButtonClick(position: Int) {
                val productId = productList[position].productId
                saveProduct(productId)
            }

            override fun onImageClick(position: Int) {
                val productId = productList[position].productId
                navigateToBundleDetailsFragment(productId)
            }

            override fun onIncreaseQuantity(position: Int) {
                increaseQuantity(productList[position])
                popularPackAdapter.notifyItemChanged(position)
            }

            override fun onDecreaseQuantity(position: Int) {
                decreaseQuantity(productList[position])
                popularPackAdapter.notifyItemChanged(position)

            }
        })
        binding.rvPopularPack.adapter = popularPackAdapter
    }

    private fun setOurNewItemRecyclerView(productList: List<ProductEntity>) {
        newItemAdapter = HomeAdapter(ArrayList(productList), object : HomeAdapter.OnItemClickListener {
            override fun onSaveButtonClick(position: Int) {
                val productId = productList[position].productId
                saveProduct(productId)
            }

            override fun onImageClick(position: Int) {
                val product = productList[position].productId
                navigateToBundleDetailsFragment(product)
            }

            override fun onIncreaseQuantity(position: Int) {
                increaseQuantity(productList[position])
                newItemAdapter.notifyItemChanged(position)
            }

            override fun onDecreaseQuantity(position: Int) {
                decreaseQuantity(productList[position])
                newItemAdapter.notifyItemChanged(position)
            }
        })
        binding.rvOurNewItem.adapter = newItemAdapter
    }


    private fun insertCartProduct(product: ProductEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            val isCartProductExists: CartModel? =
                cartViewModel.isCartProductExists(userId, product.productId)
            if (isCartProductExists == null) {
                val cartModel = CartModel(userId = userId, productId = product.productId, quantity = product.quantityCounter)
                cartViewModel.insertCartProducts(cartModel)
            } else {
                cartViewModel.updateCartProduct(product.quantityCounter, userId, product.productId)
            }
        }
    }


    private fun increaseQuantity(product: ProductEntity) {
        product.quantityCounter++
        insertCartProduct(product)
        //updateQuantityUI()
    }

    private fun decreaseQuantity(product: ProductEntity) {
        if (product.quantityCounter > 0) {
            product.quantityCounter--
            if (product.quantityCounter > 0) {
                insertCartProduct(product)
            } else {
                cartViewModel.deleteCartProduct(userId, product.productId)
            }

        }
    }


    private fun saveProduct(productId: Int) {
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        val saveProduct = SaveProductModel(userId = userId, productId = productId)
        saveViewModel.insertSaveProducts(saveProduct)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
    }

    private fun setClickListeners() {
        binding.tvViewAllPopularPack.setOnClickListener {
            navigateToProductFragment( "Popular Pack")
        }

        binding.tvViewAllOurNewItem.setOnClickListener {
            navigateToProductFragment( "New Item")
        }
    }

    private fun navigateToProductFragment(title: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(title)
        findNavController().navigate(action)
    }

    private fun navigateToBundleDetailsFragment(productId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToBundleDetailsFragment(productId = productId)
        findNavController().navigate(action)
    }
}