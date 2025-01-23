package com.tunesapp.tunesbank.activities.model

data class Usuario(
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var agencia: String? = null,
    var conta: String? = null,
    var saldo: Double? = null,
    var data_criacao: String? = null
)

data class LoginResponse(
    val usuario: Usuario,
    val token: String
)