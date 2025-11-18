package com.example.controledeestoque_v2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controledeestoque_v2.ui.auth.CadastroScreen
import com.example.controledeestoque_v2.ui.auth.LoginScreen
import com.example.controledeestoque_v2.ui.navigation.Rotas.TelaPrincipal
import com.example.controledeestoque_v2.ui.principal.TelaPrincipal
import com.example.controledeestoque_v2.ui.produto.TelaEstoque

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
                onNavigateToCadastro = {
                    navController.navigate(Rotas.TelaCadastro)
                },
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
                onCadastroSuccess = {
                    navController.popBackStack()
                },
                viewModel = usuarioViewModel
            )
        }


        composable(Rotas.TelaPrincipal) {
            TelaPrincipal(
                onIrParaEstoque = { navController.navigate(Rotas.TelaEstoque) }
            )
        }
        
        composable(Rotas.TelaEstoque) {
            TelaEstoque(
                onAddProduto = { navController.navigate(Rotas.TelaAdicionarProduto) }
            )
        }


        composable(Rotas.TelaAdicionarProduto) { }
    }
}
