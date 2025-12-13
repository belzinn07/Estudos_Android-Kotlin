package com.example.controledeestoque_v2.data.repository

import com.example.controledeestoque_v2.core.datastore.GerenciadorDeToken
import com.example.controledeestoque_v2.data.datasource.UsuarioLocalDataSource
import com.example.controledeestoque_v2.data.datasource.UsuarioRemotoDataSouce
import com.example.controledeestoque_v2.data.local.entities.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioRemotoDataSource: UsuarioRemotoDataSouce,
    private val usuarioLocalDataSource: UsuarioLocalDataSource,
    private val gerenciadorDeToken: GerenciadorDeToken
) {

    fun login(email: String, senha: String): Flow<Usuario?> =
        executarFluxoUsuario(
            chamadaRemota = { usuarioRemotoDataSource.login(email, senha) },
            montarUsuario = { response ->
                Usuario(
                    email = email,
                    token = response.token
                )
            }
        )


    fun cadastrar(nome: String, email: String, senha: String): Flow<Usuario?> =
        executarFluxoUsuario(
            chamadaRemota = { usuarioRemotoDataSource.cadastrar(nome, email, senha) },
            montarUsuario = { response ->
                Usuario(
                    nome = nome,
                    email = email,
                    senha = senha,
                    token = response.token
                )
            }
        )


    private inline fun <T> executarFluxoUsuario(
        crossinline chamadaRemota: suspend () -> T?,
        crossinline montarUsuario: (T) -> Usuario
    ): Flow<Usuario?> = flow {

        val resposta = chamadaRemota()

        if (resposta != null) {
            val usuario = montarUsuario(resposta)
            usuarioLocalDataSource.salvarUsuario(usuario)

            emit(usuario)
        } else {
            emit(null)
        }
    }


    fun getUsuarioLogado(): Flow<Usuario?> {
        return usuarioLocalDataSource.getUsuarioLogado()
    }

    suspend fun logout() {
        usuarioLocalDataSource.deletarUsuario()
        gerenciadorDeToken.limparToken()
    }
}
