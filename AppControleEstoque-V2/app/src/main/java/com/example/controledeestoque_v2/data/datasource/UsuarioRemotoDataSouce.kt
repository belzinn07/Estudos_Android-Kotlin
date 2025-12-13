package com.example.controledeestoque_v2.data.datasource

import android.util.Log
import com.example.controledeestoque_v2.core.datastore.GerenciadorDeToken
import com.example.controledeestoque_v2.data.remote.ApiService
import com.example.controledeestoque_v2.data.remote.dto.AuthResponse
import com.example.controledeestoque_v2.data.remote.dto.CadastroRequest
import com.example.controledeestoque_v2.data.remote.dto.LoginRequest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRemotoDataSouce @Inject constructor(
    private val apiService: ApiService,
    private val gerenciadorDeToken: GerenciadorDeToken
)  {

    suspend fun login(email: String, senha: String): AuthResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val resposta = apiService.login(LoginRequest(email, senha))

                if (resposta.isSuccessful && resposta.body() != null) {
                    val corpo = resposta.body()!!

                    gerenciadorDeToken.salvarToken(corpo.token.toString())
                    Log.i("LOGIN_API", "Login OK → Token salvo: ${corpo.token}")

                    corpo
                } else {
                    Log.e("LOGIN_API", "Erro HTTP: ${resposta.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("LOGIN_API", "Falha: ${e.message}")
                null
            }
        }
    }



    suspend fun cadastrar(nome: String, email: String, senha: String): AuthResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val resposta = apiService.cadastrar(CadastroRequest(nome, email, senha))

                if (resposta.isSuccessful && resposta.body() != null) {
                    val corpo = resposta.body()!!

                    gerenciadorDeToken.salvarToken(corpo.token.toString())
                    Log.i("CADASTRO_API", "Cadastro OK → Token salvo: ${corpo.token}")

                    corpo
                } else {
                    Log.e("CADASTRO_API", "Erro HTTP: ${resposta.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("CADASTRO_API", "Falha: ${e.message}")
                null
            }
        }


    }}
