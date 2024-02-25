package com.example.suryaapp.data.paymentlists

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.OrderListFragmentDirections
import com.example.suryaapp.R
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.previousData.Previous
import com.example.suryaapp.data.previousData.PreviousViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {

    private var paymentsList= emptyList<Payments>()




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date: TextView =itemView.findViewById(R.id.orderDate)
        val totalamount: TextView = itemView.findViewById(R.id.totalamount)
        val cashtype: TextView = itemView.findViewById(R.id.cashtype)
        val paydescription: TextView = itemView.findViewById(R.id.paydescription)

        val cardLayout : CardView =itemView.findViewById(R.id.cardLayout)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = paymentsList[position]


        holder.customerName.text = orderData.customerName
        holder.date.text=orderData.date
        holder.totalamount.text=orderData.totalamount
        holder.cashtype.text=orderData.cashtype
        holder.paydescription.text=orderData.paydescription


//        holder.cardLayout.setOnClickListener{
//            val action = OrderListFragmentDirections.actionOrderListFragment2ToUpdateFragment(orderData)
//            holder.itemView.findNavController().navigate(action)
//        }





        // Set other TextViews with corresponding data fields
    }

    override fun getItemCount(): Int {
        return paymentsList.size
    }
    fun setData(order:List<Payments>){
        this.paymentsList=order
        notifyDataSetChanged()
    }
}

