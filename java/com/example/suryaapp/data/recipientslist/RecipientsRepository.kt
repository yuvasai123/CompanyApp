package com.example.suryaapp.data.recipientslist

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsDao

class RecipientsRepository(private val recipientsDao: RecipientsDao){

    val readAllRecipientsData: LiveData<List<Recipients>> = recipientsDao.readAllRecipientsData()
    val readAllBalanceRecipientsData: LiveData<List<Recipients>> = recipientsDao.readAllBalanceRecipientsData()

    suspend fun addRecipient(recipients: Recipients)
    {
        recipientsDao.addRecipient(recipients)

    }
    suspend fun updateRecipients(recipients: Recipients)
    {
        recipientsDao.updateRecipients(recipients)
    }
    //    suspend fun deleteOrder(order: Order)
//    {
//        orderDao.deleteOrder(order)
//    }
    fun searchDatabase(searchQuery:String): LiveData<List<Recipients>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return recipientsDao.searchDatabase(searchQuery)
    }

}