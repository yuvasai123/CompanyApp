package com.example.suryaapp.data.paymentlists

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.suryaapp.data.ordersData.ItemsSum
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderDao

class PaymentsRepository(private val paymentsDao: PaymentsDao){

    val readAllPaymentsData: LiveData<List<Payments>> = paymentsDao.readAllPaymentsData()

    suspend fun addPayment(payments: Payments)
    {
        paymentsDao.addPayment(payments)

    }
    suspend fun updatePayments(payments: Payments)
    {
        paymentsDao.updatePayments(payments)
    }
    suspend fun deletePayment(payments: Payments)
    {
        paymentsDao.deletePayment(payments)
    }
    fun searchDatabase(searchQuery:String): LiveData<List<Payments>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return paymentsDao.searchDatabase(searchQuery)
    }

}