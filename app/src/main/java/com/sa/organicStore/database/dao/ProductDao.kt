package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sa.organicStore.database.entities.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<ProductEntity>)

    @Query("SELECT product.*, cart.quantity AS quantityCounter " +
            "FROM product " +
            "JOIN cart ON cart.productId = product.productId " +
            "WHERE product.productId= :productId AND cart.userId = :userId ")
    suspend fun getProduct(productId: Int, userId: Int) : ProductEntity?

//    @Query("SELECT * FROM product WHERE productId= :productId")
//    suspend fun getProduct(productId: Int) : ProductEntity
































    @Query(
        "SELECT product.*, cart.quantity AS quantityCounter " +
                "FROM product " +
                "LEFT JOIN cart ON product.productId = cart.productId AND cart.userId = :userId " +
                "WHERE category = :category "
    )
    suspend fun getAllProducts(category: String, userId: Int): List<ProductEntity>

}
