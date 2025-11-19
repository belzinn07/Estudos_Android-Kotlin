package com.example.controledeestoque_v2.ui.produto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExposedDropdownMenuBox

import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.controledeestoque_v2.data.local.entities.Categoria
import com.example.controledeestoque_v2.data.local.entities.Produto
import com.example.controledeestoque_v2.viewmodel.ProdutoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProduto(
    viewModel: ProdutoViewModel,
    produtoId: Int? = null,
    onVoltar: () -> Unit
) {

    val produtos by viewModel.listarProdutos.collectAsState()

    val produtoExistente = produtos.find { it.id == produtoId }

    var nome by remember { mutableStateOf(produtoExistente?.nome ?: "") }
    var precoUnitario by remember { mutableStateOf(produtoExistente?.precoUnitario?.toString() ?: "") }
    var quantidade by remember { mutableStateOf(produtoExistente?.quantidade?.toString() ?: "") }


    var categoria by remember { mutableStateOf(produtoExistente?.categoria ?: Categoria.OUTROS) }

    var expanded by remember { mutableStateOf(false) }

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.mensagem.collect { mensagem ->
            scope.launch {
                snackbar.showSnackbar(mensagem)
                onVoltar()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (produtoExistente == null) "Adicionar Produto"
                        else "Editar Produto"
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precoUnitario,
                onValueChange = { precoUnitario = it },
                label = { Text("Preço Unitário") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = quantidade,
                onValueChange = { quantidade = it },
                label = { Text("Quantidade") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    readOnly = true,
                    value = categoria.name,
                    onValueChange = {},
                    label = { Text("Categoria") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Categoria.values().forEach { categoriaItem ->
                        DropdownMenuItem(
                            text = { Text(categoriaItem.name) },
                            onClick = {
                                categoria = categoriaItem
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val precoDouble = precoUnitario.toDoubleOrNull() ?: 0.0
                    val quantidadeInt = quantidade.toIntOrNull() ?: 0

                    val novoProduto = Produto(
                        id = produtoExistente?.id ?: 0,
                        nome = nome,
                        precoUnitario = precoDouble,
                        quantidade = quantidadeInt,
                        categoria = categoria
                    )

                    if (produtoExistente == null)
                        viewModel.inserir(novoProduto)
                    else
                        viewModel.atualizar(produtoExistente, novoProduto)
                }
            ) {
                Text(if (produtoExistente == null) "Salvar" else "Atualizar")
            }

        }
    }
}
