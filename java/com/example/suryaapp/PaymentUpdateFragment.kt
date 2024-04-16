package com.example.suryaapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.util.Calendar

class PaymentUpdateFragment : Fragment() {

    private val args by navArgs<PaymentUpdateFragmentArgs>()
    private lateinit var paymentsViewModel: PaymentsViewModel

    private lateinit var customerName: EditText
    private lateinit var date: TextView
    private lateinit var totalamount: EditText
    private lateinit var modeOfPayment: EditText
    private lateinit var description: EditText

    private lateinit var updatePayment: Button
    private lateinit var toolbar:Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemView = inflater.inflate(R.layout.fragment_payment_update, container, false)

        paymentsViewModel = ViewModelProvider(this).get(PaymentsViewModel::class.java)

        // Initialize variables correctly
        customerName = itemView.findViewById(R.id.updatecustomerName)
        date = itemView.findViewById(R.id.updateidEdtDate)
        totalamount=itemView.findViewById(R.id.updatetotalamount)
      modeOfPayment = itemView.findViewById(R.id.updatemodeOfPayment)
        description = itemView.findViewById(R.id.updatedescription)
        updatePayment = itemView.findViewById(R.id.updateaddpaymentbtn)


        toolbar = itemView.findViewById(R.id.toolbar)

        // Set values
        customerName.setText(args.currentPayment.customerName)
        date.text = args.currentPayment.date
        totalamount.setText(args.currentPayment.totalamount)
        modeOfPayment.setText(args.currentPayment.cashtype)
        description.setText(args.currentPayment.paydescription)



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
        val updatedDate = date.text.toString()
        val updatedamount = totalamount.text.toString()
        val updatedmodeofpayment = modeOfPayment.text.toString()
        val updateddescription = description.text.toString()



        val updatedOrder = Payments(
            args.currentPayment.id,
            updatedCustomerName,
            updatedDate,
            updatedamount,
            updatedmodeofpayment,
            updateddescription,
        )

        if (updatedCustomerName.isEmpty()) {
            showCustomToast("Please enter customer Name")

        }
        else if (updatedDate.isEmpty()) {
            showCustomToast("Please enter the date")
        }
        else {
            paymentsViewModel.updatePayment(updatedOrder)
            findNavController().navigate(R.id.action_paymentUpdateFragment_to_addPaymentFragment)
            showCustomToast("Payment Updated Successfully")
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