package com.example.arch_store.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.arch_store.R
import com.example.arch_store.models.Product

class ProductListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.product_row, parent, false)) {

    private var price: TextView? = null
    private var discount: TextView? = null
    private var image: ImageView


    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
        price = itemView.findViewById<TextView>(R.id.price)
        discount = itemView.findViewById<TextView>(R.id.discount)

        item = itemView.findViewById<LinearLayout>(R.id.product_item)

        image = itemView.findViewById<ImageView>(R.id.product_image)

    }

    //discountTxt
    fun bind(product: Product) {
        price?.text = product.price.toString() + "- SDG"
        if (product.discount.toDouble() > 0.0) {
            discount!!.visibility = View.VISIBLE
            discount?.text = "${product.discount.toInt()}%"
        } else {
            discount!!.visibility = View.GONE

        }

        Glide.with(itemView.context)
            .load("http://164.90.225.36${product.previewImage}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))

            .into(image)


    }


}

interface ProductListListener {
    fun onItemClicked(product: Product)
}

class ProductListAdapter(
    private var products: List<Product>,
    var ctx: Context,
    var productListListener: ProductListListener

) :
    RecyclerView.Adapter<ProductListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        val inflater = LayoutInflater.from(ctx)
        return ProductListHolder(inflater, parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        holder.bind(
            products.get(position)
        )
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            productListListener.onItemClicked(product = products.get(position))
//          we need to call the interface
        }

    }

    internal fun setList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}