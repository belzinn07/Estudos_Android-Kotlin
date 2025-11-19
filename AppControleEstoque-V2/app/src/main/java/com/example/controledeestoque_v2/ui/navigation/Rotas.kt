object Rotas {
    const val TelaDecisao = "decisao"
    const val TelaLogin = "login"
    const val TelaCadastro = "cadastro"
    const val TelaPrincipal = "principal"
    const val TelaEstoque = "estoque"

    const val TelaAdicionarProduto = "formProduto"
    const val TelaEditarProduto = "formProduto/{id}"

    fun editarProduto(id: Int) = "formProduto/$id"
}
