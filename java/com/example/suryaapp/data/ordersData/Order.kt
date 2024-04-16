package com.example.suryaapp.data.ordersData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val customerName: String,

    val date: String,
    val tmq: String,
    val chq: String,
    val soq: String,
    val vgq: String,
    val tm5q: String,
    val ch5q: String,
    val so5q:String,
    val amount:String
):Parcelable