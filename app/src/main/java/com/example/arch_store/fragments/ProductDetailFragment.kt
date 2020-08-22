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
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentProductDetailBinding
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType


class ProductDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val images = arrayListOf(
        R.drawable.p_1,
        R.drawable.p_2, R.drawable.p_3
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding: FragmentProductDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
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
                imageView.setImageDrawable(resources.getDrawable(images[position]))
            }
            // After you finish setting up, show the CarouselView
            show()
        }

//        carouselView.enableSnapping(false)
        var sizes = binding.sizes
        sizes.onItemSelectedListener = this


        var sizesList = arrayListOf<String>("Size 1", "Size 2", "Size 4")
        val adapter = ArrayAdapter(
            activity!!.applicationContext, // Context
            android.R.layout.simple_spinner_item, // Layout
            sizesList // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizes.adapter = adapter




        return binding.root
    }


    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item
        print("Item selected")
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


}