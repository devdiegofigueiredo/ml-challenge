package com.android.ml_challenge.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.ml_challenge.R
import com.android.ml_challenge.databinding.FragmentGenericErrorBinding

class ProductsErrorFragment : Fragment() {

    private val productsViewModel: ProductsViewModel by activityViewModels()
    private lateinit var binding: FragmentGenericErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_generic_error, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tryAgainButton.setOnClickListener {
            productsViewModel.fetchProducts()
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }
}