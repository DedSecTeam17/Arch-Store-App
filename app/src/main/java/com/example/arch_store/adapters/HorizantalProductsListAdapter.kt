package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.arch_store.R

class HorizontalProductsListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.product_h_row, parent, false)) {

    //    private var title: TextView? = null
    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
//        title = itemView.findViewById<TextView>(R.id.nav_todo_item)
        item = itemView.findViewById<LinearLayout>(R.id.product_item_h)
    }
//
//    fun bind(text: String) {
//        title?.text = text
//
//    }


}

interface HorizontalProductsListListener {
    fun onItemClicked()
}

class HorizontalProductsListAdapter(
    private var products: List<String>,
    var ctx: Context,
    var horizontalProductsListListener: HorizontalProductsListListener

) :
    RecyclerView.Adapter<HorizontalProductsListHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalProductsListHolder {
        val inflater = LayoutInflater.from(ctx)
        return HorizontalProductsListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: HorizontalProductsListHolder, position: Int) {
//        holder.bind(text = products.get(position))
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            horizontalProductsListListener.onItemClicked()
//          we need to call the interface
        }

    }

    internal fun setList(products: List<String>) {
        this.products = products
        notifyDataSetChanged()
    }

}