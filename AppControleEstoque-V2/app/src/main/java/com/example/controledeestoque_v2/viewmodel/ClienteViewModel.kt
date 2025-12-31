package com.example.controledeestoque_v2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controledeestoque_v2.core.validation.ResultadoValidacao
import com.example.controledeestoque_v2.core.validation.ValidadorDados
import com.example.controledeestoque_v2.core.validation.ValidadorEndereco
import com.example.controledeestoque_v2.data.local.entities.Cliente
import com.example.controledeestoque_v2.data.repository.ClienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(private val repository: ClienteRepository) : ViewModel(){

    private val _mensagem = MutableSharedFlow<String>()
    val mensagem = _mensagem.asSharedFlow()

    val listarClientes: StateFlow<List<Cliente>> = repository.getTodosClientes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun adicionarCliente(cliente: Cliente) {
        viewModelScope.launch {

            if (!validarOuEmitirErro(cliente)) return@launch

            repository.inserir(cliente)
            _mensagem.emit("Cliente adicionado com sucesso!")
        }
        }

    fun  atualizarCliente(clienteExistente: Cliente, clienteNovo: Cliente) {
        viewModelScope.launch {
            val atualizado = clienteExistente.copy(
                nome = clienteNovo.nome,
                email = clienteNovo.email,
                cnpjcpf = clienteNovo.cnpjcpf,
                telefone = clienteNovo.telefone,
                tipo = clienteNovo.tipo,
                endereco = clienteNovo.endereco
            )

            if (!validarOuEmitirErro(atualizado)) return@launch

            repository.atualizar(atualizado)
            _mensagem.emit("Cliente atualizado com sucesso!")
        }
    }

    private suspend fun validarOuEmitirErro(cliente: Cliente): Boolean {
        return when (val resultado = validarEntradas(cliente)) {
            is ResultadoValidacao.Erro -> {
                _mensagem.emit(resultado.mensagem)
                false
            }
            ResultadoValidacao.Sucesso -> true
        }
    }

    private fun validarEntradas(cliente: Cliente): ResultadoValidacao {
        ValidadorDados.validar(cliente)?.let {
            return ResultadoValidacao.Erro(it)
        }

        ValidadorEndereco.validar(cliente.endereco)?.let {
            return ResultadoValidacao.Erro(it)
        }

        return ResultadoValidacao.Sucesso
    }

    fun deletarCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.deletar(cliente)
            _mensagem.emit("Cliente deletado com sucesso!")
        }
    }


}
