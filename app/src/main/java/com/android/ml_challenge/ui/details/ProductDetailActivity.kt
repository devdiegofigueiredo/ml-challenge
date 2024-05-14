package com.android.ml_challenge.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ml_challenge.R
import com.android.ml_challenge.databinding.ActivityProductDetailBinding
import com.android.ml_challenge.model.Product
import com.android.ml_challenge.ui.details.di.productDetailModule
import com.android.ml_challenge.ui.util.formatMoney
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class ProductDetailActivity : AppCompatActivity() {

    private val productDetailViewModel: ProductDetailViewModel by viewModel()
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoin()
        setupBinding()
        setupToolbar()
        setupLifecycleScope()
        setupBuyNow()

        productDetailViewModel.onProductDataReceived(intent)
    }

    override fun onDestroy() {
        unloadKoinModules(listOf(productDetailModule))
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.takeIf { it.itemId == android.R.id.home }?.apply { finish() }
        return super.onOptionsItemSelected(item)
    }

    private fun setupLifecycleScope() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productDetailViewModel.uiProductDetailState.collect {
                    when (it) {
                        is UiProductDetailState.Loading -> {
                            setLoadingVisibility(true)
                        }

                        is UiProductDetailState.Success -> {
                            product = it.products
                            setLoadingVisibility(false)
                            setupAttributes()
                            setupNewProduct()
                            setupProductImage()
                            setupProductInformation()
                            binding.mainContainer.visibility = View.VISIBLE
                        }

                        is UiProductDetailState.Error -> {
                            setLoadingVisibility(false)
                        }
                    }
                }
            }
        }
    }

    private fun loadKoin() {
        loadKoinModules(listOf(productDetailModule))
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.binding = this
    }

    private fun setupToolbar() {
        binding.productToolbar.title = getString(R.string.product_details)
        setSupportActionBar(binding.productToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setLoadingVisibility(shouldShow: Boolean) {
        if (shouldShow) {
            binding.productDetailLoad.visibility = View.VISIBLE
        } else {
            binding.productDetailLoad.visibility = View.GONE
        }
    }

    private fun setupAttributes() {
        binding.attributesList.run {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = AttributesAdapter(product.attributes)
        }
    }

    private fun setupBuyNow() {
        binding.buyNowButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.permalink))
            startActivity(browserIntent)
        }
    }

    private fun setupNewProduct() {
        if (product.condition == "new") {
            binding.run {
                checkImage.visibility = View.VISIBLE
                newProductText.visibility = View.VISIBLE
            }
        }
    }

    private fun setupProductImage() {
        Picasso.get()
            .load(product.thumbnail)
            .into(binding.productImage)
    }

    private fun setupProductInformation() {
        binding.run {
            availableQuantityText.text =
                product.availableQuantity.plus(getString(R.string.available_units))
            sellerNameText.text = product.seller.nickname
            productPriceText.text = product.price.formatMoney()
            productTitleText.text = product.title

        }
    }
}