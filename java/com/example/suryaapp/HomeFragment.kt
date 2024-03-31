package com.example.suryaapp

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.suryaapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set menu icon click listener
        binding.menuIcon.setOnClickListener { showPopupMenu(it) }

        // Set click listeners for other views
        binding.placeOrder.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addOrderFragment)
        }
        binding.viewOrders.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_orderListFragment2)
        }
        binding.pendingPayments.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_pendingListFragment)
        }
        binding.previousOrders.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_previousListFragment)
        }
        binding.payments.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addPaymentFragment)
        }
        binding.recipients.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_recipientsListFragment)
        }

        return binding.root
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.home_menu) // Inflate your menu XML file
        popupMenu.setOnMenuItemClickListener { item ->
            handleMenuItemClick(item)
        }
        popupMenu.show()
    }

    private fun handleMenuItemClick(item: MenuItem): Boolean {
        // Handle menu item click events here
        when (item.itemId) {
            R.id.backup -> {
                // Handle menu item 1 click
                return true
            }
            R.id.restore -> {
                // Handle menu item 2 click
                return true
            }
            // Add more cases for other menu items if needed
            else -> return false
        }
    }
}
