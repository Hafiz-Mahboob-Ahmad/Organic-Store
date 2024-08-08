package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel

@Dao
interface SaveProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveProduct(saveProduct: SaveProductModel)

    @Query(
        "SELECT * FROM product " +
                "JOIN save ON product.productId = save.productId " +
                "WHERE save.userId = :userId"
    )
    suspend fun getProductsByUserId(userId: Int): List<ProductEntity>
}