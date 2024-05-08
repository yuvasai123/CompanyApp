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
import com.example.suryaapp.data.expenditurelist.Expenditure
import com.example.suryaapp.data.expenditurelist.ExpenditureViewModel
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.data.recipientslist.Recipients
import com.example.suryaapp.data.recipientslist.RecipientsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExpenditureUpdateFragment : Fragment() {

    private val args by navArgs<ExpenditureUpdateFragmentArgs>()
    private lateinit var expeditureViewModel: ExpenditureViewModel

    private lateinit var customerName: EditText
    private lateinit var date: TextView
    private lateinit var totalamount: EditText
    private lateinit var description: EditText
    private lateinit var updatePayment: Button
    private lateinit var toolbar:Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemView = inflater.inflate(R.layout.fragment_expenditure_update, container, false)

        expeditureViewModel = ViewModelProvider(this).get(ExpenditureViewModel::class.java)

        // Initialize variables correctly
        customerName = itemView.findViewById(R.id.updatedcustomerName)
        date = itemView.findViewById(R.id.updatedidEdtDate)
        totalamount=itemView.findViewById(R.id.updatedtotalamount)
        description = itemView.findViewById(R.id.updateddescriptionedittext)
        updatePayment = itemView.findViewById(R.id.updatedaddexpenditurebtn)


        toolbar = itemView.findViewById(R.id.toolbar)

        // Set values
        customerName.setText(args.currentExpenditure.customerName)
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = dateFormatter.format(args.currentExpenditure.date)
        date.text = dateString
        totalamount.setText(args.currentExpenditure.amount)
        description.setText(args.currentExpenditure.description)



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
        val updateddescription = description.text.toString()
        val updatedbalance = totalamount.text.toString()



        val updatedOrder = Expenditure(
            args.currentExpenditure.id,
            updatedCustomerName,
            updatedDate,
            updatedamount,
            updateddescription,
        )

        if (updatedCustomerName.isEmpty()) {
            showCustomToast("Please enter customer Name")

        }
        else if (updatedDate==0L) {
            showCustomToast("Please enter the date")
        }
        else {
            expeditureViewModel.updateExpenditure(updatedOrder)
            findNavController().navigate(R.id.action_expenditureUpdateFragment_to_expenditureListFragment)
            showCustomToast("Expenditure Updated Successfully")
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