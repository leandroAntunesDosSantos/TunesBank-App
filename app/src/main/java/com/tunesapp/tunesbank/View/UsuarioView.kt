package com.tunesapp.tunesbank.View

data class UsuarioView(
    var nome: String = "",
    var email: String = "",
    var agencia: String? = null,
    var conta: String? = null,
    var saldo: Double? = null,
    var data_criacao: String? = null
)

