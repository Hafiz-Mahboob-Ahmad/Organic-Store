package com.sa.organicStore.database.cartPage

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "card")
@Parcelize
data class cartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productImage: Int,
    val name: String,
    val weight: Int,
    val weightUnit: String,
    val quantityCounter: Int
) : Parcelable
