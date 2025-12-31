package com.example.controledeestoque_v2.data.local.entities

enum class TipoPessoa {
    FISICA("RG"),
    JURIDICA("Inscrição Estadual");

    val documento: String

    constructor(documento: String) {
        this.documento = documento
    }

}