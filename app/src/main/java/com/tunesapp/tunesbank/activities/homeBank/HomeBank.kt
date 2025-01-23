package com.tunesapp.tunesbank.activities.homeBank

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tunesapp.tunesbank.activities.FormLogin.FormLogin
import com.tunesapp.tunesbank.activities.token.TokenManager
import com.tunesapp.tunesbank.databinding.ActivityHomeBankBinding

class HomeBank : AppCompatActivity() {

    lateinit var binding: ActivityHomeBankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from Intent
        val nome = intent.getStringExtra("nome")
        val email = intent.getStringExtra("email")
        val agencia = intent.getStringExtra("agencia")
        val conta = intent.getStringExtra("conta")
        val saldo = intent.getDoubleExtra("saldo", 0.0)
        val dataCriacao = intent.getStringExtra("data_criacao")

        // Set data to views
        binding.tvNome.text = "Olá, $nome"
//        binding.txtEmail.text = email
        binding.tvAgencia.text = "Agencia: $agencia"
        binding.tvConta.text = "Conta: $conta"
        binding.tvSaldo.text = "Saldo: R$ $saldo"
//        binding.txtDataCriacao.text = dataCriacao

        binding.btnSair.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val tokenManager = TokenManager(this)
        tokenManager.deleteToken()
        val intent = Intent(this, FormLogin::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}