package com.android.ml_challenge.ui.products

import android.content.Intent
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
import com.android.ml_challenge.databinding.ActivityProductsBinding
import com.android.ml_challenge.model.Product
import com.android.ml_challenge.ui.details.ProductDetailActivity
import com.android.ml_challenge.ui.products.di.productsModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ProductsActivity : AppCompatActivity() {

    private val productsViewModel: ProductsViewModel by viewModel()
    private lateinit var binding: ActivityProductsBinding
    private val productsAdapter = ProductsAdapter(::loadMoreItems, ::onItemViewClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoin()
        setupLifecycleScope()
        setupBinding()
        interceptProductName()
        setupRecyclerView()
        productsViewModel.fetchProducts()
    }

    override fun onDestroy() {
        unloadKoinModules(listOf(productsModule))
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.takeIf { it.itemId == android.R.id.home }?.apply { finish() }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        binding.binding = this
    }

    private fun interceptProductName() {
        intent.extras?.getString("productName")?.apply {
            setupToolbar(this)
            productsViewModel.setProductName(this)
        }
    }

    private fun loadKoin() {
        loadKoinModules(listOf(productsModule))
    }

    private fun setupLifecycleScope() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productsViewModel.uiProductsState.collect {
                    when (it) {

                        is UiProductsState.Error -> {
                            setLoadingVisibility(false)
                            showErrorScreen()
                        }

                        is UiProductsState.Empty -> {
                            setLoadingVisibility(false)
                            showEmptyProductView()
                        }

                        is UiProductsState.Loading -> {
                            setLoadingVisibility(true)
                        }

                        is UiProductsState.Success -> {
                            setupProductsAdapter(it.products)
                        }
                    }
                }
            }
        }
    }

    private fun setupProductsAdapter(products: List<Product>) {
        binding.run {
            setLoadingVisibility(false)
            productsAdapter.addProducts(products)

            if (productsList.visibility == View.GONE) {
                productsList.visibility = View.VISIBLE
            }
        }
    }

    private fun showErrorScreen() {
        binding.fragmentErrorContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_error_container, ProductsErrorFragment()).commit()
    }

    private fun showEmptyProductView() {
        binding.run {
            emptyContainer.visibility = View.VISIBLE
            searchNewProductButton.setOnClickListener { finish() }
        }
    }

    private fun setupToolbar(product: String) {
        binding.productsToolbar.title = product
        setSupportActionBar(binding.productsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        binding.productsList.run {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = productsAdapter
        }
    }

    private fun loadMoreItems() {
        productsViewModel.fetchMoreProducts()
    }

    private fun onItemViewClickListener(product: Product) {
        Intent(baseContext, ProductDetailActivity::class.java).run {
            putExtra("product", product)
            startActivity(this)
        }
    }

    private fun setLoadingVisibility(shouldShow: Boolean) {
        if (shouldShow) {
            binding.productsLoad.visibility = View.VISIBLE
        } else {
            binding.productsLoad.visibility = View.GONE
        }
    }
}