package com.sa.organicStore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sa.organicStore.constant.AppConstants
import com.sa.organicStore.constant.Constant
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val _popularProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val popularProducts: StateFlow<List<ProductEntity>> get() = _popularProducts.asStateFlow()


    private val _newProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val newProducts: StateFlow<List<ProductEntity>> get() = _newProducts.asStateFlow()

    private val appDatabase = AppDatabase.invoke(application)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            insertInitialProducts()
            fetchInitialProducts()
        }
    }

    private suspend fun insertInitialProducts() {
        val existingProducts = appDatabase.getProductDao().getAllProducts()
        if (existingProducts.isNullOrEmpty()) {
            val popularPackList = Constant.getPopularPackData().onEach { it.category = AppConstants.POPULAR_PRODUCTS }
            val newItemList = Constant.getOurNewItemData().onEach { it.category = AppConstants.NEW_ITEM }
            appDatabase.getProductDao().insertProduct(popularPackList)
            appDatabase.getProductDao().insertProduct(newItemList)
        }
    }

    private fun fetchInitialProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val popularProductsList = appDatabase.getProductDao().getAllProducts(AppConstants.POPULAR_PRODUCTS)
            val newProductsList = appDatabase.getProductDao().getAllProducts(AppConstants.NEW_ITEM)
            _popularProducts.value = popularProductsList
            _newProducts.value = newProductsList
        }
    }
}