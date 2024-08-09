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
import androidx.navigation.fragment.navArgs
import com.sa.organicStore.adapter.ProductAdapter
import com.sa.organicStore.constant.AppConstants
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.databinding.FragmentProductBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.CartViewModel
import com.sa.organicStore.viewmodel.ProductViewModel
import com.sa.organicStore.viewmodel.SaveViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val args: ProductFragmentArgs by navArgs()
    private var userId by Delegates.notNull<Int>()
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val saveViewModel: SaveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = UserPrefs(requireContext()).getUser()!!.userId

        setTitle()
        fetchInitialProducts()
        collectStateFlows()
        setupClickListeners()

    }

    private fun setTitle() {
        binding.tvTitle.text = args.title
    }

    private fun fetchInitialProducts() {
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        val category = if (args.title == "Popular Pack") {
            AppConstants.POPULAR_PRODUCTS
        } else {
            AppConstants.NEW_ITEM
        }
        productViewModel.fetchProductsByCategory(userId, category)
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            productViewModel.productsByCategory.collect { productList ->
                //setupRecyclerView(productList as ArrayList<ProductEntity>) generate error
                setupRecyclerView(ArrayList(productList))
            }
        }
    }

    private fun setupRecyclerView(productList: ArrayList<ProductEntity>) {
        productAdapter = ProductAdapter(productList, object : ProductAdapter.OnItemClickListener {
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
                productAdapter.notifyItemChanged(position)
            }

            override fun onDecreaseQuantity(position: Int) {
                decreaseQuantity(productList[position])
                productAdapter.notifyItemChanged(position)

            }
        })
        binding.rvProductData.adapter = productAdapter
    }

    private fun setupClickListeners() {
        binding.ivBackArrow.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }


    private fun navigateToBundleDetailsFragment(productId: Int) {
        val action = ProductFragmentDirections.actionProductFragmentToBundleDetailsFragment(productId = productId)
        findNavController().navigate(action)
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
}