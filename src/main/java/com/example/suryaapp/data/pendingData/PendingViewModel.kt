package com.example.suryaapp.data.pendingData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.suryaapp.data.OrderDatabase
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PendingViewModel(application: Application): AndroidViewModel(application) {
    val readAllPendingData: LiveData<List<Pending>>
    private val repository: PendingRepository

    init {
        val pendingDao = OrderDatabase.getDatabase(application).PendingDao()
        repository = PendingRepository(pendingDao)
        readAllPendingData = repository.readAllPendingData
    }

    fun addPending(pending: Pending) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPending(pending)
        }
    }
//    fun updateOrder(order: Order) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateOrder(order)
//        }
//    }
}
