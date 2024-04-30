package com.example.suryaapp.data.previousData

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.R
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class BalanceAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<BalanceAdapter.ViewHolder>() {

    private var ordersList = emptyList<Previous>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date: TextView = itemView.findViewById(R.id.orderDate)
        val tmq: TextView = itemView.findViewById(R.id.tmq)
        val chq: TextView = itemView.findViewById(R.id.chq)
        val soq: TextView = itemView.findViewById(R.id.soq)
        val vgq: TextView = itemView.findViewById(R.id.vgq)
        val tm5q: TextView = itemView.findViewById(R.id.tm5q)
        val ch5q: TextView = itemView.findViewById(R.id.ch5q)
        val so5q: TextView = itemView.findViewById(R.id.so5q)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val save: Button = itemView.findViewById(R.id.save)
        val balanceamount: TextView = itemView.findViewById(R.id.balancaamount)
        val cardLayout: CardView = itemView.findViewById(R.id.cardLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pending_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = ordersList[position]

        holder.customerName.text = orderData.customerName
        holder.date.text = orderData.date
        holder.tmq.text = orderData.tmq
        holder.chq.text = orderData.chq
        holder.soq.text = orderData.soq
        holder.vgq.text = orderData.vgq
        holder.tm5q.text = orderData.tm5q
        holder.ch5q.text = orderData.ch5q
        holder.so5q.text = orderData.so5q
        holder.amount.text = orderData.amount
        holder.balanceamount.text = orderData.balance
        holder.save.setOnClickListener {

            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.pending_box_layout, null)

            val edited = dialogView.findViewById<EditText>(R.id.balanceEditText)
            val amountEdited = dialogView.findViewById<TextView>(R.id.amountEditText)
            val modeOfPayment = dialogView.findViewById<EditText>(R.id.modeofpayment)
            val description = dialogView.findViewById<EditText>(R.id.description)

            amountEdited.text = holder.amount.text

            val dateEdt = dialogView.findViewById<EditText>(R.id.idEdtDate)
            dateEdt.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    holder.itemView.context,
                    { _, year, monthOfYear, dayOfMonth ->
                        val dat = "$dayOfMonth-${monthOfYear + 1}-$year"
                        dateEdt.setText(dat)
                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.show()
            }

            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel",null)// Set positive button to null initially

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            // Override positive button behavior after dialog is shown
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
                val editedValue = edited.text.toString().toDoubleOrNull() ?: 0.0
                val balanceValue = orderData.balance.toDoubleOrNull() ?: 0.0
                val updatedBalance = (balanceValue - editedValue).toInt().toString()

                val updatedPending = Previous(
                    id = orderData.id,
                    customerName = orderData.customerName,
                    date = orderData.date,
                    tmq = orderData.tmq,
                    chq = orderData.chq,
                    soq = orderData.soq,
                    vgq = orderData.vgq,
                    tm5q = orderData.tm5q,
                    ch5q = orderData.ch5q,
                    so5q = orderData.so5q,
                    amount = orderData.amount,
                    balance = updatedBalance
                )

                if (edited.text.isEmpty() || dateEdt.text.isEmpty() || modeOfPayment.text.isEmpty()) {
                    showCustomToast(holder.itemView.context, "Please fill all fields")
                } else {
                    val payments = Payments(
                        id = System.currentTimeMillis().toInt(),
                        customerName = orderData.customerName,
                        date = dateEdt.text.toString(),
                        totalamount = edited.text.toString(),
                        cashtype = modeOfPayment.text.toString(),
                        paydescription = description.text.toString()
                    )

                    val paymentsViewModel = ViewModelProvider(viewModelStoreOwner)
                        .get(PaymentsViewModel::class.java)
                    val previousViewModel = ViewModelProvider(viewModelStoreOwner)
                        .get(PreviousViewModel::class.java)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            previousViewModel.updatePending(updatedPending)
                            paymentsViewModel.addPayment(payments)
                        } catch (e: Exception) {
                            Log.e("Error in Pending", "Error during transaction", e)
                        }
                    }

                    alertDialog.dismiss() // Dismiss dialog after all fields are filled
                }
            }
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setOnClickListener{
                alertDialog.dismiss()
            }
        }
    }

    fun setData(order: List<Previous>) {
        this.ordersList = order
        notifyDataSetChanged()
    }

    private fun showCustomToast(context: Context, message: String) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val textView: TextView = layout.findViewById(R.id.custom_toast_message)
        textView.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}
