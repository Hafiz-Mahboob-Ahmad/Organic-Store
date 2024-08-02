package com.sa.organicStore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.sa.organicStore.R
import com.sa.organicStore.adapter.ProductImagesAdapter
import com.sa.organicStore.databinding.FragmentBundleDetailsBinding
import com.sa.organicStore.model.ProductModel

class BundleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBundleDetailsBinding
    private val args: BundleDetailsFragmentArgs by navArgs()
    private var quantityCounter: Int = 0

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
        bindPackViews(Gson().fromJson(args.packItemData, ProductModel::class.java))
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
    }

    private fun increaseCounter() {
        quantityCounter++
        updateCounter()
    }

    private fun decreaseCounter() {
        if (quantityCounter > 0) {
            quantityCounter--
        }
        updateCounter()
    }

    private fun updateCounter() {
        binding.tvQuantityCounter.text = quantityCounter.toString()
    }

    private fun navigateToCartFragment() {
        val action = BundleDetailsFragmentDirections.actionBundleDetailsFragmentToCartFragment()
        findNavController().navigate(action)
    }

    private fun bindPackViews(itemData: ProductModel) {
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