package com.example.suryaapp.data.pendingData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.R
import com.example.suryaapp.data.pendingData.PendingViewModel

class PendingAdapter :
    RecyclerView.Adapter<PendingAdapter.ViewHolder>() {

    private var ordersList= emptyList<Pending>()



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
        val save : Button = itemView.findViewById(R.id.save)
        val cardLayout : CardView=itemView.findViewById(R.id.cardLayout)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_view_layout, parent, false)
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


        // Set other TextViews with corresponding data fields
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
    fun setData(order:List<Pending>){
        this.ordersList=order
        notifyDataSetChanged()
    }
}
