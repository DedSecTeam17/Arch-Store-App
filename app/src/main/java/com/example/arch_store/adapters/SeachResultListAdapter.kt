package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.arch_store.R
import com.example.arch_store.models.Product

class SearchListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.search_row, parent, false)) {

    //    private var title: TextView? = null
    var item: LinearLayout? = null
    var title: TextView? = null
    var price: TextView? = null

    var image: ImageView


    //
//
//    //    on initialization
    init {
        title = itemView.findViewById<TextView>(R.id.title)
        price = itemView.findViewById<TextView>(R.id.price)
        image = itemView.findViewById<ImageView>(R.id.product_image)
        item = itemView.findViewById<LinearLayout>(R.id.search_item)
    }

    //
    fun bind(product: Product) {
        title?.text = product.title
        price?.text = product.price.toString() + "- SDG"


        Glide.with(itemView.context)
            .load("http://164.90.225.36${product.previewImage}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))

            .into(image)

    }


}

interface SearchListListener {
    fun onItemClicked(product: Product)
}

class SearchListAdapter(
    private var products: List<Product>,
    var ctx: Context,
    var searchListListener: SearchListListener

) :
    RecyclerView.Adapter<SearchListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder {
        val inflater = LayoutInflater.from(ctx)
        return SearchListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        holder.bind(product=products.get(position))
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            searchListListener.onItemClicked(products.get(position))
//          we need to call the interface
        }

    }

    internal fun setList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}