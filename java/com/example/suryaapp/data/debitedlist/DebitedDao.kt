package com.example.suryaapp.data.debitedlist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    suspend fun deleteDebited(debited: Debited)

    @Query("SELECT * FROM debited_table ORDER BY date ASC")
    fun readAllDebitedData(): LiveData<List<Debited>>


    @Query("  SELECT * FROM debited_table \n" +
            "        WHERE customerName LIKE '%' || :searchQuery || '%' OR \n" +
            "              CAST(date AS TEXT) LIKE '%' || :searchQuery || '%' OR \n" +
            "              totalamount LIKE '%' || :searchQuery || '%'\n" +
            "        ORDER BY date ASC")
    fun searchDatabase(searchQuery: String?): LiveData<List<Debited>>
}
