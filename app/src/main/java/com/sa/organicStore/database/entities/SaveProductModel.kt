package com.sa.organicStore.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "save")
@Parcelize
data class SaveProductModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Long,
    val productId: Int
) : Parcelable
