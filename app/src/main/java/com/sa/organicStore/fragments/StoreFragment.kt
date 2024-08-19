package com.sa.organicStore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.databinding.FragmentStoreBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.CartViewModel
import com.sa.organicStore.viewmodel.ProductViewModel
import com.sa.organicStore.viewmodel.SaveViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    private val productViewModel: ProductViewModel by viewModels()

    private val cartViewModel: CartViewModel by viewModels()

    private val saveViewModel: SaveViewModel by viewModels()

    private lateinit var adapter: HomeAdapter

    private var userId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        userId = UserPrefs(requireContext()).getUser()!!.userId
        init()
        return binding.root
    }

    private fun init() {
        fetchProductData()
        collectData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBackArrow.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun fetchProductData() {
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        productViewModel.fetchAllProducts(userId)
    }

    private fun collectData() {
        lifecycleScope.launch {
            productViewModel.allProducts.collect { productList ->
                setAdapter(productList)
            }
        }
    }

    private fun setAdapter(productList: List<ProductEntity>) {
        adapter = HomeAdapter(ArrayList(productList), object : HomeAdapter.OnItemClickListener {
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
                adapter.notifyItemChanged(position)
            }

            override fun onDecreaseQuantity(position: Int) {
                decreaseQuantity(productList[position])
                adapter.notifyItemChanged(position)

            }
        })
        binding.rvProducts.adapter = adapter
    }

    private fun increaseQuantity(product: ProductEntity) {
        product.quantityCounter++
        insertCartProduct(product)
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


    private fun navigateToBundleDetailsFragment(productId: Int) {
        val action = StoreFragmentDirections.actionStoreFragmentToBundleDetailsFragment(productId = productId)
        findNavController().navigate(action)
    }

}