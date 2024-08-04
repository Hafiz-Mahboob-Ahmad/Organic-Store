package com.sa.organicStore.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveBinding
    private lateinit var saveProductsAdapter: HomeAdapter
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        setRecyclerView()
        fetchData()
    }

    private fun setRecyclerView() {
        saveProductsAdapter = HomeAdapter(ArrayList(), object : HomeAdapter.OnItemClickListener {
            override fun onSaveButtonClick(position: Int, adapter: HomeAdapter) {

            }

            override fun onImageClick(position: Int, adapter: HomeAdapter) {

            }
        })

        binding.rvSaveProductData.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaveProductData.adapter = saveProductsAdapter
    }

    private fun fetchData() {
        val userEmail = getUserEmail()
        lifecycleScope.launch {
            productViewModel.getProductsByUserEmail(userEmail).collect { products ->
                saveProductsAdapter.updateData(ArrayList(products))
            }
        }
    }

    private fun getUserEmail(): String {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_email", "") ?: ""
    }
}
