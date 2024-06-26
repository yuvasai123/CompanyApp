package com.example.suryaapp.data.recipientslist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipients_table")
data class Recipients(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val customerName: String,
    val billNo:String,
    val date: Long,
    val totalamount: String,
    val description:String,
    val balance:String

): Parcelable