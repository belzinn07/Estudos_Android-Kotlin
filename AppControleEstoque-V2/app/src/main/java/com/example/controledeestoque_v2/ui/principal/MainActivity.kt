package com.example.controledeestoque_v2.ui.principal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.controledeestoque_v2.ui.navigation.AppNavegacao
import com.example.controledeestoque_v2.ui.theme.ControleDeEstoqueV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ControleDeEstoqueV2Theme {
                AppNavegacao()
            }
        }
    }
}

@Composable
TelaPrincipal(){

}

@Composable
fun CardView(
    titulo:String,
    textoBotao: String,
    Icon: ImageVector,
    onClick: () -> Unit

    ){



}