package com.example.suryaapp.data.previousOrders


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "previous_table")
data class Previous(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val customerName: String,
    val date:String,
    val tmq: String,
    val chq: String,
    val soq: String,
    val vgq: String,
    val tm5q: String,
    val ch5q: String,
    val so5q:String,
    val amount :String
):Parcelable