package com.example.controledeestoque_v2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controledeestoque_v2.data.local.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Insert
    suspend fun inserir(produto: Produto)
    @Update
    suspend fun atualizar(produto: Produto)
    @Delete
    suspend fun deletar(produto: Produto)

@Query("SELECT * FROM produtos ORDER BY nome ASC")
fun  listarProdutos(): Flow<List<Produto>>

@Query("SELECT SUM(quantidade * precoUnitario) FROM produtos")
fun calcularValorTotalEstoque(): Flow<Double?>
}