package com.example.suryaapp.data.expenditurelist

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.suryaapp.data.OrderDatabase
import com.example.suryaapp.data.ordersData.ItemsSum
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderDao

class ExpenditureRepository(private val expenditureDao: ExpenditureDao){

    val readAllData:LiveData<List<Expenditure>> = expenditureDao.readAllData()

    suspend fun addExpenditure(expenditure: Expenditure)
    {
        expenditureDao.addExpenditure(expenditure)
        Log.d("Insert", "Received query: $readAllData")
    }
    suspend fun updateExpenditure(expenditure: Expenditure)
    {
        expenditureDao.updateExpenditure(expenditure)
    }
    suspend fun deleteExpenditure(expenditure: Expenditure)
    {
        expenditureDao.deleteExpenditure(expenditure)
    }
    fun searchDatabase(searchQuery:String):LiveData<List<Expenditure>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return expenditureDao.searchDatabase(searchQuery)
    }

}