package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel

@Dao
interface SaveProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveProduct(saveProduct: SaveProductModel)

    @Query(
        "SELECT product.*, cart.quantity AS quantityCounter " +
                "FROM product " +
                "JOIN save ON product.productId = save.productId " +
                "LEFT JOIN cart ON product.productId = cart.id AND cart.userId = :userId " +
                "WHERE save.userId = :userId"
    )
    suspend fun getSavedProductsByUserId(userId: Int): List<ProductEntity>
}