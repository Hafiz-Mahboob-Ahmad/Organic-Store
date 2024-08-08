package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sa.organicStore.database.entities.CartModel

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProducts(cartProduct: CartModel)

    @Query("SELECT * FROM cart WHERE userId = :userId AND productId = :productId")
    suspend fun getCartProduct(userId: Int, productId: Int) : CartModel?

    @Query(
        "UPDATE cart SET quantity = :quantity " +
                "WHERE userId = :userId AND productId = :productId"
    )
    suspend fun updateCartProductQuantity(quantity: Int, userId: Int, productId: Int)



//    @Query(
//        "SELECT * FROM product " +
//                "JOIN cart ON  product.productId = cart.id")
//    suspend fun getCartProducts() : List<CartModel>
}