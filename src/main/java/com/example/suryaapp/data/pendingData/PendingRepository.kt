package com.example.suryaapp.data.pendingData

import androidx.lifecycle.LiveData
import com.example.suryaapp.data.pendingData.Pending
import com.example.suryaapp.data.pendingData.PendingDao

class PendingRepository (private val pendingDao: PendingDao){

    val readAllPendingData:LiveData<List<Pending>> = pendingDao.readAllPendingData()

    suspend fun addPending(pending: Pending)
    {
        pendingDao.addPending(pending)
    }
//    suspend fun updateOrder(order: Order)
//    {
//        orderDao.updateOrder(order)
//    }
}