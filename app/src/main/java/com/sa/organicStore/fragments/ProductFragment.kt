package com.sa.organicStore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sa.organicStore.adapter.ProductAdapter
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.FragmentProductBinding
import com.sa.organicStore.model.ProductModel

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listType = object : TypeToken<ArrayList<ProductEntity>>() {}.type
        val productList: ArrayList<ProductEntity> = Gson().fromJson(args.productItemData, listType)

        setTitle()
        setupRecyclerView(productList)
        setupClickListeners()
    }

    private fun setTitle() {
        binding.tvTitle.text = args.title
    }

    private fun setupRecyclerView(productList: ArrayList<ProductEntity>) {
        productAdapter = ProductAdapter(productList, object : ProductAdapter.OnItemClickListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onSaveButtonClick(position: Int) {
              val product = productList[position].productId
                navigateToBundleDetailsFragment(product)
            }

            override fun onImageClick(position: Int) {
                val product = productList[position].productId
                navigateToBundleDetailsFragment(product)
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

}