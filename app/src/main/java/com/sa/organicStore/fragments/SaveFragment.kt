package com.sa.organicStore.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sa.organicStore.R
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.databinding.FragmentSaveBinding
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.viewmodel.SaveViewModel

class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveBinding
    private lateinit var saveProductsAdapter: HomeAdapter
    private val saveViewModel: SaveViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectStateFlows()
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            saveViewModel.fetchProducts.collect {
                setRecyclerView(it)
            }
        }
    }

    private fun setRecyclerView(productList: List<ProductEntity>) {

        saveProductsAdapter = HomeAdapter(ArrayList(productList), object : HomeAdapter.OnItemClickListener {
            override fun onSaveButtonClick(position: Int) {

            }

            override fun onImageClick(position: Int) {
                val product = productList[position]
                navigateToBundleDetailsFragment(product)
            }
        })

        binding.rvSaveProductData.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaveProductData.adapter = saveProductsAdapter
    }

    private fun navigateToBundleDetailsFragment(pack: ProductEntity) {
        val json: String = Gson().toJson(pack)
        val action = HomeFragmentDirections.actionHomeFragmentToBundleDetailsFragment(packItemData = json)
        findNavController().navigate(action)
    }
}