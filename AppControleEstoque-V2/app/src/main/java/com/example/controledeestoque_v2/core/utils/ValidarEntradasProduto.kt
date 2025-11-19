package com.example.controledeestoque_v2.core.utils

import com.example.controledeestoque_v2.data.local.entities.Produto

object ValidarEntradasProduto {
    fun validar(produto: Produto): String? {

        if (produto.nome.isBlank())
            return "O nome não pode estar vazio."

        if (produto.precoUnitario <= 0)
            return "O preço deve ser maior que zero."

        if (produto.quantidade <= 0)
            return "A quantidade deve ser maior que zero."


        return null
    }
}