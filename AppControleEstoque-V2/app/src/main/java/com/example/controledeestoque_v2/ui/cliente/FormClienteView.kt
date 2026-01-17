@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.controledeestoque_v2.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.controledeestoque_v2.core.utils.Formatador
import com.example.controledeestoque_v2.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FormClienteView(
    viewModel: ClienteViewModel,
    onNavegarParaSecao: (String) -> Unit,
    onVoltar: () -> Unit

){

    val clientes by viewModel.dadosClienteFormulario.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = { Text(if (clientes.id == 0) "Novo Cliente" else "Editar Cliente") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }

            )
        },

        bottomBar = {
            Button(
                onClick = { viewModel.salvarCliente(onSucesso = onVoltar) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {Text("Salvar Cliente") }
        },


    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

          CardSecao(
            titulo = "Dados Pessoais",
            resumo = if (clientes.nome.isNotBlank()) clientes.nome else "Não preenchido",
            onClick = {onNavegarParaSecao("identificacao")}
          )

          CardSecao(
              titulo = "Endereço",
              resumo = if (clientes.endereco.logradouro.isBlank()) "Pendente"
              else "${clientes.endereco.cidade}, ${clientes.endereco.uf}",
              onClick = {onNavegarParaSecao("endereco")}
          )

          CardSecao(
              titulo = "Financeiro",
              resumo = "Limite: ${Formatador.moeda(clientes.limitecredito)}",
              onClick = { onNavegarParaSecao("financeiro") }
          )

        }

    }
}

@Composable
fun CardSecao(titulo: String,resumo: String, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = resumo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}
