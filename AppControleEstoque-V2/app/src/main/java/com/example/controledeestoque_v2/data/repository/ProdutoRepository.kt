package com.example.controledeestoque_v2.data.repository

import com.example.controledeestoque_v2.data.local.entities.Produto
import com.example.controledeestoque_v2.data.local.dao.ProdutoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProdutoRepository @Inject constructor(private val dao: ProdutoDao){


    fun getTodosProdutos(): Flow<List<Produto>> = dao.listarProdutos()
    fun getValorTotalEstoque(): Flow<Double?> = dao.calcularValorTotalEstoque()

    suspend fun inserir(produto: Produto) = dao.inserir(produto)
    suspend fun atualizar(produto: Produto) = dao.atualizar(produto)
    suspend fun deletar(produto: Produto) = dao.deletar(produto)

}