package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.arch_store.R

class OrdersListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.order_row, parent, false)) {

    //    private var title: TextView? = null
    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
//        title = itemView.findViewById<TextView>(R.id.nav_todo_item)
        item = itemView.findViewById<LinearLayout>(R.id.order_item)
    }
//
//    fun bind(text: String) {
//        title?.text = text
//
//    }


}

interface OrdersListListener {
    fun onItemClicked()
}

class OrdersListAdapter(
    private var orders: List<String>,
    var ctx: Context,
    var ordersListListener: OrdersListListener

) :
    RecyclerView.Adapter<OrdersListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersListHolder {
        val inflater = LayoutInflater.from(ctx)
        return OrdersListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrdersListHolder, position: Int) {
//        holder.bind(text = orders.get(position))
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            ordersListListener.onItemClicked()
//          we need to call the interface
        }

    }

    internal fun setList(orders: List<String>) {
        this.orders = orders
        notifyDataSetChanged()
    }

}