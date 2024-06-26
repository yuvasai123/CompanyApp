package com.example.suryaapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.suryaapp.data.OrderDatabase
import com.example.suryaapp.databinding.FragmentHomeBinding
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1001
    private val REQUEST_CODE_BACKUP_DB = 1002
    private val REQUEST_CODE_RESTORE_DB = 1003
    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1001

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
        binding.expenditurebtn.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_expenditureListFragment)

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
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request the permission
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_READ_EXTERNAL_STORAGE
                    )
                } else {
                    // Permission already granted, perform your operation
                    restoreDatabase()
                }
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

    private fun restoreDatabase() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream" // Set the MIME type based on your file type
        }

        startActivityForResult(intent, REQUEST_CODE_RESTORE_DB)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_BACKUP_DB -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        try {

                            // Copy the main database file
                            requireContext().contentResolver.openOutputStream(uri)
                                ?.use { outputStream ->
                                    val dbFile = requireContext().getDatabasePath("order_database")
                                    dbFile.inputStream().use { inputStream ->
                                        inputStream.copyTo(outputStream)
                                    }
                                }
                            Toast.makeText(
                                requireContext(),
                                "Database backup successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                "Database backup failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            e.printStackTrace()
                        }
                    }
                }
            }

            REQUEST_CODE_RESTORE_DB -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        try {
                            // Copy the selected database backup file to the main database file
                            requireContext().contentResolver.openInputStream(uri)
                                ?.use { inputStream ->
                                    val dbFile = requireContext().getDatabasePath("order_database")
                                    dbFile.outputStream().use { outputStream ->
                                        inputStream.copyTo(outputStream)
                                    }
                                }
                            Toast.makeText(
                                requireContext(),
                                "Database restore successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: IOException) {
                            Toast.makeText(
                                requireContext(),
                                "Database restore failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            e.printStackTrace()
                        }
                    }
                }
            }

        }
    }
}



