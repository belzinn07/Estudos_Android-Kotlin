    package com.example.controledeestoque_v2.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.controledeestoque_v2.data.local.entities.TipoPessoa
import com.example.controledeestoque_v2.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaFormIdentificacao (
    viewModel: ClienteViewModel = hiltViewModel(),
    onVoltar: () -> Unit
){

    val cliente by viewModel.dadosClienteFormulario.collectAsState()

    val nomeAtual = cliente.nome
    val emailAtual = cliente.email
    val telefoneAtual = cliente.telefone
    val tipoAtual = cliente.tipo
    val cnpjcpfAtual = cliente.cnpjcpf
    val registrogeralAtual = cliente.registrogeral

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

        ){

            OutlinedTextField(
                 value = nomeAtual,
                 onValueChange ={ novoNome ->
                     viewModel.atualizarRascunho(cliente.copy(nome = novoNome))
                 },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Tipo de Pessoa", style = MaterialTheme.typography.titleSmall)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                TipoPessoa.entries.forEach { tipo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .selectable(
                                selected = (tipo == tipoAtual),
                                onClick = { viewModel.atualizarRascunho(cliente.copy(tipo = tipo)) },
                                role = Role.RadioButton
                            )
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = (tipo == tipoAtual),
                            onClick = null
                        )
                        Text(text = tipo.name, modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }

            OutlinedTextField(
                value = cnpjcpfAtual,
                onValueChange ={ novoDocumento ->
                    viewModel.atualizarRascunho(cliente.copy(cnpjcpf = novoDocumento))
                },
                label = { Text(if (tipoAtual == TipoPessoa.JURIDICA) "CNPJ" else "CPF") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = registrogeralAtual,
                onValueChange ={ novoRG ->
                    viewModel.atualizarRascunho(cliente.copy(cnpjcpf = novoRG))
                },
                label = { Text("Registro Geral") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefoneAtual,
                onValueChange ={ novoTelefone ->
                    viewModel.atualizarRascunho(cliente.copy(cnpjcpf = novoTelefone))
                },
                label = { Text("Telefone") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = emailAtual,
                onValueChange = { novoEmail->
                    viewModel.atualizarRascunho(cliente.copy(email = novoEmail))
                },
                label = {Text("Email")},
                modifier = Modifier.fillMaxWidth()
                )


        }


    }


}

