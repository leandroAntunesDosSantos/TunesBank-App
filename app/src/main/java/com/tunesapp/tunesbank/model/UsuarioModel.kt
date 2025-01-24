package com.tunesapp.tunesbank.model

data class UsuarioModel(
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var agencia: String? = null,
    var conta: String? = null,
    var saldo: Double? = null,
    var data_criacao: String? = null
)

data class LoginResponse(
    val usuario: UsuarioModel,
    val token: String
)

data class DepositoModel(
    val valor: Double
)