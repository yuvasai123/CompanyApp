package com.example.suryaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.data.pendingData.PendingAdapter
import com.example.suryaapp.data.pendingData.PendingViewModel


class PendingListFragment : Fragment() {

    private lateinit var pendingViewModel: PendingViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        activity?.window?.statusBarColor = resources.getColor(R.color.pendinglistfragment)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pending_list, container, false)
        val adapter= PendingAdapter()
        pendingViewModel=ViewModelProvider(this).get(PendingViewModel::class.java)
        pendingViewModel.readAllPendingData.observe(viewLifecycleOwner, Observer {
                pending->
            adapter.setData(pending)
        })

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)

        // Set navigation icon click listener
        toolbar.setNavigationOnClickListener {
            // Handle navigation icon click (usually navigating back)
            findNavController().navigateUp()
        }

        // Set up the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviews)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }



}