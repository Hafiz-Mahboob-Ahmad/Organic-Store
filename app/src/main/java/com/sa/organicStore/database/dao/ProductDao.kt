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

    @Query("SELECT * FROM product " +
            "JOIN cart ON product.productId = cart.id AND product.quantityCounter = cart.quantity " +
            "WHERE cart.userId = :userId AND category = :category ")
    suspend fun getAllProducts(category: String, userId: Int): List<ProductEntity>

}
