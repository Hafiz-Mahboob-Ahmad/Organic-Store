package com.sa.organicStore.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sa.organicStore.database.converters.Converters
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product")
@Parcelize
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val int: Int = 0,
    @TypeConverters(Converters::class)
    val image: ArrayList<Int>,
    val name: String,
    val ingredients: String,
    val offerPrice: Int,
    val actualPrice: Int,
    val description: String,
    val weight: Int,
    val weightUnit: String = "Kg",
    var quantityCounter: Int = 0,
    var category: String = ""
) : Parcelable
