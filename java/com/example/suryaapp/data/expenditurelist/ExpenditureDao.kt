package com.example.suryaapp.data.expenditurelist

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
interface ExpenditureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExpenditure(expenditure: Expenditure)

    @Update
    suspend fun updateExpenditure(expenditure: Expenditure)

    @Delete
    suspend fun deleteExpenditure(expenditure: Expenditure)

    @Query("SELECT * FROM expenditure_table ORDER BY date ASC")
    fun readAllData(): LiveData<List<Expenditure>>


    @Query("SELECT * FROM expenditure_table WHERE customerName LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String?): LiveData<List<Expenditure>>

}

