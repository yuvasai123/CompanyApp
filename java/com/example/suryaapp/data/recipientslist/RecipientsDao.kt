package com.example.suryaapp.data.recipientslist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.previousData.Previous

@Dao
interface RecipientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipient(recipients: Recipients)

    @Update
    suspend fun updateRecipients(recipients: Recipients)

    @Delete
    suspend fun deleteRecipients(recipients: Recipients)

    @Query("SELECT * FROM recipients_table ORDER BY date ASC")
    fun readAllRecipientsData(): LiveData<List<Recipients>>


    @Query("SELECT * FROM recipients_table WHERE customerName LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String?): LiveData<List<Recipients>>

    @Query("SELECT * FROM recipients_table WHERE customerName LIKE '%' || :searchQuery || '%' AND balance > 0")
    fun searchPendingWithPositiveBalance(searchQuery: String?): LiveData<List<Recipients>>

    @Query("SELECT * FROM recipients_table WHERE balance <> 0")
    fun readAllBalanceRecipientsData(): LiveData<List<Recipients>>
}
