package com.sa.organicStore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.constant.Constant
import com.sa.organicStore.databinding.FragmentHomeBinding
import com.sa.organicStore.model.ProductModel

class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPopularPackRecyclerView()
        setupOurNewItemRecyclerView()
        setupClickListeners()
    }

    private fun setupPopularPackRecyclerView() {
        homeAdapter = HomeAdapter(Constant.getPopularPackData(), this)
        binding.rvPopularPack.adapter = homeAdapter
    }

    private fun setupOurNewItemRecyclerView() {
        homeAdapter = HomeAdapter(Constant.getOurNewItemData(), this)
        binding.rvOurNewItem.adapter = homeAdapter
    }

    private fun setupClickListeners() {
        binding.tvViewAllPopularPack.setOnClickListener {
            val popularPackData = Constant.getPopularPackData()
            navigateToProductFragment(popularPackData, "Popular Pack")
        }

        binding.tvViewAllOurNewItem.setOnClickListener {
            val ourNewItemData = Constant.getOurNewItemData()
            navigateToProductFragment(ourNewItemData, "New Item")
        }
    }

    private fun navigateToProductFragment(dataList: ArrayList<ProductModel>, title: String ) {
        val json: String = Gson().toJson(dataList)
        val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(json, title)
        findNavController().navigate(action)
    }

    override fun onItemClick(pack: ProductModel) {
        navigateToBundleDetailsFragment(pack)
    }

    private fun navigateToBundleDetailsFragment(pack: ProductModel) {
        val json: String = Gson().toJson(pack)
        val action = HomeFragmentDirections.actionHomeFragmentToBundleDetailsFragment(packItemData = json)
        findNavController().navigate(action)
    }
}