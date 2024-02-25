package com.example.suryaapp.data.ordersData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order:Order)

    @Query("SELECT * FROM order_table ORDER BY date ASC")
    fun readAllData():LiveData<List<Order>>

    @Query("SELECT SUM(tmq) AS sumtmq, SUM(chq) AS sumchq, SUM(soq) AS sumsoq, SUM(vgq) AS sumvgq, SUM(tm5q) AS sumtm5q, SUM(ch5q) AS sumch5q, SUM(so5q) AS sumso5q FROM order_table")
    fun sumAllData():LiveData<List<ItemsSum>>

    @Query("SELECT * FROM order_table WHERE customerName LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String?): LiveData<List<Order>>




}