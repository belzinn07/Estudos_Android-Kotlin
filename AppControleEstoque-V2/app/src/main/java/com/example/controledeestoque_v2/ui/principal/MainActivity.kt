package com.example.controledeestoque_v2.ui.principal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

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