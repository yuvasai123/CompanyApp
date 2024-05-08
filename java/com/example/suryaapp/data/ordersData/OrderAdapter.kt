package com.example.suryaapp.data.ordersData
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
import com.example.suryaapp.data.previousData.Previous
import com.example.suryaapp.data.previousData.PreviousViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val small:TextView = itemView.findViewById(R.id.small)
        val big:TextView = itemView.findViewById(R.id.big)
        val totalboxes:TextView = itemView.findViewById(R.id.totalboxes)
        val cardLayout : CardView=itemView.findViewById(R.id.cardLayout)


        init {
            // Set long click listener
            cardLayout.setOnLongClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = view.context

                    // Build confirmation dialog
                    val alertDialogBuilder = AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes") { dialog, _ ->
                            // Handle positive button click (delete item)
                            val deletedOrder = ordersList[position]

                            // Remove the order from the list
                            ordersList = ordersList.filterIndexed { index, _ -> index != position }

                            // Notify the adapter about the removal
                            notifyItemRemoved(position)

                            // Delete the order from the database
                            val orderViewModel: OrderViewModel = ViewModelProvider(viewModelStoreOwner).get(OrderViewModel::class.java)
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    orderViewModel.deleteOrder(deletedOrder)
                                } catch (e: Exception) {
                                    Log.e("Error in Order", "Error during deletion", e)
                                }
                            }

                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            // Handle negative button click (dismiss dialog)
                            dialog.dismiss()
                        }

                    // Create and show the dialog
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                true // Consumed the long click event
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = ordersList[position]
        val amountValue = orderData.amount

        val tmqInt = if (orderData.tmq.isNotEmpty()) orderData.tmq.toInt() else 0
        val chqInt = if (orderData.chq.isNotEmpty()) orderData.chq.toInt() else 0
        val soqInt = if (orderData.soq.isNotEmpty()) orderData.soq.toInt() else 0
        val vgqInt = if (orderData.vgq.isNotEmpty()) orderData.vgq.toInt() else 0
        val tm5qInt = if (orderData.tm5q.isNotEmpty()) orderData.tm5q.toInt() else 0
        val ch5qInt = if (orderData.ch5q.isNotEmpty()) orderData.ch5q.toInt() else 0
        val so5qInt = if (orderData.so5q.isNotEmpty()) orderData.so5q.toInt() else 0


        val smallsum = tmqInt + chqInt + soqInt + vgqInt
        val bigsum = tm5qInt + ch5qInt + so5qInt

        val totalsum = smallsum+(2*bigsum)
        // Set the sum to the small TextView

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = sdf.format(Date(orderData.date))
        val dates = sdf.parse(dateString)?.time ?: 0L
        val pending = Previous(
            id = 0, // ID will be auto-generated
            customerName = orderData.customerName,
            date = dates,
            tmq = orderData.tmq,
            chq = orderData.chq,
            soq = orderData.soq,
            vgq = orderData.vgq,
            tm5q = orderData.tm5q,
            ch5q = orderData.ch5q,
            so5q = orderData.so5q,
            amount = orderData.amount,
            balance = orderData.amount
        )


        holder.customerName.text = orderData.customerName
        holder.date.text=dateString
        holder.tmq.text = orderData.tmq
        holder.chq.text = orderData.chq
        holder.soq.text = orderData.soq
        holder.vgq.text = orderData.vgq
        holder.tm5q.text = orderData.tm5q
        holder.ch5q.text = orderData.ch5q
        holder.so5q.text = orderData.so5q
        holder.amount.text = orderData.amount
        holder.small.text = smallsum.toString()
        holder.big.text = bigsum.toString()
        holder.totalboxes.text = totalsum.toString()
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

// Parse the selected date string into a Date object
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateObject = sdf.parse(selectedDate)

// Convert the Date object to milliseconds since January 1, 1970, 00:00:00 GMT
                    val selectedDateTimeInMillis = dateObject?.time ?: 0L

                    // Do something with the selected date
                    val updatedPending = Previous(
                        id = pending.id, // Assuming id remains the same
                        customerName = pending.customerName,
                        date = selectedDateTimeInMillis, // Update date here
                        tmq = pending.tmq,
                        chq = pending.chq,
                        soq = pending.soq,
                        vgq = pending.vgq,
                        tm5q = pending.tm5q,
                        ch5q = pending.ch5q,
                        so5q = pending.so5q,
                        amount = pending.amount,
                        balance = pending.amount

                    )


                    val pendingViewModel:PreviousViewModel
                    pendingViewModel = ViewModelProvider(viewModelStoreOwner).get(PreviousViewModel::class.java)



                    val orderViewModel: OrderViewModel = ViewModelProvider(viewModelStoreOwner).get(OrderViewModel::class.java)

                    // Use a coroutine to perform the operations in a background thread
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Insert into Pending table
                            pendingViewModel.addPending(updatedPending)
                            Log.d("QueryCalled", " msg is $updatedPending")

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
