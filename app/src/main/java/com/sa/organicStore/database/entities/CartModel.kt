package com.sa.organicStore.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart")
@Parcelize
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: Int,
    val name: String,
    val weight: Int,
    val price: Int,
    val quantity: Int
) : Parcelable
