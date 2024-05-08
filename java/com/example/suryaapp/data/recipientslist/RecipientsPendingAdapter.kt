package com.example.suryaapp.data.recipientslist

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
import com.example.suryaapp.data.debitedlist.Debited
import com.example.suryaapp.data.debitedlist.DebitedViewModel
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


class RecipientsPendingAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<RecipientsPendingAdapter.ViewHolder>() {

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

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = sdf.format(Date(orderData.date))
        val dates = sdf.parse(dateString)?.time ?: 0L
        holder.customerName.text = orderData.customerName
        holder.date.text = dateString
        holder.totalamount.text = orderData.totalamount
        holder.billNo.text = orderData.billNo
        holder.paydescription.text = orderData.description
        holder.balance.text = orderData.balance
        holder.button.setOnClickListener {

            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.pending_box_layout, null)

            val amountedittext = dialogView.findViewById<TextView>(R.id.amountEditText)
            val edited = dialogView.findViewById<EditText>(R.id.balanceEditText)
            val modeofpayment = dialogView.findViewById<EditText>(R.id.modeofpayment)
            val description = dialogView.findViewById<EditText>(R.id.description)
            amountedittext.text = orderData.balance


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
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {

                val editedValue = edited.text.toString().toDoubleOrNull() ?: 0.0
                val balanceValue = orderData.balance.toDoubleOrNull() ?: 0.0
                val updatedBalance = (balanceValue - editedValue).toInt().toString()
                if (editedValue > balanceValue) {
                    showCustomToast(
                        holder.itemView.context,
                        "Entered balance cannot be greater than the original amount"
                    )
                } else {

                    val updatedPending = Recipients(
                        id = orderData.id, // Assuming id remains the same
                        customerName = orderData.customerName,
                        date = orderData.date,
                        billNo = orderData.billNo,
                        totalamount = orderData.totalamount,
                        description = orderData.description,
                        balance = updatedBalance
                    )

                    fun generateUniqueId(): Int {
                        // Use current time as a base for the ID
                        return System.currentTimeMillis().toInt()
                    }

                    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val dateString = sdf.format(Date(orderData.date))
                    val dates = sdf.parse(dateString)?.time ?: 0L

                    val debited = Debited(
                        id = generateUniqueId(),
                        customerName = orderData.customerName,
                        date = dates,
                        totalamount = edited.text.toString(),
                        cashtype = modeofpayment.text.toString(),
                        paydescription = description.text.toString()
                    )
                    val debitedViewModel: DebitedViewModel
                    debitedViewModel =
                        ViewModelProvider(viewModelStoreOwner).get(DebitedViewModel::class.java)
                    val recipientsViewModel: RecipientsViewModel
                    recipientsViewModel =
                        ViewModelProvider(viewModelStoreOwner).get(RecipientsViewModel::class.java)





                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Insert into Pending table


                            recipientsViewModel.updateRecipients(updatedPending)
                            debitedViewModel.addDebited(debited)
                            Log.d("Menu", "$updatedPending")


                            // Delete from Orders table
                        } catch (e: Exception) {
                            Log.e("Error in Pending", "Error during transaction", e)
                        }
                    }

                    alertDialog.dismiss()
                }
            }


            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setOnClickListener {
                    // Handle "Cancel" button click
                    // You can perform any action here or simply dismiss the dialog
                    alertDialog.dismiss()
                }



            // Create and show the AlertDialog


        }
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

