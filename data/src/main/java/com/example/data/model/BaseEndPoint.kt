package com.example.data.model

import com.google.gson.annotations.SerializedName

data class BaseEndPointResponse<T>(
    @SerializedName("status")val status: String,
    @SerializedName("totalResults")val totalResults: Int,
    @SerializedName("articles") var data: T? = null
)
