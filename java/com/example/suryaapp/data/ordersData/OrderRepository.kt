package com.example.suryaapp.data.ordersData

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.suryaapp.data.OrderDatabase

class OrderRepository(private val orderDao: OrderDao){

    val readAllData:LiveData<List<Order>> = orderDao.readAllData()
    val sumAllData:LiveData<List<ItemsSum>> = orderDao.sumAllData()

    suspend fun addOrder(order: Order)
    {
        orderDao.addOrder(order)
        Log.d("Insert", "Received query: $readAllData")
    }
    suspend fun updateOrder(order: Order)
    {
        orderDao.updateOrder(order)
    }
    suspend fun deleteOrder(order: Order)
    {
        orderDao.deleteOrder(order)
    }
    fun searchDatabase(searchQuery:String):LiveData<List<Order>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return orderDao.searchDatabase(searchQuery)
    }

}