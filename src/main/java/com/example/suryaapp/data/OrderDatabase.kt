package com.example.suryaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderDao
import com.example.suryaapp.data.pendingData.Pending
import com.example.suryaapp.data.pendingData.PendingDao

@Database(entities = [Order::class,Pending::class], version = 1, exportSchema = true)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun OrderDao(): OrderDao
    abstract fun PendingDao(): PendingDao

    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getDatabase(context: Context): OrderDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "order_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Define your migration logic here
                // For example, you can create new tables, alter existing tables, etc.
            }
        }
    }
}
