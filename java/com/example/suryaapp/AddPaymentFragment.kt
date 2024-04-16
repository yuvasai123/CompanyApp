package com.example.suryaapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.databinding.FragmentAddOrderBinding
import com.example.suryaapp.databinding.FragmentAddPaymentBinding
import java.util.Calendar


class AddPaymentFragment : Fragment() {
    private lateinit var binding: FragmentAddPaymentBinding
    private lateinit var paymentViewModel: PaymentsViewModel
    private lateinit var dateEdt: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddPaymentBinding.inflate(inflater, container, false)



        dateEdt = binding.idEdtDate
        dateEdt.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
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

        val toolbar: Toolbar = binding.toolbar

        // Set navigation icon click listener
        toolbar.setNavigationOnClickListener {
            // Handle navigation icon click (usually navigating back)
            findNavController().navigateUp()
        }

        paymentViewModel = ViewModelProvider(this).get(PaymentsViewModel::class.java)



        binding.addpaymentbtn.setOnClickListener {
            if (binding.customerName.text.isEmpty()) {
                showCustomToast("Please enter customer Name")

            }
            else if (binding.idEdtDate.text.isEmpty()) {
                showCustomToast("Please enter the date")
            }
            else {
                insertData()
            }
        }

        return binding.root
    }




    fun insertData() {
        val customerName = binding.customerName.text.toString()
        val date = binding.idEdtDate.text.toString()
       val totalamount=binding.totalamount.text.toString()
        val modeofpayment = binding.modeOfPayment.text.toString()
        val description = binding.description.text.toString()

        val payment = Payments(0, customerName, date, totalamount , modeofpayment, description)
        paymentViewModel.addPayment(payment)
        findNavController().navigate(R.id.action_addPaymentFragment3_to_addPaymentFragment)

        showCustomToast("Payment added Successfully")


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
