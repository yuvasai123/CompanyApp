package com.example.suryaapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.suryaapp.databinding.FragmentHomeBinding
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1001
    private val REQUEST_CODE_BACKUP_DB = 1002

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
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request the permission
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE
                    )
                } else {
                    // Permission already granted, perform your operation
                    backupDatabase()
                }
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

    // HomeFragment.kt
    private fun backupDatabase() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream" // Set the MIME type based on your file type
            putExtra(Intent.EXTRA_TITLE, "backup.db") // Default file name
        }

        startActivityForResult(intent, REQUEST_CODE_BACKUP_DB)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BACKUP_DB && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                try {
                    requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                        val dbFile = requireContext().getDatabasePath("order_database")
                        dbFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    Toast.makeText(requireContext(), "Database backup successful", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Toast.makeText(requireContext(), "Database backup failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }
}
