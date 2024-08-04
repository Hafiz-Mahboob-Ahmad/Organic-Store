package com.sa.organicStore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.databinding.FragmentHomeBinding
import com.sa.organicStore.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var popularPackAdapter: HomeAdapter
    private lateinit var newItemAdapter: HomeAdapter
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStateFlows()
        setupClickListeners()
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            productViewModel.popularProducts.collect { productList ->
                setupPopularPackRecyclerView(productList)
            }
        }

        lifecycleScope.launch {
            productViewModel.newProducts.collect { productList ->
                setupOurNewItemRecyclerView(productList)
            }
        }
    }

    private fun setupPopularPackRecyclerView(productList: List<ProductEntity>) {
        popularPackAdapter = HomeAdapter(ArrayList(productList), this)
        binding.rvPopularPack.adapter = popularPackAdapter
    }

    private fun setupOurNewItemRecyclerView(productList: List<ProductEntity>) {
        newItemAdapter = HomeAdapter(ArrayList(productList), this)
        binding.rvOurNewItem.adapter = newItemAdapter
    }

    private fun setupClickListeners() {
        binding.tvViewAllPopularPack.setOnClickListener {
            val productList = productViewModel.popularProducts.value
            navigateToProductFragment(ArrayList(productList), "Popular Pack")
        }

        binding.tvViewAllOurNewItem.setOnClickListener {
            val productList = productViewModel.newProducts.value
            navigateToProductFragment(ArrayList(productList), "New Item")
        }
    }

    private fun navigateToProductFragment(dataList: ArrayList<ProductEntity>, title: String) {
        if (dataList.isNotEmpty()) {
            val json: String = Gson().toJson(dataList)
            val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(json, title)
            findNavController().navigate(action)
        }
    }

    override fun onItemClick(pack: ProductEntity) {
        navigateToBundleDetailsFragment(pack)
    }

    private fun navigateToBundleDetailsFragment(pack: ProductEntity) {
        val json: String = Gson().toJson(pack)
        val action = HomeFragmentDirections.actionHomeFragmentToBundleDetailsFragment(packItemData = json)
        findNavController().navigate(action)
    }
}
