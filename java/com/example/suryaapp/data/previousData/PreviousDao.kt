package com.example.suryaapp.data.previousData


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update



@Dao
interface PreviousDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPending(pending: Previous)

    @Update
    suspend fun updatePending(pending: Previous)

    @Delete
    suspend fun deletePending(pending: Previous)

    @Query("SELECT * FROM previous_table ORDER BY date DESC")
    fun readAllPendingData(): LiveData<List<Previous>>


    @Query("SELECT * FROM previous_table WHERE customerName LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String?): LiveData<List<Previous>>

    @Query("SELECT * FROM previous_table WHERE balance <> 0 ORDER BY date DESC")
    fun readAllBalanceData(): LiveData<List<Previous>>

    @Query("SELECT * FROM previous_table WHERE customerName LIKE '%' || :searchQuery || '%' AND balance > 0")
    fun searchPendingWithPositiveBalance(searchQuery: String?): LiveData<List<Previous>>

}