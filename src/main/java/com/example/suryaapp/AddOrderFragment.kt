package com.example.suryaapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.suryaapp.data.ordersData.Order
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.databinding.FragmentAddOrderBinding
import java.util.Calendar

class AddOrderFragment : Fragment() {
    private lateinit var binding: FragmentAddOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var dateEdt: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)

        binding = FragmentAddOrderBinding.inflate(inflater, container, false)



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

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        binding.pro1.addTextChangedListener(createTextWatcher())
        binding.pro2.addTextChangedListener(createTextWatcher())
        binding.pro3.addTextChangedListener(createTextWatcher())
        binding.pro4.addTextChangedListener(createTextWatcher())
        binding.pro5.addTextChangedListener(createTextWatcher())
        binding.pro6.addTextChangedListener(createTextWatcher())
        binding.pro7.addTextChangedListener(createTextWatcher())


        binding.submitOrder.setOnClickListener {
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

    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateTotal()
            }
        }
    }

    private fun updateTotal() {
        val pro1 = binding.pro1.text.toString().toDoubleOrNull() ?: 0.0
        val pro2 = binding.pro2.text.toString().toDoubleOrNull() ?: 0.0
        val pro3 = binding.pro3.text.toString().toDoubleOrNull() ?: 0.0
        val pro4 = binding.pro4.text.toString().toDoubleOrNull() ?: 0.0
        val pro5 = binding.pro5.text.toString().toDoubleOrNull() ?: 0.0
        val pro6 = binding.pro6.text.toString().toDoubleOrNull() ?: 0.0
        val pro7 = binding.pro7.text.toString().toDoubleOrNull() ?: 0.0

        val amount = (pro1 + pro2 + pro3) * 250 + pro4 * 150 + (pro5 + pro6 + pro7) * 600

        binding.amount.setText(amount.toString())
    }

    fun insertData() {
        val customerName = binding.customerName.text.toString()
        val date = binding.idEdtDate.text.toString()
        val pro1 = binding.pro1.text.toString()
        val pro2 = binding.pro2.text.toString()
        val pro3 = binding.pro3.text.toString()
        val pro4 = binding.pro4.text.toString()
        val pro5 = binding.pro5.text.toString()
        val pro6 = binding.pro6.text.toString()
        val pro7 = binding.pro7.text.toString()
        val amount = binding.amount.text.toString()
        val order = Order(0, customerName, date, pro1, pro2, pro3, pro4, pro5, pro6, pro7, amount)
        orderViewModel.addUser(order)
        findNavController().navigate(R.id.action_addOrderFragment_to_homeFragment)
        showCustomToast("Order Placed Successfully")
       

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



