package com.example.suryaapp.data.recipientslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.suryaapp.data.OrderDatabase
import com.example.suryaapp.data.previousData.Previous
import com.example.suryaapp.data.previousData.PreviousRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipientsViewModel(application: Application): AndroidViewModel(application) {
    val readAllRecipientsData: LiveData<List<Recipients>>

    private val repository: RecipientsRepository

    init {
        val recipientsDao = OrderDatabase.getDatabase(application).RecipientsDao()
        repository = RecipientsRepository(recipientsDao)
        readAllRecipientsData = repository.readAllRecipientsData

    }

    fun addRecipient(recipients: Recipients) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecipient(recipients)
        }
    }
    fun updateRecipients(recipients: Recipients) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipients(recipients)
        }
    }
    fun searchDatabase(searchQuery:String): LiveData<List<Recipients>>
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
