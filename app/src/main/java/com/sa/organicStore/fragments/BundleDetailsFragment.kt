package com.sa.organicStore.fragments

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
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.FragmentBundleDetailsBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BundleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBundleDetailsBinding
    private lateinit var product: ProductEntity
    private val args: BundleDetailsFragmentArgs by navArgs()
    private val cartViewModel: CartViewModel by viewModels()
    private var quantity: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBundleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArguments()
        setupClickListeners()
    }

    private fun setupArguments() {
        product = Gson().fromJson(args.packItemData, ProductEntity::class.java)
        bindPackViews(product)
    }

    private fun setupClickListeners() {
        binding.ivDecreaseQuantity.setOnClickListener {
            decreaseCounter()
        }
        binding.ivIncreaseQuantity.setOnClickListener {
            increaseCounter()
        }
        binding.ivBackArrow.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnBuyNow.setOnClickListener {
            navigateToCartFragment()
        }
        binding.ivShoppingTrolley.setOnClickListener {
            insertProductIntoCart()
        }
    }

    private fun insertProductIntoCart() {
        val productId = product.productId
        Log.d("Bundle", "fun insertProductIntoCart : productId = $productId")
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        Log.d("Bundle", "fun insertProductIntoCart : userId = $userId")

        lifecycleScope.launch(Dispatchers.IO) {
            val isCartProductExists: CartModel? =
                cartViewModel.getCartProduct(userId = userId, productId = productId)
            if (isCartProductExists == null) {
                val cartModel = CartModel(
                    userId = userId,
                    productId = productId,
                    quantity = quantity
                )
                cartViewModel.insertCartProducts(cartModel)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Added to cart.", Toast.LENGTH_SHORT).show()
                }
            } else {
                cartViewModel.updateCartProduct(quantity, userId, productId)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Quantity Updated to $quantity.", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun increaseCounter() {
        quantity++
        updateCounter()
    }

    private fun decreaseCounter() {
        if (quantity > 0) {
            quantity--
        }
        updateCounter()
    }

    private fun updateCounter() {
        binding.tvQuantityCounter.text = quantity.toString()
    }

    private fun navigateToCartFragment() {
        val action = BundleDetailsFragmentDirections.actionBundleDetailsFragmentToCartFragment()
        findNavController().navigate(action)
    }

    private fun bindPackViews(itemData: ProductEntity) {
        setupViewPager(itemData.image)
        binding.tvProductName.text = itemData.name
        binding.tvProductWeight.text = itemData.weight.toString()
        binding.tvProductWeightUnit.text = itemData.weightUnit
        binding.tvRegularPrice.text = "$" + itemData.actualPrice.toString()
        binding.tvOfferPrice.text = "$" + itemData.offerPrice.toString()
        binding.tvProductDetailsDescription.text = itemData.description

    }

    private fun setupViewPager(imageArray: ArrayList<Int>) {
        binding.vpProductImages.adapter = ProductImagesAdapter(imageArray)
        setupDotsIndicator(imageArray)
        binding.vpProductImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })
    }

    private fun setupDotsIndicator(imageArray: ArrayList<Int>) {
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