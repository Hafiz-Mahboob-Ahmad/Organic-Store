package com.sa.organicStore.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SaveViewModel(application: Application) : AndroidViewModel(application) {

    private val _fetchSavedProductsWithDetails = MutableStateFlow<List<ProductEntity>>(emptyList())
    val fetchSavedProductsWithDetails: StateFlow<List<ProductEntity>> get() = _fetchSavedProductsWithDetails.asStateFlow()


    private val _fetchProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val fetchProducts: StateFlow<List<ProductEntity>> get() = _fetchProducts.asStateFlow()

    private val appDatabase = AppDatabase.invoke(application)

    init {
        fetchSavedProductsWithDetails()
        fetchProducts()
    }

    private fun fetchSavedProductsWithDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = appDatabase.getSaveProductDao().getSavedProductsWithDetails()
            _fetchSavedProductsWithDetails.value = products
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = appDatabase.getSaveProductDao().getProducts()
            _fetchProducts.value = products
        }
    }

    fun insertSaveProducts(product: SaveProductModel){
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.getSaveProductDao().insertSaveProduct(product)
            fetchSavedProductsWithDetails() //refresh the saved products list after insertion
        }
    }
}