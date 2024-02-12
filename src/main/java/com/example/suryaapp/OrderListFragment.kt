import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suryaapp.R
import com.example.suryaapp.data.ordersData.ItemsSum
import com.example.suryaapp.data.ordersData.OrderAdapter
import com.example.suryaapp.data.ordersData.OrderViewModel

class OrderListFragment : Fragment(), SearchView.OnQueryTextListener ,MenuProvider{

    private lateinit var orderViewModel: OrderViewModel
    lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = resources.getColor(R.color.orderlistfragment)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        val adapter = OrderAdapter(this)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        orderViewModel.readAllData.observe(viewLifecycleOwner, Observer { order ->
            Log.d("OrderListFragment", "onCreateOptionsMenu called")
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
        return view
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("OrderListFragment4", "onCreateOptionsMenu called")
return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        Log.d("OrderListFragment3", "onCreateOptionsMenu called")
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        Log.d("OrderListFragment2", "onCreateOptionsMenu called")
        menu.clear()
        menuInflater.inflate(R.menu.actionbar_menu,menu)
        val menuSearch = menu.findItem(R.id.searchbar).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        Log.d("OrderListFragment1", "onCreateOptionsMenu called")
        return false
    }
    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        orderViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list)
        })
    }

}
