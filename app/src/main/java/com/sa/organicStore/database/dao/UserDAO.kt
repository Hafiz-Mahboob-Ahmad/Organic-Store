package com.sa.organicStore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sa.organicStore.database.entities.UserEntity

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(userEntity: UserEntity) :Long // Return the newly generated ID

    @Query("SELECT userId FROM user WHERE email = :userEmail LIMIT 1")
    suspend fun getUserId(userEmail: String) : Int

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE email= :email")
    suspend fun getUserProfile(email: String): UserEntity

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
}