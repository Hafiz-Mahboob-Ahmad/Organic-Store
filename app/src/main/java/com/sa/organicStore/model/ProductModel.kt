package com.sa.organicStore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val image: ArrayList<Int>,
    val name: String,
    val ingredients: String,
    val offerPrice: Int,
    val actualPrice: Int,
    val description: String,
    val weight: Int,
    val weightUnit: String,
    var quantityCounter: Int
) : Parcelable
