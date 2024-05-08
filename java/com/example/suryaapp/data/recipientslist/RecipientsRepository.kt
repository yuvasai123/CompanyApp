package com.example.suryaapp.data.recipientslist

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsDao
import com.example.suryaapp.data.previousData.Previous

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
        suspend fun deleteRecipients(recipients: Recipients)
    {
        recipientsDao.deleteRecipients(recipients)
    }
    fun searchDatabase(searchQuery:String): LiveData<List<Recipients>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return recipientsDao.searchDatabase(searchQuery)
    }
    fun searchPendingWithPositiveBalance(searchQuery:String):LiveData<List<Recipients>>
    {
        return recipientsDao.searchPendingWithPositiveBalance(searchQuery)
    }


}