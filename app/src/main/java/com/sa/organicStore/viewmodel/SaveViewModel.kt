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
import kotlinx.coroutines.withContext

class SaveViewModel(application: Application) : AndroidViewModel(application) {

    private val appDatabase = AppDatabase.invoke(application)

    private val _fetchSavedProductsByUserId = MutableStateFlow<List<ProductEntity>>(emptyList())
    val fetchSavedProductsByUserId: StateFlow<List<ProductEntity>> get() = _fetchSavedProductsByUserId.asStateFlow()

    fun insertSaveProducts(product: SaveProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.getSaveProductDao().insertSaveProduct(product)
        }
    }

    fun fetchSavedProductsByUserId(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = appDatabase.getSaveProductDao().getSavedProductsByUserId(userId)
            _fetchSavedProductsByUserId.value = products
        }
    }
}