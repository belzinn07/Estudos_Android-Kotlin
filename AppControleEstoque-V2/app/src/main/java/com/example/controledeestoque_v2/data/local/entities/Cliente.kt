package com.example.controledeestoque_v2.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String = "",
    val tipo: TipoPessoa = TipoPessoa.FISICA,
    val cnpjcpf: String = "",
    val registrogeral: String = "",
    val telefone: String = "",
    val email: String = "",

    @Embedded
    val endereco: Endereco = Endereco(),

    val limitecredito: BigDecimal = BigDecimal.ZERO,
    val categoria: CategoriaCliente = CategoriaCliente.BRONZE,
    val numeroDeCompras: Int = 0,
    val valorDaCompra: Double = 0.0,
    val dataCadastro: LocalDateTime = LocalDateTime.now(),
    val ativo: Boolean = true,

)

