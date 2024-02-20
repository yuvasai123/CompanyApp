package com.example.suryaapp.data.previousOrders

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suryaapp.data.previousOrders.Previous


@Dao
interface PreviousDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPreviousOrder(previous: Previous)

}