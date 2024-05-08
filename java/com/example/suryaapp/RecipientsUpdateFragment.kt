package com.example.suryaapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.data.recipientslist.Recipients
import com.example.suryaapp.data.recipientslist.RecipientsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RecipientsUpdateFragment : Fragment() {

    private val args by navArgs<RecipientsUpdateFragmentArgs>()
    private lateinit var recipientsViewModel: RecipientsViewModel

    private lateinit var customerName: EditText
    private lateinit var date: TextView
    private lateinit var billno: TextView
    private lateinit var totalamount: EditText

    private lateinit var description: EditText
    private lateinit var updatePayment: Button
    private lateinit var toolbar:Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemView = inflater.inflate(R.layout.fragment_recipients_update, container, false)

        recipientsViewModel = ViewModelProvider(this).get(RecipientsViewModel::class.java)

        // Initialize variables correctly
        customerName = itemView.findViewById(R.id.updatecustomerName)
        date = itemView.findViewById(R.id.updateidEdtDate)
        billno = itemView.findViewById(R.id.updatebillno)
        totalamount=itemView.findViewById(R.id.updatetotalamount)
        description = itemView.findViewById(R.id.updatedescriptionedittext)
        updatePayment = itemView.findViewById(R.id.updaterecipientbtn)


        toolbar = itemView.findViewById(R.id.toolbar)

        // Set values
        customerName.setText(args.currentRecipients.customerName)
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = dateFormatter.format(args.currentRecipients.date)
        date.text = dateString
        totalamount.setText(args.currentRecipients.totalamount)
        billno.setText(args.currentRecipients.billNo)
        description.setText(args.currentRecipients.description)



        date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    date.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }







        toolbar.setNavigationOnClickListener {
            // Handle navigation icon click (usually navigating back)
            findNavController().navigateUp()
        }


        updatePayment.setOnClickListener {
            updateItem()
        }

        return itemView
    }


    private fun updateItem() {
        val updatedCustomerName = customerName.text.toString()
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val updatedDate = sdf.parse(date.text.toString())?.time ?: 0L
        val updatedamount = totalamount.text.toString()
        val updatedbillno = billno.text.toString()
        val updateddescription = description.text.toString()
        val updatedbalance = totalamount.text.toString()



        val updatedOrder = Recipients(
            args.currentRecipients.id,
            updatedCustomerName,
            updatedbillno,
            updatedDate,
            updatedamount,
            updateddescription,
            updatedbalance
        )

        if (updatedCustomerName.isEmpty()) {
            showCustomToast("Please enter customer Name")

        }
        else if (updatedDate==0L) {
            showCustomToast("Please enter the date")
        }
        else {
            recipientsViewModel.updateRecipients(updatedOrder)
            findNavController().navigate(R.id.action_recipientsUpdateFragment_to_pendingRecipientsFragment)
            showCustomToast("Recipient Updated Successfully")
        }

    }
    fun showCustomToast(message: String) {
        val inflater = LayoutInflater.from(requireContext())
        val layout: View = inflater.inflate(R.layout.custom_toast, null)

        val textView: TextView = layout.findViewById(R.id.custom_toast_message)
        textView.text = message

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

}