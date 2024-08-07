package com.sa.organicStore.fragments

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.sa.organicStore.R
import com.sa.organicStore.adapter.ProductImagesAdapter
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.FragmentBundleDetailsBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.CartViewModel
import com.sa.organicStore.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class BundleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBundleDetailsBinding

    private var userId by Delegates.notNull<Int>()
    private var productId by Delegates.notNull<Int>()
    private var quantity: Int = 0

    private val args: BundleDetailsFragmentArgs by navArgs()

    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBundleDetailsBinding.inflate(inflater, container, false)
        userId = UserPrefs(requireContext()).getUser()!!.userId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArguments()
        setProductDetails()
        setClickListeners()
    }

    private fun setupArguments() {
        productId = args.productId
        Log.d("TAG", "fun setupArguments: productId = $productId")
    }

    private fun setProductDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isCartProductExists: CartModel? =
                cartViewModel.isCartProductExists(userId, productId)

            if (isCartProductExists == null) {
                withContext(Dispatchers.Main) {
                    setPackViews(productViewModel.getDefaultProductDetails(productId))
                }

            } else {
                val cartProduct: ProductEntity = cartViewModel.getCartProductDetails(productId, userId)
                withContext(Dispatchers.Main) {
                    setPackViews(cartProduct)
                    Log.d("Quantity", "Cart Quantity = ${cartProduct.quantityCounter}")
                }
            }

        }
    }

    private fun setClickListeners() {
        binding.ivDecreaseQuantity.setOnClickListener {
            decreaseQuantity()
        }
        binding.ivIncreaseQuantity.setOnClickListener {
            increaseQuantity()
        }
        binding.ivBackArrow.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnBuyNow.setOnClickListener {
            lifecycleScope.launch {
                if (isCartEmpty()) {
                    Toast.makeText(requireContext(), "Please add at least one product!", Toast.LENGTH_SHORT).show()
                } else {
                    navigateToCartFragment()
                }
            }
        }
    }

    private suspend fun isCartEmpty() : Boolean {
        return cartViewModel.isCartEmpty(userId)
    }

    private fun insertCartProduct() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isCartProductExists: CartModel? =
                cartViewModel.isCartProductExists(userId, productId)
            if (isCartProductExists == null) {
                val cartModel = CartModel(userId = userId, productId = productId, quantity = quantity)
                cartViewModel.insertCartProducts(cartModel)
            } else {
                cartViewModel.updateCartProduct(quantity, userId, productId)
            }
        }
    }


    private fun increaseQuantity() {
        quantity++
        insertCartProduct()
        updateQuantityUI()
    }

    private fun decreaseQuantity() {
        if (quantity > 0) {
            quantity--
            if (quantity > 0) {
                insertCartProduct()
            } else {
                cartViewModel.deleteCartProduct(userId, productId)
            }

        }
        updateQuantityUI()
    }

    private fun updateQuantityUI() {
        binding.tvQuantityCounter.text = quantity.toString()
    }

    private fun navigateToCartFragment() {
        val action = BundleDetailsFragmentDirections.actionBundleDetailsFragmentToCartFragment()
        findNavController().navigate(action)
    }

    private fun setPackViews(itemData: ProductEntity?) {
        setViewPager(itemData!!.image)
        binding.tvProductName.text = itemData.name
        binding.tvProductWeight.text = itemData.weight.toString()
        binding.tvProductWeightUnit.text = itemData.weightUnit
        binding.tvRegularPrice.text = "$" + itemData.actualPrice.toString()
        binding.tvRegularPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.tvOfferPrice.text = "$" + itemData.offerPrice.toString()
        binding.tvProductDetailsDescription.text = itemData.description
        binding.tvQuantityCounter.text = itemData.quantityCounter.toString()
        quantity = itemData.quantityCounter

    }

    private fun setViewPager(imageArray: ArrayList<Int>) {
        binding.vpProductImages.adapter = ProductImagesAdapter(imageArray)
        setDotsIndicator(imageArray)
        binding.vpProductImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })
    }

    private fun setDotsIndicator(imageArray: ArrayList<Int>) {
        val dots = arrayOfNulls<ImageView>(imageArray.size)
        binding.dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(requireContext()).apply {
                setImageResource(R.drawable.ic_inactive_dot)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(4.dpToPx(), 0, 4.dpToPx(), 0)
                }
                layoutParams = params
            }
            binding.dotsLayout.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[0]?.setImageResource(R.drawable.ic_active_dot)
        }
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

    private fun updateDots(position: Int) {
        val dotsCount = binding.dotsLayout.childCount
        for (i in 0 until dotsCount) {
            val imageView = binding.dotsLayout.getChildAt(i) as ImageView
            val drawableId = if (i == position) R.drawable.ic_active_dot else R.drawable.ic_inactive_dot
            imageView.setImageResource(drawableId)
        }
    }
}