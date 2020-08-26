package com.example.arch_store.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
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
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.offline_db.favourtites.FavProduct

class FavListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.fav_product_row, parent, false)) {

    private var title: TextView? = null
    private var price: TextView? = null


    private var image: ImageView

    var remvFav: ImageButton


    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {

//        basic info
        title = itemView.findViewById<TextView>(R.id.title)
        price = itemView.findViewById<TextView>(R.id.price)



        image = itemView.findViewById<ImageView>(R.id.product_preview)

        remvFav = itemView.findViewById<ImageButton>(R.id.remove_from_fav)


        item = itemView.findViewById<LinearLayout>(R.id.fav_item)
    }

    //
    fun bind(favProduct: FavProduct) {

        if (favProduct.title.length > 20) {
            title?.text = favProduct.title.subSequence(0, 20).toString() + ".."
        } else {
            title?.text = favProduct.title
        }
        price?.text = favProduct.price.toString() + "- SDG"

        Glide.with(itemView.context)
            .load("http://164.90.225.36${favProduct.previewImage}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))
            .into(image)

    }

}

interface FavListListener {
    fun onItemClicked(favProduct: FavProduct)
    fun onItemRemoveFromFav(favProduct: FavProduct)
}

class FavListAdapter(
    private var products: List<FavProduct>,
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
        holder.bind(favProduct = products[position])
//
//
        holder.item?.setOnClickListener {
            println("clicked")
            favListListener.onItemClicked(products[position])
//          we need to call the interface
        }
        holder.remvFav.setOnClickListener {
            favListListener.onItemRemoveFromFav(products[position])
        }

    }

    internal fun setList(products: List<FavProduct>) {
        this.products = products
        notifyDataSetChanged()
    }

}