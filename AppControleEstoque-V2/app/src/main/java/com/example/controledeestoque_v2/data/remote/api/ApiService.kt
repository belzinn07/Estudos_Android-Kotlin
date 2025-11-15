package com.example.controledeestoque_v2.data.remote

import com.example.controledeestoque_v2.data.remote.dto.AuthResponse
import com.example.controledeestoque_v2.data.remote.dto.CadastroRequest
import com.example.controledeestoque_v2.data.remote.dto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("/api/cadastrar")
    suspend fun cadastrar(
        @Body request: CadastroRequest
    ): Response<AuthResponse>
}
