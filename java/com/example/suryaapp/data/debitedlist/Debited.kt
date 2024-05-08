package com.example.suryaapp.data.debitedlist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "debited_table")
data class Debited(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val customerName: String,
    val date: Long,
    val totalamount: String,
    val cashtype:String,
    val paydescription:String

): Parcelable