package com.example.arch_store.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentProductBinding
import com.example.arch_store.databinding.FragmentProductDetailBinding
import com.example.arch_store.models.Product
import com.example.arch_store.utils.DataState
import com.example.arch_store.view_models.ProductStateEvent
import com.example.arch_store.view_models.ProductViewModel
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val images = arrayListOf(
        R.drawable.p_1,
        R.drawable.p_2, R.drawable.p_3
    )
    private val viewModel: ProductViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args: ProductDetailFragmentArgs by navArgs()
        var productId = args.productId

        println("Product id $productId")

        var binding: FragmentProductDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)

        viewModel.setStateEvent(ProductStateEvent.GetProductById, productId = productId)

        subscribeObservers(binding)

        return binding.root
    }


    private fun setCarousal(binding: FragmentProductDetailBinding, images: List<String>) {
        val carouselView = binding.carousalView

        carouselView.apply {
            size = images.size
            resource = R.layout.image_carousel_item
            autoPlay = true



            indicatorAnimationType = IndicatorAnimationType.THIN_WORM
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                // Example here is setting up a full image carousel
                val imageView = view.findViewById<ImageView>(R.id.product_preview)

                Glide.with(requireActivity())
                    .load("http://164.90.225.36${images.get(position)}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //3
                    .transform(CenterCrop(), RoundedCorners(15))
                    .into(imageView)
//                use glide here
            }
            // After you finish setting up, show the CarouselView
            show()
        }
    }

    private fun setBasicInfo(binding: FragmentProductDetailBinding, product: Product) {
        binding.title.text = product.title
        binding.description.text = product.description
        if(product.discount>0){
            binding.price.text = product.price.toString()+"- SDG"
            var  percentageToNum = product.price*product.discount;


            binding.newPrice.text = (product.price-(percentageToNum/100)).toString()+"- SDG"

        }else{
            binding.newPrice.text = product.price.toString()+"- SDG"
//            show new price only
            binding.discount.visibility =View.GONE
            binding.newPrice.visibility =View.VISIBLE

        }

        var sizes = binding.sizes
        sizes.onItemSelectedListener = this
        var sizesList = product.sizes
        val adapter = ArrayAdapter(
            requireActivity(), // Context
            android.R.layout.simple_spinner_item, // Layout
            sizesList // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizes.adapter = adapter

    }


    private fun subscribeObservers(binding: FragmentProductDetailBinding) {
        viewModel.product.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Product> -> {
                    binding.loading.visibility = View.GONE
                    binding.noProduct.visibility = View.GONE

                    println("we have data" + dataState.data.toString())

                    setCarousal(binding, dataState.data.images)
                    setBasicInfo(binding, dataState.data)
                }
                is DataState.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.detail.visibility = View.GONE

                    binding.noProduct.visibility = View.VISIBLE


                    println("Error to get cats.")


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.noProduct.visibility = View.GONE
                    println("Loading...")
                }
            }
        })
    }


    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item
        print("Item selected")
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


}