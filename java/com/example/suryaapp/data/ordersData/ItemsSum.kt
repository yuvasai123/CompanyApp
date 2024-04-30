package com.example.suryaapp.data.ordersData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Parcelize

data class ItemsSum(

    val sumtmq: String? = "0",
    val sumchq: String? = "0",
    val sumsoq: String? = "0",
    val sumvgq: String? = "0",
    val sumtm5q: String? = "0",
    val sumch5q: String? = "0",
    val sumso5q:String? = "0",
):Parcelable