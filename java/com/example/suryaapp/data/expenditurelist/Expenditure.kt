package com.example.suryaapp.data.expenditurelist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "expenditure_table")
data class Expenditure(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val customerName: String,
    val date: Long,
    val amount:String,
    val description: String
):Parcelable