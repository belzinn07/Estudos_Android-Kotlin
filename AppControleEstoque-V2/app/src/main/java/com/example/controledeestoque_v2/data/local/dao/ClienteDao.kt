package com.example.controledeestoque_v2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controledeestoque_v2.data.local.entities.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert
    suspend fun inserir(cliente: Cliente)
    @Update
    suspend fun atualizar(cliente: Cliente)
    @Delete
    suspend fun deletar(cliente: Cliente)

    @Query("SELECT * FROM clientes ORDER BY nome ASC")
    fun listarClientes(): Flow<List<Cliente>>

}