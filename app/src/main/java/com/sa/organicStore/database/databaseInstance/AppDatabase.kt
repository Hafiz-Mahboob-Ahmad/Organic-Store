package com.sa.organicStore.database.databaseInstance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sa.organicStore.constant.AppConstants
import com.sa.organicStore.constant.Constant
import com.sa.organicStore.database.converters.Converters
import com.sa.organicStore.database.dao.CartDao
import com.sa.organicStore.database.dao.ProductDao
import com.sa.organicStore.database.dao.SaveProductsDao
import com.sa.organicStore.database.dao.UserDAO
import com.sa.organicStore.database.entities.CartModel
import com.sa.organicStore.database.entities.ProductEntity
import com.sa.organicStore.database.entities.SaveProductModel
import com.sa.organicStore.database.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        SaveProductModel::class,
        CartModel::class
    ],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDAO(): UserDAO
    abstract fun getProductDao(): ProductDao
    abstract fun getSaveProductDao(): SaveProductsDao
    abstract fun getCartDao(): CartDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun invoke(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "app_database"
        ).addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    invoke(context).getProductDao().insertProduct(getProducts())
                }
            }
        }).fallbackToDestructiveMigration().build()

        private fun getProducts(): ArrayList<ProductEntity> {
            val products: ArrayList<ProductEntity> = ArrayList()

            products.addAll(Constant.getOurNewItemData().onEach { it.category = AppConstants.NEW_ITEM })
            products.addAll(Constant.getPopularPackData().onEach { it.category = AppConstants.POPULAR_PRODUCTS })

            return products
        }
    }
}