package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.arch_store.R

class CartListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.cart_row, parent, false)) {

    //    private var title: TextView? = null
    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
//        title = itemView.findViewById<TextView>(R.id.nav_todo_item)
        item = itemView.findViewById<LinearLayout>(R.id.cart_item)
    }
//
//    fun bind(text: String) {
//        title?.text = text
//
//    }


}

interface CartListListener {
    fun onItemClicked()
}

class CartListAdapter(
    private var products: List<String>,
    var ctx: Context,
    var cartListListener: CartListListener

) :
    RecyclerView.Adapter<CartListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListHolder {
        val inflater = LayoutInflater.from(ctx)
        return CartListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: CartListHolder, position: Int) {
//        holder.bind(text = products.get(position))
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            cartListListener.onItemClicked()
//          we need to call the interface
        }

    }

    internal fun setList(products: List<String>) {
        this.products = products
        notifyDataSetChanged()
    }

}