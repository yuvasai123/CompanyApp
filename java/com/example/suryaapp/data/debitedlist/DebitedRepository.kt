package com.example.suryaapp.data.debitedlist

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsDao

class DebitedRepository(private val debitedDao: DebitedDao){

    val readAllDebitedData: LiveData<List<Debited>> = debitedDao.readAllDebitedData()

    suspend fun addDebited(debited: Debited)
    {
        debitedDao.addDebited(debited)

    }
    suspend fun updateDebited(debited: Debited)
    {
        debitedDao.updateDebited(debited)
    }
    //    suspend fun deleteOrder(order: Order)
//    {
//        orderDao.deleteOrder(order)
//    }
    fun searchDatabase(searchQuery:String): LiveData<List<Debited>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return debitedDao.searchDatabase(searchQuery)
    }

}