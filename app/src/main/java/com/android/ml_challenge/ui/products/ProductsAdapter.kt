package com.android.ml_challenge.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.ml_challenge.R
import com.android.ml_challenge.databinding.ProductItemViewBinding
import com.android.ml_challenge.model.Product
import com.android.ml_challenge.ui.util.formatMoney
import com.squareup.picasso.Picasso
import kotlin.reflect.KFunction1

class ProductsAdapter(
    private val loadMoreItems: () -> Unit,
    private val onItemViewClickListener: KFunction1<Product, Unit>
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    var products = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.run {
            bind(products[position])
            itemView.setOnClickListener { onItemViewClickListener(products[position]) }
        }
        if (position == products.size - 5) {
            loadMoreItems()
        }
    }

    fun addProducts(products: List<Product>) {
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    class ProductsViewHolder(private val view: ProductItemViewBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(product: Product) {
            view.run {
                productTitle.text = product.title
                productPrice.text = product.price.formatMoney()
                productAvailabilityQuantity.text =
                    setupAvailabilityQuantity(product.availableQuantity, view.root.context)
                loadImage(view.productImage, product.thumbnail)
            }
        }

        private fun loadImage(imageView: ImageView, imageUrl: String) {
            Picasso.get()
                .load(imageUrl)
                .into(imageView)
        }

        private fun setupAvailabilityQuantity(availableQuantity: String, context: Context): String {
            return context.getString(R.string.availability_quantity) + availableQuantity
        }
    }
}