package com.example.controledeestoque_v2.core.validation

import com.example.controledeestoque_v2.data.local.entities.Produto

object ValidarEntradasProduto {
    fun validar(produto: Produto): String? =

         when{
             produto.nome.isBlank() -> "O nome não pode estar vazio."
             produto.precoUnitario <= 0 -> "O preço deve ser maior que zero."
             produto.quantidade <= 0 -> "A quantidade deve ser maior que zero."

                else -> null
         }

}