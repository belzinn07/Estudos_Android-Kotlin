package com.example.controledeestoque_v2.core.validation

import com.example.controledeestoque_v2.core.utils.ValidacaoComercialException
import com.example.controledeestoque_v2.data.local.entities.Endereco

object ValidadorEndereco : Validador<Endereco>{

    override fun validar(dado: Endereco) : String? {
        when{
            dado.logradouro.isBlank() -> throw ValidacaoComercialException("O logradouro não pode estar vazio.")
            dado.numero.isBlank() -> throw ValidacaoComercialException("O número não pode estar vazio.")
            dado.bairro.isBlank() -> throw ValidacaoComercialException("O bairro não pode estar vazio.")
            dado.cidade.isBlank() -> throw ValidacaoComercialException("A cidade não pode estar vazia.")
            dado.cep.isBlank() -> throw ValidacaoComercialException("O CEP não pode estar vazio.")
            dado.uf.isBlank() -> throw ValidacaoComercialException("A UF não pode estar vazia.")
        }

        validarCep(dado)
        return null

    }

    private fun validarCep(endereco: Endereco) {
        val cepNumeros = endereco.cep.filter { it.isDigit() }
        if (cepNumeros.length != 8) {
            throw ValidacaoComercialException("CEP inválido. Deve conter 8 dígitos.")
        }
    }
}