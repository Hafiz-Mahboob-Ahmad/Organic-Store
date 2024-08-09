package com.sa.organicStore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(Application()) {

    private val appDatabase = AppDatabase.invoke(application)

    private val _cartProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val cartProducts : StateFlow<List<ProductEntity>> get() = _cartProducts.asStateFlow()



    private val _cartProductDetails = MutableStateFlow<ProductEntity?>(null)
    val cartProductDetails : StateFlow<ProductEntity?> get() = _cartProductDetails



    private val _isCartProductExists = MutableStateFlow<CartModel?>(null)
    val isCartProductExists : StateFlow<CartModel?> get() = _isCartProductExists



    // Cart Product for BundleDetailsFragment
    fun getCartProductDetails(productId: Int, userId: Int) {
        viewModelScope.launch (Dispatchers.IO){
            _cartProductDetails.value = appDatabase.getCartDao().getCartProductDetails(productId, userId)
        }
    }



    fun insertCartProducts(cartProduct: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.getCartDao().insertCartProducts(cartProduct)
        }
    }


    fun isCartProductExists(userId: Int, productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isCartProductExists.value = appDatabase.getCartDao().isCartProductExists(userId, productId)
        }
    }


    fun getCartProducts(userId: Int) {
        viewModelScope.launch (Dispatchers.IO){
            _cartProducts.value = appDatabase.getCartDao().getCartProducts(userId)
        }
    }

    fun updateCartProduct(quantity: Int, userId: Int, productId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            appDatabase.getCartDao().updateCartProductQuantity(quantity, userId, productId)
        }
    }

    fun deleteCartProduct(userId: Int, productId: Int){
        viewModelScope.launch (Dispatchers.IO){
            appDatabase.getCartDao().deleteCartProduct(userId, productId)
        }
    }
}