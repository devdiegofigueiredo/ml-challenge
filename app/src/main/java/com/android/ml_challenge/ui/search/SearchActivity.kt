package com.android.ml_challenge.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.ml_challenge.R
import com.android.ml_challenge.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupLifecycleScope()
    }

    private fun setupLifecycleScope() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiSearchState.collect {
                    when (it) {
                        is UiSearchState.Success -> {
                            startProductsActivity()
                        }

                        is UiSearchState.Error -> {
                            binding.searchField.error = it.message
                        }

                        is UiSearchState.Empty -> {
                            binding.searchField.error = getString(R.string.type_valid_product)
                        }

                        UiSearchState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.binding = this
    }

    private fun startProductsActivity() {
    }

    fun onSearchClicked() {
        binding.searchField.requestFocus()
        searchViewModel.search(binding.searchField.text.toString())
    }
}