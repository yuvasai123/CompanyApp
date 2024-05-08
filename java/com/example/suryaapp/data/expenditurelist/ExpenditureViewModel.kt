package com.example.suryaapp.data.expenditurelist

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

class ExpenditureViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Expenditure>>

    private val repository: ExpenditureRepository

    init {
        val expenditureDao = OrderDatabase.getDatabase(application).ExpenditureDao()
        repository = ExpenditureRepository(expenditureDao)
        readAllData = repository.readAllData


    }

    fun addExpenditure(expenditure: Expenditure) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExpenditure(expenditure)
        }
    }
    fun updateExpenditure(expenditure: Expenditure) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExpenditure(expenditure)
        }
    }
    fun deleteExpenditure(expenditure: Expenditure) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExpenditure(expenditure)
        }
    }
    fun searchDatabase(searchQuery:String):LiveData<List<Expenditure>>
    {
        Log.d("OrderViewModel", "searchDatabase query: $searchQuery")
        return repository.searchDatabase(searchQuery)
    }
}
