package com.sa.organicStore.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "User")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    val name: String,
    val email: String,
    val password: String
) : Parcelable