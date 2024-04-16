package com.example.suryaapp.data.recipientslist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.R
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.data.previousData.Previous
import com.example.suryaapp.data.previousData.PreviousViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar


class RecipientsAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<RecipientsAdapter.ViewHolder>() {

    private var recipientsList = emptyList<Recipients>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date: TextView = itemView.findViewById(R.id.orderDate)
        val totalamount: TextView = itemView.findViewById(R.id.totalamount)
        val billNo: TextView = itemView.findViewById(R.id.cashtype)
        val paydescription: TextView = itemView.findViewById(R.id.paydescription)
        val balance: TextView = itemView.findViewById(R.id.balancetxt)
        val button: Button = itemView.findViewById(R.id.save)
        val cardLayout: CardView = itemView.findViewById(R.id.cardLayout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipient_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = recipientsList[position]


        holder.customerName.text = orderData.customerName
        holder.date.text = orderData.date
        holder.totalamount.text = orderData.totalamount
        holder.billNo.text = orderData.billNo
        holder.paydescription.text = orderData.description
        holder.balance.text = orderData.balance


    }

//        holder.cardLayout.setOnClickListener{
//            val action = OrderListFragmentDirections.actionOrderListFragment2ToUpdateFragment(orderData)
//            holder.itemView.findNavController().navigate(action)
//        }


    // Set other TextViews with corresponding data fields


    override fun getItemCount(): Int {
        return recipientsList.size
    }

    fun setData(order: List<Recipients>) {
        this.recipientsList = order
        notifyDataSetChanged()
    }
}

