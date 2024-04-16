package com.example.suryaapp.data.paymentlists

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.suryaapp.data.OrderDatabase
import com.example.suryaapp.data.ordersData.ItemsSum
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsViewModel(application: Application): AndroidViewModel(application) {
    val readAllPaymentsData: LiveData<List<Payments>>

    private val repository: PaymentsRepository

    init {
        val paymentsDao = OrderDatabase.getDatabase(application).PaymentsDao()
        repository = PaymentsRepository(paymentsDao)
        readAllPaymentsData = repository.readAllPaymentsData

    }

    fun addPayment(payments: Payments) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPayment(payments)
        }
    }
    fun updatePayment(payments: Payments) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePayments(payments)
        }
    }
//    fun deleteOrder(order: Order) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteOrder(order)
//        }
//    }
    fun searchDatabase(searchQuery:String): LiveData<List<Payments>>
    {
        Log.d("OrderViewModel", "searchDatabase query: $searchQuery")
        return repository.searchDatabase(searchQuery)
    }
}

