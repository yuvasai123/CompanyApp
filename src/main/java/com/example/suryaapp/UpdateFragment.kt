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
import java.util.Calendar

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var orderViewModel: OrderViewModel

    private lateinit var customerName: EditText
    private lateinit var date: TextView
    private lateinit var tmq: EditText
    private lateinit var chq: EditText
    private lateinit var soq: EditText
    private lateinit var vgq: EditText
    private lateinit var tm5q: EditText
    private lateinit var ch5q: EditText
    private lateinit var so5q: EditText
    private lateinit var amount: EditText
    private lateinit var updateOrder: Button
    private lateinit var toolbar:Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemView = inflater.inflate(R.layout.fragment_update, container, false)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        // Initialize variables correctly
        customerName = itemView.findViewById(R.id.UpdateCustomerName)
        date = itemView.findViewById(R.id.UpdateEdtDate)
        tmq = itemView.findViewById(R.id.Updatepro1)
        chq = itemView.findViewById(R.id.Updatepro2)
        soq = itemView.findViewById(R.id.Updatepro3)
        vgq = itemView.findViewById(R.id.Updatepro4)
        tm5q = itemView.findViewById(R.id.Updatepro5)
        ch5q = itemView.findViewById(R.id.Updatepro6)
        so5q = itemView.findViewById(R.id.Updatepro7)
        amount = itemView.findViewById(R.id.Updateamount)
        updateOrder = itemView.findViewById(R.id.updateOrder)
        toolbar = itemView.findViewById(R.id.toolbar)

        // Set values
        customerName.setText(args.currentOrder.customerName)
        date.text = args.currentOrder.date
        tmq.setText(args.currentOrder.tmq)
        chq.setText(args.currentOrder.chq)
        vgq.setText(args.currentOrder.vgq)
        soq.setText(args.currentOrder.soq)
        tm5q.setText(args.currentOrder.tm5q)
        ch5q.setText(args.currentOrder.ch5q)
        so5q.setText(args.currentOrder.so5q)
        amount.setText(args.currentOrder.amount)


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




        tmq.addTextChangedListener(createTextWatcher())
        chq.addTextChangedListener(createTextWatcher())
        vgq.addTextChangedListener(createTextWatcher())
        soq.addTextChangedListener(createTextWatcher())
        tm5q.addTextChangedListener(createTextWatcher())
        ch5q.addTextChangedListener(createTextWatcher())
        so5q.addTextChangedListener(createTextWatcher())


        toolbar.setNavigationOnClickListener {
            // Handle navigation icon click (usually navigating back)
            findNavController().navigateUp()
        }


        updateOrder.setOnClickListener {
            updateItem()
        }



        return itemView
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
        // Get text values from EditText fields and convert to numbers
        val tmqValue = tmq.text.toString().toDoubleOrNull() ?: 0.0
        val chqValue = chq.text.toString().toDoubleOrNull() ?: 0.0
        val soqValue = soq.text.toString().toDoubleOrNull() ?: 0.0
        val vgqValue = vgq.text.toString().toDoubleOrNull() ?: 0.0
        val tm5qValue = tm5q.text.toString().toDoubleOrNull() ?: 0.0
        val ch5qValue = ch5q.text.toString().toDoubleOrNull() ?: 0.0
        val so5qValue = so5q.text.toString().toDoubleOrNull() ?: 0.0


        // Calculate the total
        val totalAmount = (tmqValue + chqValue + soqValue) * 250 +
                vgqValue * 150 +
                (tm5qValue + ch5qValue + so5qValue) * 600


        // Set the total as the text of the total TextView using setText()
       amount.setText(totalAmount.toString())
    }



    private fun updateItem() {
        val updatedCustomerName = customerName.text.toString()
        val updatedDate = date.text.toString()
        val updatedTmq = tmq.text.toString()
        val updatedChq = chq.text.toString()
        val updatedSoq = soq.text.toString()
        val updatedVgq = vgq.text.toString()
        val updatedTm5q = tm5q.text.toString()
        val updatedCh5q = ch5q.text.toString()
        val updatedSo5q = so5q.text.toString()
        val updatedAmount = amount.text.toString()




        val updatedOrder = Order(
            args.currentOrder.id,
            updatedCustomerName,
            updatedDate,
            updatedTmq,
            updatedChq,
            updatedSoq,
            updatedVgq,
            updatedTm5q,
            updatedCh5q,
            updatedSo5q,
            updatedAmount
        )

        if (updatedCustomerName.isEmpty()) {
            showCustomToast("Please enter customer Name")

        }
        else if (updatedDate.isEmpty()) {
            showCustomToast("Please enter the date")
        }
        else {
            orderViewModel.updateOrder(updatedOrder)
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            showCustomToast("Order Updated Successfully")
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