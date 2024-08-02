package com.sa.organicStore.database.loginSignup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
}