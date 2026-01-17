package com.example.controledeestoque_v2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.controledeestoque_v2.ui.auth.CadastroScreen
import com.example.controledeestoque_v2.ui.auth.LoginScreen
import com.example.controledeestoque_v2.ui.cliente.FormClienteView
import com.example.controledeestoque_v2.ui.cliente.TelaClientes
import com.example.controledeestoque_v2.ui.cliente.TelaDetalhesCliente
import com.example.controledeestoque_v2.ui.cliente.TelaFormEndereco
import com.example.controledeestoque_v2.ui.cliente.TelaFormIdentificacao
import com.example.controledeestoque_v2.ui.principal.TelaPrincipal
import com.example.controledeestoque_v2.ui.produto.FormularioProduto
import com.example.controledeestoque_v2.ui.produto.TelaEstoque
import com.example.controledeestoque_v2.viewmodel.ClienteViewModel
import com.example.controledeestoque_v2.viewmodel.ProdutoViewModel
import com.example.controledeestoque_v2.viewmodel.UsuarioViewModel

@Composable
fun AppNavegacao() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.TelaDecisao
    ) {

        composable(Rotas.TelaDecisao) {
            val usuarioViewModel = hiltViewModel<UsuarioViewModel>()
            val usuario by usuarioViewModel.usuarioLogado.collectAsState()

            LaunchedEffect(usuario) {
                if (usuario != null) {
                    navController.navigate(Rotas.TelaPrincipal) {
                        popUpTo(Rotas.TelaDecisao) { inclusive = true }
                    }
                } else {
                    navController.navigate(Rotas.TelaLogin) {
                        popUpTo(Rotas.TelaDecisao) { inclusive = true }
                    }
                }
            }
        }

        composable(Rotas.TelaLogin) {
            val usuarioViewModel = hiltViewModel<UsuarioViewModel>()

            LoginScreen(
                onNavigateToCadastro = { navController.navigate(Rotas.TelaCadastro) },
                onLoginSuccess = {
                    navController.navigate(Rotas.TelaPrincipal) {
                        popUpTo(Rotas.TelaLogin) { inclusive = true }
                    }
                },
                viewModel = usuarioViewModel
            )
        }

        composable(Rotas.TelaCadastro) {
            val usuarioViewModel = hiltViewModel<UsuarioViewModel>()

            CadastroScreen(
                onCadastroSuccess = { navController.popBackStack() },
                viewModel = usuarioViewModel
            )
        }

        composable(Rotas.TelaPrincipal) {
            TelaPrincipal(
                onIrParaEstoque = { navController.navigate(Rotas.TelaEstoque) },
                onIrParaClientes = { navController.navigate(Rotas.TelaCliente) }
            )
        }

        composable(Rotas.TelaEstoque) {
            TelaEstoque(
                onAddProduto = { navController.navigate(Rotas.TelaAdicionarProduto) },
                onEditarProduto = { id ->
                    navController.navigate(Rotas.editarProduto(id))
                }
            )
        }

        composable(Rotas.TelaAdicionarProduto) {
            val produtoViewModel = hiltViewModel<ProdutoViewModel>()

            FormularioProduto(
                viewModel = produtoViewModel,
                produtoId = null,
                onVoltar = { navController.popBackStack() }
            )
        }


        composable(
            route = Rotas.TelaEditarProduto,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")
            val produtoViewModel = hiltViewModel<ProdutoViewModel>()

            FormularioProduto(
                viewModel = produtoViewModel,
                produtoId = id,
                onVoltar = { navController.popBackStack() }
            )
        }

        composable(Rotas.TelaCliente) {
            val viewModel: ClienteViewModel = hiltViewModel()
            TelaClientes(
                viewModel = viewModel,
                onAddCliente = { navController.navigate("formCliente/0") },
                onNavegarParaDetalhesCliente = { id ->
                    navController.navigate("detalhesCliente/$id")
                }
            )
        }

        composable(
            route = Rotas.TelaDetalhesCliente,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val viewModel: ClienteViewModel = hiltViewModel()
            val cliente by viewModel.dadosClienteFormulario.collectAsState()

            LaunchedEffect(id) {
                viewModel.carregarCliente(id)
            }

            TelaDetalhesCliente(
                cliente = cliente,
                onVoltar = { navController.popBackStack() },
                onEditar = { clienteId ->
                    navController.navigate("formCliente/$clienteId")
                }
            )
        }

        composable(
            route = Rotas.TelaFormCliente,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val viewModel: ClienteViewModel = hiltViewModel(backStackEntry)

            LaunchedEffect(id) {
                if (id > 0) viewModel.carregarCliente(id)
                else viewModel.iniciarNovoCadastro()
            }

            FormClienteView(
                viewModel = viewModel,
                onNavegarParaSecao = { secao ->
                    when (secao) {
                        "identificacao" -> {
                            navController.navigate("identificacao")
                        }

                        "endereco" -> {
                            navController.navigate("endereco")
                        }

                        "financeiro" -> {
                            navController.navigate("financeiro")
                        }
                    }
                },
                onVoltar = { navController.popBackStack() }
            )

        }

        composable(
            route = Rotas.TelaIdenticacao
        ) { backStackEntry ->

            val entradaFormulario = remember(backStackEntry) {
                navController.getBackStackEntry("formCliente/{id}")
            }

            val viewModel: ClienteViewModel = hiltViewModel(entradaFormulario)

            TelaFormIdentificacao(
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() }
            )

        }

        composable(
            route = Rotas.TelaEndereco
        ) { backStackEntry ->

            val entradaFormulario = remember(backStackEntry) {
                navController.getBackStackEntry("formCliente/{id}")
            }

            val viewModel: ClienteViewModel = hiltViewModel(entradaFormulario)

            TelaFormEndereco(
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() }
            )

        }

    }

}
