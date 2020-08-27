package com.example.arch_store.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
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
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.offline_db.favourtites.FavProduct
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.MoneyFormatter
import com.example.arch_store.view_models.*
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var product: Product
    private lateinit var sizes: Spinner
    private val viewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val favViewModel: FavViewModel by viewModels()

    @Inject
    lateinit var moneyFormatter: MoneyFormatter

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

        subscribeObserversToGetProductDetail(binding)
        subscribeObserversToAddProductCart(binding)
        subscribeObserversToAddProductToFav(binding)

        binding.addToBag.setOnClickListener {
//            add product to cart
            var newPrice: Float = 0f
            if (product.discount > 0) {


                var percentageToNum = product.price * product.discount;


                newPrice =
                    product.price - (percentageToNum / 100)

            } else {

                newPrice =
                    product.price
            }
                cartViewModel.setStateEvent(
                CartStateEvent.InsertCartProduct,
                cartProduct = CartProduct(
                    productId = product.id,
                    title = product.title,
                    price =newPrice,
                    discount = product.discount,
                    previewImage = product.previewImage,
                    quantity = 1,
                    size = product.sizes[sizes.selectedItemPosition]
                )
            )
        }

        binding.addToFav.setOnClickListener {

            var newPrice: Float = 0f
            if (product.discount > 0) {


                var percentageToNum = product.price * product.discount;


                newPrice =
                    product.price - (percentageToNum / 100)

            } else {

                newPrice =
                    product.price

            }
            favViewModel.setStateEvent(
                FavStateEvent.InsertFavProduct,
                favProduct = FavProduct(
                    productId = product.id,
                    title = product.title,
                    price = newPrice,
                    discount = product.discount,
                    previewImage = product.previewImage,
                    quantity = 1,
                    size = product.sizes[sizes.selectedItemPosition]
                )
            )
//            add to fav
        }

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
        if (product.discount > 0) {

            binding.price.text = moneyFormatter.getReadableMoney(product.price)

            var percentageToNum = product.price * product.discount;


            binding.newPrice.text =
                moneyFormatter.getReadableMoney(product.price - (percentageToNum / 100))

        } else {
            binding.newPrice.text = moneyFormatter.getReadableMoney(product.price)
//            show new price only
            binding.discount.visibility = View.GONE
            binding.newPrice.visibility = View.VISIBLE

        }

        sizes = binding.sizes
        sizes.selectedItemPosition
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


    private fun subscribeObserversToGetProductDetail(binding: FragmentProductDetailBinding) {
        viewModel.product.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Product> -> {
                    product = dataState.data
                    binding.loading.visibility = View.GONE
                    binding.noProduct.visibility = View.GONE
                    binding.addToBag.visibility = View.VISIBLE
                    println("we have data" + dataState.data.toString())

                    setCarousal(binding, dataState.data.images)
                    setBasicInfo(binding, dataState.data)
                }
                is DataState.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.detail.visibility = View.GONE
                    binding.addToBag.visibility = View.GONE
                    binding.noProduct.visibility = View.VISIBLE


                    println("Error to get cats.")


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.noProduct.visibility = View.GONE
                    binding.addToBag.visibility = View.GONE
                    println("Loading...")
                }
            }
        })
    }

    private fun subscribeObserversToAddProductCart(binding: FragmentProductDetailBinding) {
        cartViewModel.productCreated.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Long> -> {
                    println("Ok product added to cart" + dataState.data.toString())
                    Toast.makeText(requireContext(), "choose added to cart", Toast.LENGTH_SHORT)
                        .show();
                }
                is DataState.Error -> {
                    println("Error while add product to cart")
                    Toast.makeText(
                        requireContext(),
                        "Error while add product to cart",
                        Toast.LENGTH_LONG
                    ).show();

                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }

    private fun subscribeObserversToAddProductToFav(binding: FragmentProductDetailBinding) {
        favViewModel.productCreated.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Long> -> {
                    println("Ok product added to favourites" + dataState.data.toString())
                    Toast.makeText(
                        requireContext(),
                        "choose added to favourites",
                        Toast.LENGTH_SHORT
                    )
                        .show();
                }
                is DataState.Error -> {
                    println("Error while add product to cart")
                    Toast.makeText(
                        requireContext(),
                        "Error while add product to cart",
                        Toast.LENGTH_LONG
                    ).show();

                }
                is DataState.Loading -> {
                    print("Loading..")
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