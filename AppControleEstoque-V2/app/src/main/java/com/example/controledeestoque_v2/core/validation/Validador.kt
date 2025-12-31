package com.example.controledeestoque_v2.core.validation

interface Validador<T> {
    fun validar(dado: T) : String?
}