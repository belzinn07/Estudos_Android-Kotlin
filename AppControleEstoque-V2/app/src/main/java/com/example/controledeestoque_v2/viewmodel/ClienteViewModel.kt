package com.example.controledeestoque_v2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controledeestoque_v2.core.validation.ResultadoValidacao
import com.example.controledeestoque_v2.core.validation.ValidadorDados
import com.example.controledeestoque_v2.core.validation.ValidadorEndereco
import com.example.controledeestoque_v2.data.local.entities.Cliente
import com.example.controledeestoque_v2.data.local.entities.Endereco
import com.example.controledeestoque_v2.data.repository.ClienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(private val repository: ClienteRepository) : ViewModel(){

    private val _mensagem = MutableSharedFlow<String>()
    val mensagem = _mensagem.asSharedFlow()

    private val _dadosClienteFormulario = MutableStateFlow(Cliente())
    val dadosClienteFormulario = _dadosClienteFormulario.asStateFlow()

    val listarClientes: StateFlow<List<Cliente>> = repository.getTodosClientes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )



    fun iniciarNovoCadastro() {
        _dadosClienteFormulario.value = Cliente()
    }

    fun carregarCliente(id: Int) {
        if (id <= 0) return
        viewModelScope.launch {
            repository.obterClientePorId(id)?.let { _dadosClienteFormulario.value = it }
        }
    }

    fun atualizarRascunho(clienteAtualizado: Cliente) {
        _dadosClienteFormulario.value = clienteAtualizado
    }

    fun salvarCliente(onSucesso: () -> Unit) {
        viewModelScope.launch {
            val clienteParaSalvar = _dadosClienteFormulario.value
            if (!validarOuEmitirErro(clienteParaSalvar)) return@launch

            if (clienteParaSalvar.id == 0) repository.inserir(clienteParaSalvar)
            else repository.atualizar(clienteParaSalvar)

            _mensagem.emit("Cliente salvo com sucesso!")
            onSucesso()
        }
    }


    fun deletarCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.deletar(cliente)
            _mensagem.emit("Cliente deletado com sucesso!")
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
        ValidadorDados.validar(cliente)?.let { return ResultadoValidacao.Erro(it) }
        ValidadorEndereco.validar(cliente.endereco)?.let { return ResultadoValidacao.Erro(it) }
        return ResultadoValidacao.Sucesso
    }
}