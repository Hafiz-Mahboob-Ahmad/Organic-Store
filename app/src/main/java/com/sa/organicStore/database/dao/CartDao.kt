package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProducts(cartProduct: CartModel)

    @Query("SELECT * FROM cart WHERE userId = :userId AND productId = :productId")
    suspend fun isCartProductExists(userId: Int, productId: Int): CartModel?

//    @Query("SELECT * FROM cart WHERE userId = :userId AND productId = :productId")
//    suspend fun isCartProductExists(userId: Int, productId: Int): CartModel?


    @Query(
        "SELECT product.*, cart.quantity AS quantityCounter " +
                "FROM product " +
                "JOIN cart ON cart.productId = product.productId " +
                "WHERE product.productId= :productId AND cart.userId = :userId "
    )
    suspend fun getCartProductDetails(productId: Int, userId: Int): ProductEntity


    @Query(
        "SELECT product.*, cart.quantity AS quantityCounter " +
                "FROM product " +
                "JOIN cart ON product.productId = cart.productId " +
                "WHERE cart.userId = :userId "
    )
    suspend fun getCartProducts(userId: Int): List<ProductEntity>


    @Query(
        "UPDATE cart SET quantity = :quantity " +
                "WHERE userId = :userId AND productId = :productId"
    )
    suspend fun updateCartProductQuantity(quantity: Int, userId: Int, productId: Int)


    @Query(
        "DELETE FROM cart " +
                "WHERE userId = :userId AND productId = :productId"
    )
    suspend fun deleteCartProduct(userId: Int, productId: Int)
}



//    @Query(
//        "SELECT * FROM product " +
//                "JOIN cart ON  product.productId = cart.id")
//    suspend fun getCartProducts() : List<CartModel>