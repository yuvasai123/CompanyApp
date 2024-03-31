package com.example.suryaapp.data.debitedlist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suryaapp.data.paymentlists.Payments

@Dao
interface DebitedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDebited(debited: Debited)

    @Update
    suspend fun updateDebited(debited: Debited)

//    @Delete
//    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM debited_table ORDER BY date ASC")
    fun readAllDebitedData(): LiveData<List<Debited>>


    @Query("SELECT * FROM debited_table WHERE customerName LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String?): LiveData<List<Debited>>
}
