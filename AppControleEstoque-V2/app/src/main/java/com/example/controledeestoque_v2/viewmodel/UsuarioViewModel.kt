package com.example.controledeestoque_v2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controledeestoque_v2.data.local.entities.Usuario
import com.example.controledeestoque_v2.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: UsuarioRepository
) : ViewModel() {


    private val _usuarioLogado = MutableStateFlow<Usuario?>(null)
    val usuarioLogado: StateFlow<Usuario?> = _usuarioLogado

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            repository.login(email, senha).collect { usuario ->
                _usuarioLogado.value = usuario
            }
        }
    }

    fun cadastrar(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            repository.cadastrar(nome, email, senha).collect { usuario ->
                _usuarioLogado.value = usuario
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _usuarioLogado.value = null
        }
    }
}