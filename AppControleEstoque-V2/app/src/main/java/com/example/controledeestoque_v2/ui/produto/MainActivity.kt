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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val produtos by viewModel.listarProdutos.collectAsState()
    val totalEstoque by viewModel.valorTotalEstoque.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },

       floatingActionButton = {
           FloatingActionButton(onClick = onAddProduto) {
               Icon(Icons.Default.Add, contentDescription = "Adicionar Produto")
           }
       }
    ) {Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {  }


    }

}

@Composable
fun ValorTotalEstoque(totalEstoque: Double) {
    Card( modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Valor total em estoque".format(totalEstoque),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF001183)
            )
        }
    }
}


@Composable
fun ListaDeProdutos(produtos: List<Produto>) {
    LazyColumn {
        items(produtos) { produto ->
            ProdutoItem(produto)

        }

    }
}

@Composable
fun ProdutoItem(produto: Produto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column( modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(produto.nome, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Preço ${produto.precoUnitario}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Quantidade ${produto.quantidade}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Categoria ${produto.categoria}", style = MaterialTheme.typography.bodyMedium)



        }

    }
}













