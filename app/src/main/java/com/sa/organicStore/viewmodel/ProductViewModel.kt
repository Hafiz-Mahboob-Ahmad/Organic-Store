package com.sa.organicStore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sa.organicStore.constant.AppConstants
import com.sa.organicStore.constant.Constant
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.utils.UserPrefs
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
            fetchInitialProducts()
        }
    }


    private val _userId = MutableStateFlow(0)
    val userId: StateFlow<Int> get() = _userId.asStateFlow()

    fun getUserId(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = appDatabase.getUserDAO().getUserId(userEmail)
            _userId.value = userId
        }
    }

    private suspend fun fetchInitialProducts() {
        val popularProductsList = appDatabase.getProductDao().getAllProducts(AppConstants.POPULAR_PRODUCTS, userI)
        val newProductsList = appDatabase.getProductDao().getAllProducts(AppConstants.NEW_ITEM)
        _popularProducts.value = popularProductsList
        _newProducts.value = newProductsList
    }
}