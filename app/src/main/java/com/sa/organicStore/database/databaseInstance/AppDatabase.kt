package com.sa.organicStore.database.databaseInstance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sa.organicStore.database.converters.Converters
import com.sa.organicStore.database.dao.ProductDao
import com.sa.organicStore.database.dao.SaveProductsDao
import com.sa.organicStore.database.dao.UserDAO
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.database.entities.UserEntity

@Database(entities = [UserEntity::class, ProductEntity::class, SaveProductModel::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDAO(): UserDAO
    abstract fun getProductDao(): ProductDao
    abstract fun getSaveProductDao() : SaveProductsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration().build()
    }
}