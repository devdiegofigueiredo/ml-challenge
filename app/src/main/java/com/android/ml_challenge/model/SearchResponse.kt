package com.android.ml_challenge.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("paging") val paging: Paging,
    @SerializedName("results") val results: List<Product>
) {

    data class Paging(
        @SerializedName("total") val total: Int,
        @SerializedName("primary_results") val primaryResults: Int,
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int
    )
}
