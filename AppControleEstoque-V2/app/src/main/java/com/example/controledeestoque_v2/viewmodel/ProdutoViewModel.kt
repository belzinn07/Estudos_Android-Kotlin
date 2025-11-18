package com.example.controledeestoque_v2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.controledeestoque_v2.data.repository.ProdutoRepository
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.example.controledeestoque_v2.data.local.entities.Produto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProdutoViewModel @Inject constructor(private val  repository: ProdutoRepository): ViewModel(){


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

    fun inserir(produto: Produto) = viewModelScope.launch {
        if (produto.nome.isNotBlank() || produto.quantidade > 0 || produto.precoUnitario > 0)
            repository.inserir(produto)

    }

    fun atualizar(produto: Produto) = viewModelScope.launch {
        repository.atualizar(produto)
    }

    fun deletar(produto: Produto) = viewModelScope.launch {
        repository.deletar(produto)
    }

}

