package com.example.suryaapp.data.debitedlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.suryaapp.data.OrderDatabase
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DebitedViewModel(application: Application): AndroidViewModel(application) {
    val readAllDebitedData: LiveData<List<Debited>>

    private val repository: DebitedRepository

    init {
        val DebitedDao = OrderDatabase.getDatabase(application).DebitedDao()
        repository = DebitedRepository(DebitedDao)
        readAllDebitedData = repository.readAllDebitedData

    }

    fun addDebited(debited: Debited) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDebited(debited)
        }
    }
    fun updateDebited(debited: Debited) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDebited(debited)
        }
    }
        fun deleteDebited(debited: Debited) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDebited(debited)
        }
    }
    fun searchDatabase(searchQuery:String): LiveData<List<Debited>>
    {
        Log.d("OrderViewModel", "searchDatabase query: $searchQuery")
        return repository.searchDatabase(searchQuery)
    }
}

