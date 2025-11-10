package com.example.controledeestoque_v2.ui.produto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.controledeestoque_v2.ui.theme.ControleDeEstoqueV2Theme

class MainScreen : ComponentActivity{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControleDeEstoqueV2Theme {

            }
        }
    }


}