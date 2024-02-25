package com.example.suryaapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.data.ordersData.OrderAdapter
import com.example.suryaapp.data.ordersData.OrderViewModel

class OrderListFragment :Fragment(), SearchView.OnQueryTextListener {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        activity?.window?.statusBarColor = resources.getColor(R.color.orderlistfragment)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        adapter = OrderAdapter(this)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        orderViewModel.readAllData.observe(viewLifecycleOwner, Observer { order ->
            Log.d("OrderList", "List generated")
            adapter.setData(order)
        })
        orderViewModel.sumAllData.observe(viewLifecycleOwner, Observer { sums ->
            sums.forEach { itemsSum ->
                view.findViewById<TextView>(R.id.Sumtmq).text = itemsSum.sumtmq
                view.findViewById<TextView>(R.id.Sumchq).text = itemsSum.sumchq
                view.findViewById<TextView>(R.id.Sumsoq).text = itemsSum.sumsoq
                view.findViewById<TextView>(R.id.Sumvgq).text = itemsSum.sumvgq
                view.findViewById<TextView>(R.id.Sumtm5q).text = itemsSum.sumtm5q
                view.findViewById<TextView>(R.id.Sumch5q).text = itemsSum.sumch5q
                view.findViewById<TextView>(R.id.Sumso5q).text = itemsSum.sumso5q
            }
        })

        // Set up the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setHasOptionsMenu(true)

        val toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as MainActivity?)!!.setSupportActionBar(toolbar)
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

        orderViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list)
        })
    }
}
