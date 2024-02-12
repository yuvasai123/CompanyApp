package com.example.suryaapp.data.ordersData
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.OrderListFragmentDirections
import com.example.suryaapp.R
import com.example.suryaapp.data.pendingData.Pending
import com.example.suryaapp.data.pendingData.PendingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var ordersList= emptyList<Order>()



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date:TextView=itemView.findViewById(R.id.orderDate)
        val tmq: TextView = itemView.findViewById(R.id.tmq)
        val chq: TextView = itemView.findViewById(R.id.chq)
        val soq: TextView = itemView.findViewById(R.id.soq)
        val vgq: TextView = itemView.findViewById(R.id.vgq)
        val tm5q: TextView = itemView.findViewById(R.id.tm5q)
        val ch5q: TextView = itemView.findViewById(R.id.ch5q)
        val so5q: TextView = itemView.findViewById(R.id.so5q)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val save : Button = itemView.findViewById(R.id.save)
        val cardLayout : CardView=itemView.findViewById(R.id.cardLayout)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = ordersList[position]

        val pending = Pending(
            id = 0, // ID will be auto-generated
            customerName = orderData.customerName,
            date = orderData.date,
            tmq = orderData.tmq,
            chq = orderData.chq,
            soq = orderData.soq,
            vgq = orderData.vgq,
            tm5q = orderData.tm5q,
            ch5q = orderData.ch5q,
            so5q = orderData.so5q,
            amount = orderData.amount
        )


        holder.customerName.text = orderData.customerName
        holder.date.text=orderData.date
        holder.tmq.text = orderData.tmq
        holder.chq.text = orderData.chq
        holder.soq.text = orderData.soq
        holder.vgq.text = orderData.vgq
        holder.tm5q.text = orderData.tm5q
        holder.ch5q.text = orderData.ch5q
        holder.so5q.text = orderData.so5q
        holder.amount.text = orderData.amount
        holder.cardLayout.setOnClickListener{
            val action = OrderListFragmentDirections.actionOrderListFragment2ToUpdateFragment(orderData)
            holder.itemView.findNavController().navigate(action)
        }
        holder.save.setOnClickListener{

            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.dialog_box_layout, null)

            val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)



            // Build AlertDialog
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ ->
                    // Handle "OK" button click
                    val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
                    // Do something with the selected date
                    val updatedPending = Pending(
                        id = pending.id, // Assuming id remains the same
                        customerName = pending.customerName,
                        date = selectedDate, // Update date here
                        tmq = pending.tmq,
                        chq = pending.chq,
                        soq = pending.soq,
                        vgq = pending.vgq,
                        tm5q = pending.tm5q,
                        ch5q = pending.ch5q,
                        so5q = pending.so5q,
                        amount = pending.amount
                    )


                    val pendingViewModel:PendingViewModel
                    pendingViewModel = ViewModelProvider(viewModelStoreOwner).get(PendingViewModel::class.java)



                    val orderViewModel: OrderViewModel = ViewModelProvider(viewModelStoreOwner).get(OrderViewModel::class.java)

                    // Use a coroutine to perform the operations in a background thread
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Insert into Pending table
                            pendingViewModel.addPending(updatedPending)

                            // Delete from Orders table
                            orderViewModel.deleteOrder(orderData)
                        } catch (e: Exception) {
                            Log.e("Error in Pending", "Error during transaction", e)
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    // Handle "Cancel" button click
                    // You can perform any action here or simply dismiss the dialog
                    dialog.dismiss()
                }

            // Create and show the AlertDialog
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()



        }




        // Set other TextViews with corresponding data fields
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
    fun setData(order:List<Order>){
        this.ordersList=order
        notifyDataSetChanged()
    }
}

