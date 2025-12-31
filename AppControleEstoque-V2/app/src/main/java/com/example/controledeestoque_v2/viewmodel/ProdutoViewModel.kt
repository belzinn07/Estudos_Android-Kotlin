package com.example.controledeestoque_v2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.controledeestoque_v2.data.repository.ProdutoRepository
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.example.controledeestoque_v2.core.validation.ValidarEntradasProduto
import com.example.controledeestoque_v2.data.local.entities.Produto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProdutoViewModel @Inject constructor(private val  repository: ProdutoRepository): ViewModel(){

    private val _mensagem = MutableSharedFlow<String>()
    val mensagem = _mensagem.asSharedFlow()


    val listarProdutos: StateFlow<List<Produto>> = repository.getTodosProdutos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )
    val valorTotalEstoque: StateFlow<Double?> =
        repository.getValorTotalEstoque()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )

    fun inserir(produto: Produto) {
        viewModelScope.launch {
            val erro = ValidarEntradasProduto.validar(produto)
            if (erro != null) {
                _mensagem.emit(erro)
                return@launch
            }

                repository.inserir(produto)
            _mensagem.emit("Produto adicionado com sucesso!")


        }
    }

    fun atualizar(produtoExistente: Produto, produtoNovo: Produto) {
        viewModelScope.launch {
            val atualizado = produtoExistente.copy(
                nome = produtoNovo.nome,
                precoUnitario = produtoNovo.precoUnitario,
                quantidade = produtoNovo.quantidade,
                categoria = produtoNovo.categoria
            )


            repository.atualizar(atualizado)
            _mensagem.emit("Produto atualizado com sucesso! ")
        }
    }

    fun deletar(produto: Produto) {
        viewModelScope.launch {
            repository.deletar(produto)
        }
    }
}

