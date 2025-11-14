package com.example.controledeestoque_v2.data.local.dao

import Usuario
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT*FROM tabela_usuario LIMIT 1")
    fun getUsuarioLogado(): Flow<Usuario>

    @Query("DELETE FROM tabela_usuario")
    suspend fun deletarUsuarioLogado();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(usuario: Usuario)

}