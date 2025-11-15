package com.example.controledeestoque_v2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("sucess")
    val success: Boolean,

    @SerializedName("token")
    val token: String?,

    @SerializedName("mensagem")
    val mensagem: String?
)