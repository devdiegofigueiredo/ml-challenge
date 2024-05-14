package com.android.ml_challenge.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.ml_challenge.databinding.AttributesItemViewBinding
import com.android.ml_challenge.model.Product

class AttributesAdapter(private val attributes: List<Product.Attributes>) :
    RecyclerView.Adapter<AttributesAdapter.AttributesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesViewHolder {
        return AttributesViewHolder(
            AttributesItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = attributes.size

    override fun onBindViewHolder(holder: AttributesViewHolder, position: Int) {
        holder.bind(attributes[position])
    }

    class AttributesViewHolder(private val view: AttributesItemViewBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(attribute: Product.Attributes) {
            view.run {
                attributeNameText.text = attribute.name
                attributeValueText.text = attribute.valueName
            }
        }
    }
}