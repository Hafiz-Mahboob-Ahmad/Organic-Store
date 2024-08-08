package com.sa.organicStore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.databinding.FragmentSaveBinding
import com.sa.organicStore.database.entities.ProductEntity
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.SaveViewModel
import kotlinx.coroutines.Dispatchers

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

        fetchSavedProducts()
        collectStateFlows()
    }

    private fun fetchSavedProducts() {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = UserPrefs(requireContext()).getUser()
            saveViewModel.fetchSavedProducts(user!!.userId)
        }
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            saveViewModel.fetchSavedProducts.collect {
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
        val action = SaveFragmentDirections.actionSaveFragmentToBundleDetailsFragment(json)
        findNavController().navigate(action)
    }
}