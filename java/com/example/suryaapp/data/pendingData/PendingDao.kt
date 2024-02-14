package com.example.suryaapp.data.pendingData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suryaapp.data.pendingData.Pending


@Dao
interface PendingDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPending(pending: Pending)

//    @Update
//    suspend fun updateOrder(pending: Pending)

    @Query("SELECT * FROM pending_table ORDER BY id ASC")
    fun readAllPendingData(): LiveData<List<Pending>>
}