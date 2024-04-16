package com.example.suryaapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.data.previousData.BalanceAdapter
import com.example.suryaapp.data.previousData.PreviousViewModel


class PendingListFragment : Fragment() ,SearchView.OnQueryTextListener{

    private lateinit var pendingViewModel: PreviousViewModel

    private lateinit var adapter: BalanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pending_list, container, false)
        adapter= BalanceAdapter(this)
        pendingViewModel=ViewModelProvider(this).get(PreviousViewModel::class.java)
        pendingViewModel.readAllBalanceData.observe(viewLifecycleOwner, Observer {
                pending->
            Log.d("QueryCalled", " msg is $pending")
            adapter.setData(pending)
        })



        // Set up the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviews)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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

        pendingViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list)
        })
    }


}