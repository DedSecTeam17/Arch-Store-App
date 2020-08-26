package com.example.arch_store.adapters


import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.arch_store.R
import com.example.arch_store.offline_db.cart.CartProduct

class CartListHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.cart_row, parent, false)) {

    private var title: TextView? = null
    private var price: TextView? = null
    private var quantity: TextView? = null
    private var size: TextView? = null


    private var image: ImageView

    var increaseQt: ImageButton? = null

    var decreaseQt: ImageButton? = null


    var item: LinearLayout? = null

    //
//
//    //    on initialization
    init {

//        basic info
        title = itemView.findViewById<TextView>(R.id.title)
        price = itemView.findViewById<TextView>(R.id.price)
        quantity = itemView.findViewById<TextView>(R.id.quantity)
        size = itemView.findViewById<TextView>(R.id.size)


//        + -
        decreaseQt = itemView.findViewById<ImageButton>(R.id.decrease_qt)
        increaseQt = itemView.findViewById<ImageButton>(R.id.increase_qt)

//        image
        image = itemView.findViewById<ImageView>(R.id.product_preview)

        item = itemView.findViewById<LinearLayout>(R.id.cart_item)
    }

    //
    fun bind(cartProduct: CartProduct) {

        if (cartProduct.title.length > 20) {
          title?.text =  cartProduct.title.subSequence(0, 20).toString() + ".."
        } else {
            title?.text =    cartProduct.title
        }
        quantity?.text = cartProduct.quantity.toString()
        price?.text = cartProduct.price.toString() + "- SDG"
        size?.text = "Size " + cartProduct.size

        Glide.with(itemView.context)
            .load("http://164.90.225.36${cartProduct.previewImage}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))
            .into(image)

    }


}

interface CartListListener {
    fun onItemClicked(cartProduct: CartProduct)
    fun onQuantityDecreased(cartProduct: CartProduct)
    fun onQuantityIncreased(cartProduct: CartProduct)
}

class CartListAdapter(
    private var products: List<CartProduct>,
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

        var cartProduct: CartProduct = products[position]
        holder.bind(cartProduct = cartProduct)

        holder.item?.setOnClickListener {
            println("clicked")
            cartListListener.onItemClicked(cartProduct)
        }

        holder.increaseQt?.setOnClickListener {
            println("+")
            cartListListener.onQuantityIncreased(cartProduct)
        }
        holder.decreaseQt?.setOnClickListener {
            println("-")
            cartListListener.onQuantityDecreased(cartProduct)
        }


    }

    internal fun setList(products: List<CartProduct>) {
        this.products = products
        notifyDataSetChanged()
    }

}