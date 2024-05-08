package com.example.suryaapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
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
import com.example.suryaapp.data.expenditurelist.Expenditure
import com.example.suryaapp.data.expenditurelist.ExpenditureViewModel
import com.example.suryaapp.data.paymentlists.Payments
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.data.recipientslist.Recipients
import com.example.suryaapp.data.recipientslist.RecipientsViewModel
import com.example.suryaapp.databinding.FragmentAddExpenditureBinding
import com.example.suryaapp.databinding.FragmentAddOrderBinding
import com.example.suryaapp.databinding.FragmentAddPaymentBinding
import com.example.suryaapp.databinding.FragmentAddRecipientsBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddExpenditureFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenditureBinding
    private lateinit var expenditureViewModel: ExpenditureViewModel
    private lateinit var dateEdt: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentAddExpenditureBinding.inflate(inflater, container, false)



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

        expenditureViewModel = ViewModelProvider(this).get(ExpenditureViewModel::class.java)



        binding.addrecipientbtn.setOnClickListener {
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


    private fun parseDate(dateString: String): Long? {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return try {
            format.parse(dateString)?.time
        } catch (e: ParseException) {
            null
        }
    }


    fun insertData() {
        val customerName = binding.customerName.text.toString()
        val dateString = binding.idEdtDate.text.toString() // Get the date string
        val date = parseDate(dateString) ?: 0L

        val totalamount=binding.totalamount.text.toString()

        val description = binding.descriptionedittext.text.toString()


        val recipients = Expenditure(0, customerName, date, totalamount , description )
        expenditureViewModel.addExpenditure(recipients)
        findNavController().navigate(R.id.action_addExpenditureFragment_to_expenditureListFragment)

        showCustomToast("Expenditure Added Successfully")


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
