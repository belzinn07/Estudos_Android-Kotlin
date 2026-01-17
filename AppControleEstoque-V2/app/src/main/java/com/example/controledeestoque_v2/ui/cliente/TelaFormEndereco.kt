package com.example.controledeestoque_v2.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.controledeestoque_v2.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaFormEndereco (
    viewModel: ClienteViewModel = hiltViewModel(),
    onVoltar: () -> Unit
){

    val cliente by viewModel.dadosClienteFormulario.collectAsState()

    val cepAtual = cliente.endereco.cep
    val logradouroAtual =   cliente.endereco.logradouro
    val numeroAtual = cliente.endereco.numero
    val bairroAtual = cliente.endereco.bairro
    val cidadeAtual = cliente.endereco.cidade
    val ufAtual = cliente.endereco.uf

    val snackbar = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.mensagem.collect { mensagem ->
            snackbar.showSnackbar(mensagem)
            onVoltar()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = { Text("Formulário de Clientes") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }

            )
        }
    ){

        Column (
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            OutlinedTextField(
                value = cepAtual,
                onValueChange ={ novoCep->
                    viewModel.atualizarRascunho(cliente.copy(nome = novoCep))
                },
                label = { Text("CEP") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = logradouroAtual,
                onValueChange ={ novoLogradouro ->
                    viewModel.atualizarRascunho(cliente.copy(nome = novoLogradouro))
                },
                label = { Text("Logradouro") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numeroAtual,
                onValueChange ={ novoNumero ->
                    viewModel.atualizarRascunho(cliente.copy(nome = novoNumero))
                },
                label = { Text("Número") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bairroAtual,
                onValueChange ={ novoBairro ->
                    viewModel.atualizarRascunho(cliente.copy(nome = novoBairro))
                },
                label = { Text("Bairro") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cidadeAtual,
                onValueChange ={ novaCidade ->
                    viewModel.atualizarRascunho(cliente.copy(nome = novaCidade))
                },
                label = { Text("Cidade") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ufAtual,
                onValueChange ={ novoUF ->
                    viewModel.atualizarRascunho(cliente.copy(nome = novoUF))
                },
                label = { Text("UF") },
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}