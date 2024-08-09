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
import com.sa.organicStore.adapter.ProductAdapter
import com.sa.organicStore.adapter.SavedAdapter
import com.sa.organicStore.utils.UserPrefs
import com.sa.organicStore.viewmodel.SaveViewModel
import kotlinx.coroutines.Dispatchers

class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveBinding
    private lateinit var savedAdapter: SavedAdapter
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
            saveViewModel.fetchSavedProductsByUserId(user!!.userId)
        }
    }

    private fun collectStateFlows() {
        lifecycleScope.launch {
            saveViewModel.fetchSavedProductsByUserId.collect {
                setRecyclerView(ArrayList(it))
            }
        }
    }

    private fun setRecyclerView(productList: ArrayList<ProductEntity>) {

        savedAdapter = SavedAdapter(ArrayList(productList), object : SavedAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val productId = productList[position].productId
                navigateToBundleDetailsFragment(productId)
            }

//            override fun onDeleteClick(position: Int) {
//                productList.removeAt(position)
//                savedAdapter.notifyItemRemoved(position)
//
//                if (productList.isEmpty()) {
//                    navigateToBackScreen()
//                }
//            }

        })
        binding.rvSaveProductData.adapter = savedAdapter
    }

    private fun navigateToBundleDetailsFragment(productId: Int) {
        val action = SaveFragmentDirections.actionSaveFragmentToBundleDetailsFragment(productId = productId)
        findNavController().navigate(action)
    }

    private fun navigateToBackScreen() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}