package com.example.suryaapp.data.previousData
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.OrderListFragmentDirections
import com.example.suryaapp.PreviousListFragmentDirections
import com.example.suryaapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreviousAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<PreviousAdapter.ViewHolder>() {

    private var ordersList= emptyList<Previous>()



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date:TextView=itemView.findViewById(R.id.orderDate)
        val tmq: TextView = itemView.findViewById(R.id.tmq)
        val chq: TextView = itemView.findViewById(R.id.chq)
        val soq: TextView = itemView.findViewById(R.id.soq)
        val vgq: TextView = itemView.findViewById(R.id.vgq)
        val tm5q: TextView = itemView.findViewById(R.id.tm5q)
        val ch5q: TextView = itemView.findViewById(R.id.ch5q)
        val so5q: TextView = itemView.findViewById(R.id.so5q)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val cardLayout : CardView=itemView.findViewById(R.id.cardLayout)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.previous_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = ordersList[position]


        holder.customerName.text = orderData.customerName
        holder.date.text=orderData.date
        holder.tmq.text = orderData.tmq
        holder.chq.text = orderData.chq
        holder.soq.text = orderData.soq
        holder.vgq.text = orderData.vgq
        holder.tm5q.text = orderData.tm5q
        holder.ch5q.text = orderData.ch5q
        holder.so5q.text = orderData.so5q
        holder.amount.text = orderData.amount
        holder.cardLayout.setOnClickListener{
            val action = PreviousListFragmentDirections.actionPreviousListFragmentToPreviousUpdateFragment(orderData)
            holder.itemView.findNavController().navigate(action)
        }




        // Set other TextViews with corresponding data fields
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
    fun setData(order:List<Previous>){
        this.ordersList=order
        notifyDataSetChanged()
    }
}
