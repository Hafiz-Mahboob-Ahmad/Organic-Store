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

    private val appDatabase = AppDatabase.invoke(application)


    private val _popularProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val popularProducts: StateFlow<List<ProductEntity>> get() = _popularProducts.asStateFlow()


    private val _newProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val newProducts: StateFlow<List<ProductEntity>> get() = _newProducts.asStateFlow()


    val defaultProductEntity = ProductEntity(
        productId = 0,             // Default value for productId
        image = ArrayList(),       // Default empty list for image
        name = "",                 // Default empty string for name
        ingredients = "",          // Default empty string for ingredients
        offerPrice = 0,            // Default value for offerPrice
        actualPrice = 0,           // Default value for actualPrice
        description = "",          // Default empty string for description
        weight = 0,                // Default value for weight
        weightUnit = "Kg",         // Default value for weightUnit
        quantityCounter = 0,       // Default value for quantityCounter
        category = "",             // Default empty string for category
        userEmail = null           // Default null for userEmail
    )


    private val _defaultProductDetails = MutableStateFlow<ProductEntity>(defaultProductEntity)
    val defaultProductDetails: StateFlow<ProductEntity> get() = _defaultProductDetails



    // HomeFragment products
    fun fetchInitialProducts(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val popularProductsList = appDatabase.getProductDao().getAllProducts(AppConstants.POPULAR_PRODUCTS, userId)
            val newProductsList = appDatabase.getProductDao().getAllProducts(AppConstants.NEW_ITEM, userId)
            _popularProducts.value = popularProductsList
            _newProducts.value = newProductsList
        }
    }


    // Default product for BundleDetailsFragment
    fun getDefaultProductDetails(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _defaultProductDetails.value = appDatabase.getProductDao().getDefaultProductDetails(productId)
        }
    }



}