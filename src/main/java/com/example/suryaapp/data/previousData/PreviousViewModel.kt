package com.example.suryaapp.data.previousData

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.suryaapp.data.OrderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreviousViewModel(application: Application): AndroidViewModel(application) {
    val readAllPendingData: LiveData<List<Previous>>
    val readAllBalanceData:LiveData<List<Previous>>
    private val repository: PreviousRepository

    init {
        val pendingDao = OrderDatabase.getDatabase(application).PendingDao()
        repository = PreviousRepository(pendingDao)
        readAllPendingData = repository.readAllPendingData
        readAllBalanceData = repository.readAllBalanceData
    }

    fun addPending(pending: Previous) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPending(pending)
        }
    }
    fun updatePending(pending: Previous) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePending(pending)
        }
    }
    fun searchDatabase(searchQuery:String):LiveData<List<Previous>>
    {
        Log.d("OrderViewModel", "searchDatabase query: $searchQuery")
        return repository.searchDatabase(searchQuery)
    }
//    fun updateOrder(order: Order) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateOrder(order)
//        }
//    }
}
