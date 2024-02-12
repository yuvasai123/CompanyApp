package com.example.suryaapp.data.ordersData

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.suryaapp.data.OrderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData

class OrderViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Order>>
    val sumAllData:LiveData<List<ItemsSum>>
    private val repository: OrderRepository

    init {
        val orderDao = OrderDatabase.getDatabase(application).OrderDao()
        repository = OrderRepository(orderDao)
        readAllData = repository.readAllData

        sumAllData=repository.sumAllData
    }

    fun addUser(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
        }
    }
    fun updateOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateOrder(order)
        }
    }
    fun deleteOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOrder(order)
        }
    }
    fun searchDatabase(searchQuery:String):LiveData<List<Order>>
    {
        Log.d("OrderViewModel", "searchDatabase query: $searchQuery")
        return repository.searchDatabase(searchQuery)
    }
}


