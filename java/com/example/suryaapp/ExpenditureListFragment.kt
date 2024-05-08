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
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.data.expenditurelist.ExpenditureAdapter
import com.example.suryaapp.data.expenditurelist.ExpenditureViewModel
import com.example.suryaapp.data.ordersData.OrderAdapter
import com.example.suryaapp.data.ordersData.OrderViewModel
import com.example.suryaapp.data.paymentlists.PaymentsAdapter
import com.example.suryaapp.data.paymentlists.PaymentsViewModel
import com.example.suryaapp.databinding.FragmentAddOrderBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpenditureListFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var expenditureViewModel: ExpenditureViewModel
    private lateinit var adapter: ExpenditureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_expenditure_list, container, false)
        val button = view.findViewById<FloatingActionButton>(R.id.addexpenditure)
        button.setOnClickListener{
            it.findNavController().navigate(R.id.action_expenditureListFragment_to_addExpenditureFragment)
        }
        adapter = ExpenditureAdapter(this)
        expenditureViewModel = ViewModelProvider(this).get(ExpenditureViewModel::class.java)
        expenditureViewModel.readAllData.observe(viewLifecycleOwner, Observer { payments ->

            adapter.setData(payments)
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
        val searchText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
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

        expenditureViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list)
        })
    }


}