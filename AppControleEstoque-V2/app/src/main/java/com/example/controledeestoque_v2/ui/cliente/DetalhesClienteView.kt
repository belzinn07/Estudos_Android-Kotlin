package com.example.controledeestoque_v2.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.controledeestoque_v2.core.utils.Formatador
import com.example.controledeestoque_v2.data.local.entities.Cliente

import androidx.compose.ui.tooling.preview.Preview
import com.example.controledeestoque_v2.data.local.entities.Endereco
import com.example.controledeestoque_v2.data.local.entities.TipoPessoa
import com.example.controledeestoque_v2.data.local.entities.CategoriaCliente
import com.example.controledeestoque_v2.ui.theme.ControleDeEstoqueV2Theme
import java.math.BigDecimal
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDetalhesCliente(
    cliente: Cliente,
    onVoltar: () -> Unit,
    onEditar: (Int) -> Unit
){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dados do Cliente") },
                navigationIcon = {
                    IconButton(onClick = { onVoltar() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },

                actions = {
                    IconButton(onClick = { onEditar(cliente.id) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar Cliente"
                        )
                    }
                }


            )

        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditar(cliente.id) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            SecaoInformacoesCliente(titulo = "Identificação") {
                LinhaDeInformacao(label = "Nome", valor = cliente.nome)
                LinhaDeInformacao(label = "Tipo", valor = cliente.tipo.name)
                LinhaDeInformacao(label = "CNPJ/CPF", valor = cliente.cnpjcpf)
                LinhaDeInformacao(label = "Registro Geral", valor = cliente.registrogeral)
            }

            SecaoInformacoesCliente(titulo ="Endereco") {
                val endereco = cliente.endereco

                LinhaDeInformacao(label = "UF", valor = endereco.uf)
                LinhaDeInformacao(label = "CEP", valor = endereco.cep)
                LinhaDeInformacao(label = "Cidade", valor = endereco.cidade)
                LinhaDeInformacao(label = "Logradouro", valor = endereco.logradouro)
                LinhaDeInformacao(label = "Número", valor = endereco.numero)
                LinhaDeInformacao(label = "Bairro", valor = endereco.bairro)

            }

            SecaoInformacoesCliente(titulo = "Financeiro") {
                LinhaDeInformacao(label = "Limite de Crédito", valor = Formatador.moeda(cliente.limitecredito))
                LinhaDeInformacao(label = "Categoria", valor = cliente.categoria.name)
                LinhaDeInformacao(label = "Número de Compras", valor = cliente.numeroDeCompras.toString())
                LinhaDeInformacao(label = "Data de Cadastro", valor = Formatador.data(cliente.dataCadastro))
            }

        }

    }
}

@Composable
fun SecaoInformacoesCliente(titulo: String, conteudo: @Composable  ColumnScope.() -> Unit) {
    Column {
        Text(
            text = titulo,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        Column(content = conteudo)
    }
}

@Composable
fun LinhaDeInformacao(label: String, valor: String){
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text("$label: ", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(valor, style = MaterialTheme.typography.bodyMedium)
    }
}





// 1. Criamos um objeto "Mock" (falso) para teste
val clienteMock = Cliente(
    id = 1,
    nome = "João da Silva Sauro",
    tipo = TipoPessoa.FISICA,
    cnpjcpf = "123.456.789-00",
    registrogeral = "MG-12.345.678",
    telefone = "(31) 98888-7777",
    email = "joao.sauro@email.com",
    endereco = Endereco(
        uf = "MG",
        cep = "30123-456",
        cidade = "Belo Horizonte",
        logradouro = "Avenida Amazonas",
        numero = "1500",
        bairro = "Centro"

    ),
    limitecredito = BigDecimal("5000.00"),
    categoria = CategoriaCliente.OURO, // Ajuste conforme seus nomes no Enum
    numeroDeCompras = 12,
    valorDaCompra = 450.0,
    dataCadastro = LocalDateTime.now(),
    ativo = true
)

// 2. Preview para o Modo Claro
@Preview(showBackground = true, name = "Modo Claro", showSystemUi = true)
@Composable
fun PreviewTelaDetalhesLight() {
    ControleDeEstoqueV2Theme(darkTheme = false) {
        TelaDetalhesCliente(
            cliente = clienteMock,
            onVoltar = {},
            onEditar = {}
        )
    }
}

// 3. Preview para o Modo Escuro
@Preview(
    showBackground = true,
    name = "Modo Escuro",
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewTelaDetalhesDark() {
    ControleDeEstoqueV2Theme(darkTheme = true) {
        TelaDetalhesCliente(
            cliente = clienteMock,
            onVoltar = {},
            onEditar = {}

        )
    }
}