package com.example.suryaapp.data.previousData

import android.util.Log
import androidx.lifecycle.LiveData

class PreviousRepository (private val pendingDao: PreviousDao){

    val readAllPendingData:LiveData<List<Previous>> = pendingDao.readAllPendingData()
    val readAllBalanceData:LiveData<List<Previous>> = pendingDao.readAllBalanceData()

    suspend fun addPending(pending: Previous)
    {
        pendingDao.addPending(pending)
    }
    suspend fun deletePending(pending: Previous)
    {
        pendingDao.deletePending(pending)
    }
    fun searchDatabase(searchQuery:String):LiveData<List<Previous>>
    {
        Log.d("OrderRepo", "searchDatabase query: $searchQuery")
        return pendingDao.searchDatabase(searchQuery)
    }
    suspend fun updatePending(pending: Previous)
    {
        pendingDao.updatePending(pending)
    }
  fun searchPendingWithPositiveBalance(searchQuery:String):LiveData<List<Previous>>
    {
        return pendingDao.searchPendingWithPositiveBalance(searchQuery)
    }

//
}