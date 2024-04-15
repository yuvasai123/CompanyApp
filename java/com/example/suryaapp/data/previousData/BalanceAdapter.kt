
package com.example.suryaapp.data.previousData
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
import androidx.core.content.ContentProviderCompat.requireContext
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

    private var ordersList= emptyList<Previous>()



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
        val balanceamount : TextView = itemView.findViewById(R.id.balancaamount)
        val cardLayout : CardView=itemView.findViewById(R.id.cardLayout)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pending_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = ordersList[position]


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
        holder.balanceamount.text = orderData.balance
        holder.save.setOnClickListener{

            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.pending_box_layout, null)


            val edited = dialogView.findViewById<EditText>(R.id.balanceEditText)
            val amountedited = dialogView.findViewById<TextView>(R.id.amountEditText)
            val modeofpayment = dialogView.findViewById<EditText>(R.id.modeofpayment)
            val description = dialogView.findViewById<EditText>(R.id.description)

            amountedited.text = holder.amount.text

            val dateEdt = dialogView.findViewById<EditText>(R.id.idEdtDate)
            dateEdt.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    holder.itemView.context,
                    { _, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        dateEdt.setText(dat)
                    },
                    year,
                    month,
                    day
                )


                datePickerDialog.show()
            }





            // Build AlertDialog
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ ->

                        val editedValue = edited.text.toString().toDoubleOrNull() ?: 0.0
                        val balanceValue = orderData.balance.toDoubleOrNull() ?: 0.0
                    val updatedBalance = (balanceValue - editedValue).toInt().toString()





                    val updatedPending = Previous(
                        id = orderData.id, // Assuming id remains the same
                        customerName = orderData.customerName,
                        date = orderData.date, // Update date here
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

                     fun generateUniqueId(): Int {
                        // Use current time as a base for the ID
                        return System.currentTimeMillis().toInt()
                    }

                    val payments = Payments(
                        id = generateUniqueId(),
                        customerName = orderData.customerName,
                        date = dateEdt.text.toString(),
                        totalamount = edited.text.toString(),
                        cashtype = modeofpayment.text.toString(),
                        paydescription = description.text.toString()
                    )
                    val paymentsViewModel:PaymentsViewModel
                    paymentsViewModel = ViewModelProvider(viewModelStoreOwner).get(PaymentsViewModel::class.java)
                    val previousViewModel: PreviousViewModel
                    previousViewModel = ViewModelProvider(viewModelStoreOwner).get(PreviousViewModel::class.java)





                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Insert into Pending table


                            previousViewModel.updatePending(updatedPending)
                            paymentsViewModel.addPayment(payments)
                            Log.d("Menu", "$updatedPending")



                            // Delete from Orders table
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


    }


    fun setData(order:List<Previous>){
        this.ordersList=order
        notifyDataSetChanged()
    }
}