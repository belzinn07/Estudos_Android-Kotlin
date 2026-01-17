object Rotas {
    const val TelaDecisao = "decisao"
    const val TelaLogin = "login"
    const val TelaCadastro = "cadastro"
    const val TelaPrincipal = "principal"

    const val TelaEstoque = "estoque"

    const val TelaAdicionarProduto = "formProduto"
    const val TelaEditarProduto = "formProduto/{id}"

    const val  TelaCliente = "cliente"
    const val TelaFormCliente = "formCliente/{id}"
    const val TelaIdenticacao = "identificacao"
    const val TelaEndereco = "endereco"
    const val TelaFinanceiro = "financeiro"
    const val TelaDetalhesCliente = "detalhesCliente/{id}"


    fun editarProduto(id: Int) = "formProduto/$id"
}
