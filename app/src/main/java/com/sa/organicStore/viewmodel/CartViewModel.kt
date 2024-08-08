package com.sa.organicStore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.CartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(Application()) {

    private val appDatabase = AppDatabase.invoke(application)

    fun insertCartProducts(cartProduct: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.getCartDao().insertCartProducts(cartProduct)
        }
    }

    suspend fun getCartProduct(userId: Int, productId: Int): CartModel? {
        return appDatabase.getCartDao().getCartProduct(userId, productId)
    }

    suspend fun updateCartProduct(quantity: Int, userId: Int, productId: Int) {
        appDatabase.getCartDao().updateCartProductQuantity(quantity, userId, productId)
    }
}