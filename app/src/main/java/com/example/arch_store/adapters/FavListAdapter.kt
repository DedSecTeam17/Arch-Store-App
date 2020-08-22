package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.arch_store.R

class FavListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.fav_product_row, parent, false)) {

    //    private var title: TextView? = null
    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
//        title = itemView.findViewById<TextView>(R.id.nav_todo_item)
        item = itemView.findViewById<LinearLayout>(R.id.fav_item)
    }
//
//    fun bind(text: String) {
//        title?.text = text
//
//    }


}

interface FavListListener {
    fun onItemClicked()
}

class FavListAdapter(
    private var products: List<String>,
    var ctx: Context,
    var favListListener: FavListListener

) :
    RecyclerView.Adapter<FavListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavListHolder {
        val inflater = LayoutInflater.from(ctx)
        return FavListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: FavListHolder, position: Int) {
//        holder.bind(text = products.get(position))
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            favListListener.onItemClicked()
//          we need to call the interface
        }

    }

    internal fun setList(products: List<String>) {
        this.products = products
        notifyDataSetChanged()
    }

}