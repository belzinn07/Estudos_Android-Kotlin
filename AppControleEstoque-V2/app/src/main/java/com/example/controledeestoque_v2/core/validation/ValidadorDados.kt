package com.example.controledeestoque_v2.core.validation

import com.example.controledeestoque_v2.core.utils.ValidacaoComercialException
import com.example.controledeestoque_v2.data.local.entities.Cliente
import com.example.controledeestoque_v2.data.local.entities.TipoPessoa

object ValidadorDados : Validador<Cliente>  {

    override fun validar(dado: Cliente)  : String?{
        when {

            dado.nome.isBlank() -> throw ValidacaoComercialException("O nome não pode estar vazio")
            dado.cnpjcpf.isBlank() -> throw ValidacaoComercialException("O CNPJ/CPF não pode estar vazio.")
            dado.email.isBlank() -> throw ValidacaoComercialException("O email não pode estar vazio.")
            dado.telefone.isBlank() ->throw ValidacaoComercialException("O telefone não pode estar vazio.")
            !dado.email.contains("@") -> throw ValidacaoComercialException("Email inválido")

        }

        validarCpfOuCnpj(dado)
        validarRg(dado)
        validarTelefone(dado)
        return null

    }



    fun validarCpfOuCnpj(cliente: Cliente) {

        val documento = cliente.cnpjcpf.filter { it.isDigit() }


        when (cliente.tipo) {
            TipoPessoa.FISICA -> require(documento.length == 11){"O CPF deve conter 11 digitos"}
            TipoPessoa.JURIDICA ->require(documento.length == 14){"O CNPJ deve conter 14 digitos"}

        }
    }

    fun validarRg(cliente: Cliente){

        val documento = cliente.registrogeral.filter { it.isDigit() }

        if (documento.isBlank()){
            throw ValidacaoComercialException("Para pessoa ${cliente.tipo} o $documento é obrigatório")

        }
    }

    private fun validarTelefone(cliente: Cliente) {
        val regex = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$".toRegex()

        if (!cliente.telefone.matches(regex)) {
            throw ValidacaoComercialException("Telefone inválido")
        }
    }





}