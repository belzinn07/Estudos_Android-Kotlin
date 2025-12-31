package com.example.controledeestoque_v2.core.validation

sealed class ResultadoValidacao {
    object Sucesso : ResultadoValidacao()
    data class Erro(val mensagem: String) : ResultadoValidacao()
}
