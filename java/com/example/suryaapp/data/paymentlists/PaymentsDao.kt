package com.example.suryaapp.data.paymentlists

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suryaapp.data.ordersData.ItemsSum
import com.example.suryaapp.data.ordersData.Order

@Dao
interface PaymentsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPayment(payments: Payments)

    @Update
    suspend fun updatePayments(payments: Payments)

    @Delete
    suspend fun deletePayment(payments: Payments)

    @Query("SELECT * FROM payments_table ORDER BY date ASC")
    fun readAllPaymentsData(): LiveData<List<Payments>>


    @Query("SELECT * FROM payments_table WHERE customerName LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String?): LiveData<List<Payments>>
}
