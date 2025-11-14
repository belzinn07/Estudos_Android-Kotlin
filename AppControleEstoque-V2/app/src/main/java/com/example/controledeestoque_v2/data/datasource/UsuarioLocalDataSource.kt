package com.example.controledeestoque_v2.data.datasource

import Usuario
import com.example.controledeestoque_v2.data.local.dao.UsuarioDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioLocalDataSource @Inject constructor(private val dao: UsuarioDao) {

    suspend fun salvarUsuario(usuario: Usuario) = dao.inserir(usuario)

    fun getUsuarioLogado(): Flow<Usuario> = dao.getUsuarioLogado()

    suspend fun deletarUsuario() = dao.deletarUsuarioLogado()

}