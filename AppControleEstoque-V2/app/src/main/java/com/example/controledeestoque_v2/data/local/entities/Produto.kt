package com.example.controledeestoque_v2.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtos")

data class Produto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("nome")
    val nome: String,
    @ColumnInfo("categoria")
    val categoria: Categoria,
    @ColumnInfo("quantidade")
    val quantidade: Int,
    @ColumnInfo("precoUnitario")
    val precoUnitario: Double
)
