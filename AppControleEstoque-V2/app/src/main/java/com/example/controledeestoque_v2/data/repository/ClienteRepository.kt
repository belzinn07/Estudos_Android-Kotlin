package com.example.controledeestoque_v2.data.repository

import com.example.controledeestoque_v2.data.local.dao.ClienteDao
import com.example.controledeestoque_v2.data.local.entities.Cliente
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClienteRepository @Inject constructor(private val clienteDao: ClienteDao) {

    fun getTodosClientes(): Flow<List<Cliente>> = clienteDao.listarClientes()
    fun obterClientePorId(id: Int): Cliente? = clienteDao.obterClientePorId(id)

    suspend fun inserir(cliente: Cliente) = clienteDao.inserir(cliente)
    suspend fun atualizar(cliente: Cliente) = clienteDao.atualizar(cliente)
    suspend fun deletar(cliente: Cliente) = clienteDao.deletar(cliente)


}