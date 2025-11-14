package com.example.controledeestoque_v2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controledeestoque_v2.ui.navigation.Rotas.TelaPrincipal
import com.example.controledeestoque_v2.ui.produto.TelaEstoque

@Composable
fun AppNavegacao(){
    val navController = rememberNavController()

   NavHost(navController, startDestination = Rotas.TelaPrincipal){

       composable(Rotas.TelaPrincipal) {
           TelaPrincipal(
               onIrParaEstoque = {navController.navigate(Rotas.TelaEstoque)}
           )
       }
       composable(Rotas.TelaEstoque) {
           TelaEstoque(
               onAddProduto = {navController.navigate(Rotas.TelaAdicionarProduto)}
           )
       }
       composable(Rotas.TelaAdicionarProduto) {  }
   }



}