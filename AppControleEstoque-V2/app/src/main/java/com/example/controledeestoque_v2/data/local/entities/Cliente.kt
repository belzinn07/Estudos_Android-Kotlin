package com.example.controledeestoque_v2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String,
    val tipo: TipoPessoa,
    val cnpjcpf: String,
    val registrogeral: String,
    val telefone: String,
    val email: String,
    val endereco: Endereco,
    val limitecredito: BigDecimal = BigDecimal.ZERO,
    val categoria: CategoriaCliente,
    val numeroDeCompras: Int = 0,
    val valorDaCompra: Double,
    val dataCadastro: LocalDateTime = LocalDateTime.now(),
    val ativo: Boolean = true,

)


