package com.example.suryaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.data.debitedlist.DebitedViewModel
import com.example.suryaapp.data.ordersData.OrderAdapter
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.paymentlists.DebitedAdapter
import com.example.suryaapp.data.paymentlists.PaymentsAdapter
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.databinding.FragmentAddOrderBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DebitedListFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var debitedViewModel: DebitedViewModel
    private lateinit var adapter: DebitedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_debited_list, container, false)
        val button = view.findViewById<FloatingActionButton>(R.id.addpayment)
        button.setOnClickListener{
            it.findNavController().navigate(R.id.action_addPaymentFragment_to_addPaymentFragment3)
        }
        adapter = DebitedAdapter(this)
        debitedViewModel = ViewModelProvider(this).get(DebitedViewModel::class.java)
        debitedViewModel.readAllDebitedData.observe(viewLifecycleOwner, Observer { debited ->

            adapter.setData(debited)
        })
        val toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as MainActivity?)!!.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            // Handle navigation icon click (usually navigating back)
            findNavController().navigateUp()
        }


        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setHasOptionsMenu(true)

        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("Menu", "onCreatesMenu called")


        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar_menu, menu)
        val searchItem = menu.findItem(R.id.searchbar)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("QueryCalled", "query submitted called")
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        Log.d("TextChangeCalled", "searching changed called called")

        if (query != null) {
            searchDatabase(query)
        }

        else {
            // Handle the case where the query is empty
            searchDatabase("") // Pass an empty string to retrieve full data
        }


        return true
    }


    private fun searchDatabase(query: String) {

        val searchQuery = query
        Log.d("QueryCalled", " msg is $searchQuery")

        debitedViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list)
        })
    }


}