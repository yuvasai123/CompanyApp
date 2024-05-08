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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.PendingRecipientsFragmentDirections
import com.example.suryaapp.R
import com.example.suryaapp.RecipientsListFragmentDirections
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.data.previousData.Previous
import com.example.suryaapp.data.previousData.PreviousViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


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
        val cardLayout: CardView = itemView.findViewById(R.id.cardLayout)

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
                            val deletedOrder = recipientsList[position]

                            // Remove the order from the list
                            recipientsList =
                                recipientsList.filterIndexed { index, _ -> index != position }

                            // Notify the adapter about the removal
                            notifyItemRemoved(position)

                            // Delete the order from the database
                            val recipientsViewModel: RecipientsViewModel =
                                ViewModelProvider(viewModelStoreOwner).get(
                                    RecipientsViewModel::class.java
                                )
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    recipientsViewModel.deleteRecipients(deletedOrder)
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
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipients_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = recipientsList[position]

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = sdf.format(Date(orderData.date))
        val dates = sdf.parse(dateString)?.time ?: 0L
        holder.customerName.text = orderData.customerName
        holder.date.text = dateString
        holder.totalamount.text = orderData.totalamount
        holder.billNo.text = orderData.billNo
        holder.paydescription.text = orderData.description
        holder.balance.text = orderData.balance


        holder.cardLayout.setOnClickListener {
            val action =
                PendingRecipientsFragmentDirections.actionPendingRecipientsFragmentToRecipientsUpdateFragment(
                    orderData
                )
            holder.itemView.findNavController().navigate(action)
        }

    }
        // Set other TextViews with corresponding data fields


        override fun getItemCount(): Int {
            return recipientsList.size
        }

        fun setData(order: List<Recipients>) {
            this.recipientsList = order
            notifyDataSetChanged()
        }

}

