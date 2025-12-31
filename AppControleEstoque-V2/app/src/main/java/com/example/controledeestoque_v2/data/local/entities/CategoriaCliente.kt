package com.example.controledeestoque_v2.data.local.entities

enum class CategoriaCliente {
    OURO, PRATA, BRONZE;

    companion object {

        fun fromCompras(numeroDeCompras: Int): CategoriaCliente {
            return when {
                numeroDeCompras >= 10 -> OURO
                numeroDeCompras >= 5 -> PRATA
                else -> BRONZE
            }
        }

        fun aplicarDesconto(categoria: CategoriaCliente, valorCompra: Double): Double {
            return when (categoria) {
                OURO -> valorCompra * 0.95
                PRATA -> valorCompra * 0.97
                BRONZE -> valorCompra
            }
        }
    }
}