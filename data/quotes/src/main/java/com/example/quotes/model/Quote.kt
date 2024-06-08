package com.example.quotes.model

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("author")
    val author: String? = null
)