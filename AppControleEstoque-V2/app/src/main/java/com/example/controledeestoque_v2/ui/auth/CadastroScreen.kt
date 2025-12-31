package com.example.controledeestoque_v2.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.controledeestoque_v2.ui.state.UiState
import com.example.controledeestoque_v2.viewmodel.UsuarioViewModel

@Composable
fun CadastroScreen(
    onCadastroSuccess: () -> Unit,
    viewModel: UsuarioViewModel = hiltViewModel()
) {
    val usuario by viewModel.cadastroState.collectAsState()
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    LaunchedEffect(usuario) {
        if (usuario is UiState.Success) {
            onCadastroSuccess()
        }
    }



    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Cadastro", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            enabled = usuario !is UiState.Loading,
            onClick = {
                if (nome.isNotBlank() && email.isNotBlank() && senha.isNotBlank()) {
                    viewModel.cadastrar(nome, email, senha)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            when (usuario) {

                UiState.Idle -> Text("Cadastrar")

                is UiState.Loading -> CircularProgressIndicator(Modifier.size(22.dp))

                is UiState.Error ->{
                    Text(
                        text = (usuario as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is UiState.Success -> Text("Cadastrar")

            }

        }
    }
}
