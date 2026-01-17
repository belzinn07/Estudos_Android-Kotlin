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

    fun login(email: String, senha: String): Flow<Usuario?> = flow {
        val response = usuarioRemotoDataSource.login(email, senha)

        if (response != null && response.success) {
            val usuario = Usuario(email = email, token = response.token)
            usuarioLocalDataSource.salvarUsuario(usuario)
            emit(usuario)
        } else {

            emit(null)
        }
    }

    fun cadastrar(nome: String, email: String, senha: String): Flow<Usuario?> = flow {
        val response = usuarioRemotoDataSource.cadastrar(nome, email, senha)

        if (response != null && response.success) {
            val usuario = Usuario(nome = nome, email = email, senha = senha, token = response.token)
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
