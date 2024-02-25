package com.example.suryaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.suryaapp.databinding.ActivityMainBinding
import com.example.suryaapp.databinding.ActivityMainBinding.*
import com.example.suryaapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

private lateinit var binding:FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.window?.statusBarColor = resources.getColor(R.color.homefragment)

        binding= FragmentHomeBinding.inflate(inflater,container,false)

        binding.placeOrder.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addOrderFragment)
        }
          binding.viewOrders.setOnClickListener{
              it.findNavController().navigate(R.id.action_homeFragment_to_orderListFragment2)
          }
        binding.pendingPayments.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_pendingListFragment)
        }
        binding.previousOrders.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_previousListFragment)
        }
        binding.payments.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addPaymentFragment)
        }
        binding.recipients.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_recipientsListFragment)
        }

        return binding.root
    }


}