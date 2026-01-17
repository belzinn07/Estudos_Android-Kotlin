package com.example.controledeestoque_v2.core.utils

object Formatador {

    fun moeda(valor: java.math.BigDecimal): String {
        val ptBr = java.util.Locale("pt", "BR")
        return java.text.NumberFormat.getCurrencyInstance(ptBr).format(valor)
    }

    fun data(data: java.time.LocalDateTime): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return data.format(formatter)
    }
}