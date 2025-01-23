package com.tunesapp.tunesbank.activities.FormLogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tunesapp.tunesbank.R
import com.tunesapp.tunesbank.activities.FormCadastro.FormCadastro
import com.tunesapp.tunesbank.activities.api.Iapi
import com.tunesapp.tunesbank.activities.homeBank.HomeBank
import com.tunesapp.tunesbank.activities.model.LoginResponse
import com.tunesapp.tunesbank.activities.model.Usuario
import com.tunesapp.tunesbank.activities.token.TokenManager
import com.tunesapp.tunesbank.databinding.ActivityFormLoginBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class FormLogin : AppCompatActivity() {

    lateinit var binding: ActivityFormLoginBinding
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenManager = TokenManager(this)

        if (tokenManager.getToken() != null) {
            val intent = Intent(this, HomeBank::class.java)
            startActivity(intent)
        }

        binding.txtCadastrar.setOnClickListener() {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btnEntrar.setOnClickListener() {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl("https://tunesbank-backend-dwcchyeud5efg8c8.brazilsouth-01.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            val api = retrofit.create<Iapi>()

            val email = binding.edtEmail.text.toString()
            if (email.isEmpty()) {
                binding.txtErro.text = "Email não pode ser vazio"
                return@setOnClickListener
            }
            val senha = binding.edtSenha.text.toString()
            if (senha.isEmpty()) {
                binding.txtErro.text = "Senha não pode ser vazia"
                return@setOnClickListener
            }

            val usuario = Usuario()
            usuario.email = email
            usuario.senha = senha

            val call = api.login(usuario)

            call.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        val usuarioLogado = loginResponse?.usuario
                        tokenManager.saveToken(loginResponse?.token!!)
                        val intent = Intent(this@FormLogin, HomeBank::class.java).apply {
                            putExtra("nome", usuarioLogado?.nome)
                            putExtra("email", usuarioLogado?.email)
                            putExtra("agencia", usuarioLogado?.agencia)
                            putExtra("conta", usuarioLogado?.conta)
                            putExtra("saldo", usuarioLogado?.saldo)
                            putExtra("data_criacao", usuarioLogado?.data_criacao)
                        }
                        startActivity(intent)
                    } else {
                        binding.txtErro.text = "Email ou senha incorretos"
                    }
                }

                override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                    println("Erro ao fazer login")
                }
            })
        }

    }
}
