package com.example.suryaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.suryaapp.data.debitedlist.Debited
import com.example.suryaapp.data.debitedlist.DebitedDao
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderDao
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsDao
import com.example.suryaapp.data.previousData.Previous
import com.example.suryaapp.data.previousData.PreviousDao
import com.example.suryaapp.data.recipientslist.Recipients
import com.example.suryaapp.data.recipientslist.RecipientsDao
import java.io.File
import java.io.IOException

@Database(entities = [Order::class,Previous::class,Payments::class, Recipients::class ,Debited::class], version = 1, exportSchema = true)

abstract class OrderDatabase : RoomDatabase() {

    abstract fun OrderDao(): OrderDao
    abstract fun PendingDao(): PreviousDao
abstract fun RecipientsDao() : RecipientsDao
    abstract fun PaymentsDao(): PaymentsDao
    abstract fun DebitedDao(): DebitedDao



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

        // OrderDatabase.kt



        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Define your migration logic here
                // For example, you can create new tables, alter existing tables, etc.
            }
        }
    }
}
