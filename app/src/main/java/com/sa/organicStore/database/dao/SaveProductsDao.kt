package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel

@Dao
interface SaveProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveProduct(product: SaveProductModel)

    @Transaction
    @Query(
        "SELECT * FROM save " +
                "INNER JOIN product ON save.productId = product.id " +
                "INNER JOIN user ON save.userId = user.id"
    )
    suspend fun getSavedProductsWithDetails(): List<ProductEntity>

    @Query(
        "SELECT * FROM product " +
                "JOIN save ON product.id = save.productId "
    )
    suspend fun getProducts() : List<ProductEntity>

}