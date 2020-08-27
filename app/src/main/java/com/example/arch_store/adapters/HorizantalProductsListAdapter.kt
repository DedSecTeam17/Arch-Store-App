package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
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
import com.example.arch_store.utils.MoneyFormatter
import kotlinx.android.synthetic.main.product_h_row.view.*

class HorizontalProductsListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.product_h_row, parent, false)) {

    private var title: TextView
    private var price: TextView
    private var image: ImageView

    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {
        title = itemView.findViewById<TextView>(R.id.title)
        price = itemView.findViewById<TextView>(R.id.price)
        image = itemView.findViewById<ImageView>(R.id.product_preview)

        item = itemView.findViewById<LinearLayout>(R.id.product_item_h)
    }

    //
    fun bind(product: Product, moneyFormatter: MoneyFormatter) {
        title.text = product.title
       price.text = moneyFormatter.getReadableMoney(product.price)

        Glide.with(itemView.context)
            .load("http://164.90.225.36${product.previewImage}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))
            .into(image)

    }


}

interface HorizontalProductsListListener {
    fun onItemClicked(product: Product)
}

class HorizontalProductsListAdapter(
    private var products: List<Product>,
    var ctx: Context,
    var horizontalProductsListListener: HorizontalProductsListListener,
    var moneyFormatter: MoneyFormatter

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
        holder.bind(products.get(position), moneyFormatter)
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            horizontalProductsListListener.onItemClicked(
                products[position]
            )
//          we need to call the interface
        }

    }

    internal fun setList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}