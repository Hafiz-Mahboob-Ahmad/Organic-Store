package com.sa.organicStore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sa.organicStore.adapter.ProductAdapter
import com.sa.organicStore.databinding.FragmentProductBinding
import com.sa.organicStore.model.ProductModel

class ProductFragment : Fragment(), ProductAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProductBinding
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

        val listType = object : TypeToken<ArrayList<ProductModel>>() {}.type
        val productModelList: ArrayList<ProductModel> = Gson().fromJson(args.productItemData, listType)

        setTitle()
        setupRecyclerView(productModelList)
        setupClickListeners()
    }

    private fun setTitle() {
        binding.tvTitle.text = args.title
    }

    private fun setupRecyclerView(productModelList: ArrayList<ProductModel>) {
        binding.rvProductData.adapter = ProductAdapter(productModelList, this)
    }

    private fun setupClickListeners() {
        binding.ivBackArrow.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onItemClick(pack: ProductModel) {
        navigateToBundleDetailsFragment(pack)
    }

    private fun navigateToBundleDetailsFragment(pack: ProductModel) {
        val json: String = Gson().toJson(pack)
        val action = ProductFragmentDirections.actionProductFragmentToBundleDetailsFragment(packItemData = json)
        findNavController().navigate(action)
    }

}