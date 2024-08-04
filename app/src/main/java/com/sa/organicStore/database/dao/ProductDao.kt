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

    @Query("SELECT * FROM product WHERE category = :category")
    suspend fun getAllProducts(category: String): List<ProductEntity>

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<ProductEntity>?

    @Query("SELECT * FROM product WHERE userEmail = :email")
    fun getProductsByUserEmail(email: String): List<ProductEntity>


}
