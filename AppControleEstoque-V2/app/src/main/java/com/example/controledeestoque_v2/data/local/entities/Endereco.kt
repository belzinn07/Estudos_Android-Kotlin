package com.example.controledeestoque_v2.data.local.entities

import androidx.room.Entity

@Entity(tableName = "endereco")
data class Endereco(

    val cep: String,
    val logradouro: String,
    val numero: String,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val uf: String

)
