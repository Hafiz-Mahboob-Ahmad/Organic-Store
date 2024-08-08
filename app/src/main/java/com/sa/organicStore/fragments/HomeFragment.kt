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
import com.google.gson.Gson
import com.sa.organicStore.adapter.HomeAdapter
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.databinding.FragmentHomeBinding
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.ProductViewModel
import com.sa.organicStore.viewmodel.SaveViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var popularPackAdapter: HomeAdapter
    private lateinit var newItemAdapter: HomeAdapter
    private val productViewModel: ProductViewModel by viewModels()
    private val saveViewModel: SaveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUserId()
        collectStateFlows()
        setClickListeners()
    }

    private fun fetchUserId() {
            val userEmail = UserPrefs(requireContext()).getUser()!!.email
            productViewModel.getUserId(userEmail)
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            productViewModel.popularProducts.collect { productList ->
                setPopularPackRecyclerView(productList)
            }
        }

        lifecycleScope.launch {
            productViewModel.newProducts.collect { productList ->
                setOurNewItemRecyclerView(productList)
            }
        }
    }

    private fun setPopularPackRecyclerView(productList: List<ProductEntity>) {
        popularPackAdapter = HomeAdapter(ArrayList(productList), object : HomeAdapter.OnItemClickListener {
            override fun onSaveButtonClick(position: Int) {
                val productId = productList[position].productId
                saveProduct(productId)
            }

            override fun onImageClick(position: Int) {
                val product = productList[position]
                navigateToBundleDetailsFragment(product)
            }
        })
        binding.rvPopularPack.adapter = popularPackAdapter
    }

    private fun setOurNewItemRecyclerView(productList: List<ProductEntity>) {
        newItemAdapter = HomeAdapter(ArrayList(productList), object : HomeAdapter.OnItemClickListener {
            override fun onSaveButtonClick(position: Int) {
                val productId = productList[position].productId
                saveProduct(productId)
            }

            override fun onImageClick(position: Int) {
                val product = productList[position]
                navigateToBundleDetailsFragment(product)
            }
        })
        binding.rvOurNewItem.adapter = newItemAdapter
    }

    private fun setClickListeners() {
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

    private fun navigateToBundleDetailsFragment(pack: ProductEntity) {
        val json: String = Gson().toJson(pack)
        val action = HomeFragmentDirections.actionHomeFragmentToBundleDetailsFragment(packItemData = json)
        findNavController().navigate(action)
    }

    private fun saveProduct(productId: Int) {
        val userId = UserPrefs(requireContext()).getUser()!!.userId
        val saveProduct = SaveProductModel(userId = userId, productId = productId)
        saveViewModel.insertSaveProducts(saveProduct)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
    }
}