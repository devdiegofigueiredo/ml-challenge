package com.android.ml_challenge.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("price") val price: Double,
    @SerializedName("available_quantity") val availableQuantity: String,
    @SerializedName("seller") val seller: Seller,
    @SerializedName("attributes") val attributes: List<Attributes>
) {
    data class Seller(
        @SerializedName("id") val id: String,
        @SerializedName("nickname") val nickname: String
    )

    data class Attributes(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String
    )
}
