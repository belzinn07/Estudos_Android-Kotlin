package com.example.controledeestoque_v2.ui.produto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controledeestoque_v2.data.model.Produto
import com.example.controledeestoque_v2.ui.theme.ControleDeEstoqueV2Theme
import com.example.controledeestoque_v2.viewmodel.ProdutoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControleDeEstoqueV2Theme {
                TelaPricipal(onAddProduto = {});

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPricipal(
    onAddProduto: () -> Unit,
    viewModel: ProdutoViewModel = viewModel()
) {
    val produtos by viewModel.listarProdutos.collectAsState(initial = emptyList())
    val totalEstoque by viewModel.valorTotalEstoque.collectAsState(initial = 0.0)


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Controle de Estoque")
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = onAddProduto) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Produto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            ValorTotalEstoque(totalEstoque)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Produtos", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            ListaDeProdutos(produtos)
        }
    }
}

@Composable
fun ValorTotalEstoque(totalEstoque: Double?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Valor total em estoque:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "R$ %.2f".format(totalEstoque ?: 0.0),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun ListaDeProdutos(produtos: List<Produto>) {
    if (produtos.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Nenhum produto cadastrado.")
            Text("Clique no botão '+' para adicionar.")
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(produtos) { produto ->
                ProdutoItem(produto)
            }
        }
    }
}

@Composable
fun ProdutoItem(produto: Produto) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(produto.nome, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))

            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "R$ %.2f".format(produto.precoUnitario),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Quantidade: ${produto.quantidade}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
