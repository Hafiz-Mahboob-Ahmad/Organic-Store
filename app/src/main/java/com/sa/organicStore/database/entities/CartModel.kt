package com.sa.organicStore.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart", indices = [Index(value = ["userId", "productId"], unique = true)])
@Parcelize
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,
    val userId: Int,
    val productId: Int,
    val quantity: Int
) : Parcelable
