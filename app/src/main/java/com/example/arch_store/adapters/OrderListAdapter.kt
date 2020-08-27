package com.example.arch_store.adapters


import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.arch_store.R
import com.example.arch_store.models.Order
import com.example.arch_store.utils.MoneyFormatter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class OrdersListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.order_row, parent, false)) {

    private var status: TextView
    private var total: TextView
    private var count: TextView
    private var date: TextView
    private var orderId: TextView


    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
        status = itemView.findViewById<TextView>(R.id.status)
        total = itemView.findViewById<TextView>(R.id.total)
        count = itemView.findViewById<TextView>(R.id.count)
        date = itemView.findViewById<TextView>(R.id.create_date)
        orderId = itemView.findViewById<TextView>(R.id.order_id)

        item = itemView.findViewById<LinearLayout>(R.id.order_item)
    }

    //Shoes
    //
    fun bind(order: Order, formatter: MoneyFormatter) {
        status.text = order.orderStatus
        total.text = formatter.getReadableMoney(order.orderLinesTotal)
        count.text = "${order.orderLinesCount.toInt()} Shoose"


       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           var inputFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           val mDate = inputFormat.parse(order.createAt)
           val niceDateStr = DateUtils.getRelativeTimeSpanString(
               mDate.getTime(),
               Calendar.getInstance().getTimeInMillis(),
               DateUtils.MINUTE_IN_MILLIS
           )
           date.text = niceDateStr
        } else {
            TODO("VERSION.SDK_INT < O")
        }


        orderId.text = """#${order.orderName}"""
    }


}

interface OrdersListListener {
    fun onItemClicked(order: Order)
}

class OrdersListAdapter(
    private var orders: List<Order>,
    var ctx: Context,
    var ordersListListener: OrdersListListener,
    var formatter: MoneyFormatter

) :
    RecyclerView.Adapter<OrdersListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersListHolder {
        val inflater = LayoutInflater.from(ctx)
        return OrdersListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrdersListHolder, position: Int) {
        holder.bind(orders[position], formatter)
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            ordersListListener.onItemClicked(order = orders[position])
//          we need to call the interface
        }

    }

    internal fun setList(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

}