package com.example.controledeestoque_v2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controledeestoque_v2.data.local.entities.Usuario
import com.example.controledeestoque_v2.data.repository.UsuarioRepository
import com.example.controledeestoque_v2.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Usuario?>>(UiState.Idle)
    val loginState: StateFlow<UiState<Usuario?>> = _loginState

    private val _cadastroState = MutableStateFlow<UiState<Usuario?>>(UiState.Idle)
    val cadastroState: StateFlow<UiState<Usuario?>> = _cadastroState
    
    private val _usuarioLogado = MutableStateFlow<Usuario?>(null)
    val usuarioLogado: StateFlow<Usuario?> = _usuarioLogado

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val usuario = repository.login(email, senha).first()
                Log.d("LOGIN_VM", "Resultado do login: $usuario")
                
                if (usuario != null) {
                    _usuarioLogado.value = usuario
                    _loginState.value = UiState.Success(usuario)
                } else {
                    _loginState.value = UiState.Error("Credenciais inválidas ou falha na conexão.")
                }
            } catch (e: HttpException) {
                Log.e("LOGIN_VM", "Erro HTTP: ${e.code()}", e)
                _loginState.value = UiState.Error("Erro no servidor: ${e.code()}")
            } catch (e: Exception) {
                Log.e("LOGIN_VM", "Erro inesperado", e)
                _loginState.value = UiState.Error("Falha na conexão. O servidor está rodando?")
            }
        }
    }

    fun cadastrar(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            _cadastroState.value = UiState.Loading
            try {
                val usuario = repository.cadastrar(nome, email, senha).first()
                Log.d("CADASTRO_VM", "Resultado do cadastro: $usuario")
                
                if (usuario != null) {
                    _usuarioLogado.value = usuario
                    _cadastroState.value = UiState.Success(usuario)
                } else {
                    _cadastroState.value = UiState.Error("Não foi possível realizar o cadastro.")
                }
            } catch (e: HttpException) {
                Log.e("CADASTRO_VM", "Erro HTTP: ${e.code()}", e)
                _cadastroState.value = UiState.Error("Erro no servidor: ${e.code()}")
            } catch (e: Exception) {
                Log.e("CADASTRO_VM", "Erro inesperado", e)
                _cadastroState.value = UiState.Error("Falha na conexão com o servidor.")
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
