package com.tunesapp.tunesbank.activities.homeBank

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tunesapp.tunesbank.activities.FormLogin.FormLogin
import com.tunesapp.tunesbank.activities.token.TokenManager
import com.tunesapp.tunesbank.api.Iapi
import com.tunesapp.tunesbank.databinding.ActivityHomeBankBinding
import com.tunesapp.tunesbank.View.UsuarioView
import com.tunesapp.tunesbank.activities.FormDeposito
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HomeBank : AppCompatActivity() {

    lateinit var binding: ActivityHomeBankBinding
    lateinit var tokenManager: TokenManager
    lateinit var api: Iapi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenManager = TokenManager(this)

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
        api = retrofit.create()

        if (!tokenManager.hasToken()) {
            logout()
        } else {
            getConta()
        }

        binding.btnSair.setOnClickListener {
            logout()
        }

        binding.ivDeposito.setOnClickListener {
            deposito()
        }
    }

    private fun getConta() {
        val token = "Bearer ${tokenManager.getToken()}"
        val call = api.getConta(token)
        call.enqueue(object : retrofit2.Callback<UsuarioView> {
            override fun onResponse(call: retrofit2.Call<UsuarioView>, response: retrofit2.Response<UsuarioView>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    binding.tvNome.text = "Olá, ${usuario?.nome}"
                    // utlizar implementação futura
                    //binding.txtEmail.text = usuario?.email
                    binding.tvAgencia.text = "Agência: ${usuario?.agencia}"
                    binding.tvConta.text = "Conta: ${usuario?.conta}"
                    binding.tvSaldo.text = "Saldo: R$ ${usuario?.saldo}"
                    // utlizar implementação futura
                    //binding.txtDataCriacao.text = usuario?.data_criacao
                }
            }

            override fun onFailure(call: retrofit2.Call<UsuarioView>, t: Throwable) {
                logout()
            }
        })
    }

    private fun logout() {
        tokenManager.deleteToken()
        val intent = Intent(this, FormLogin::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (!tokenManager.hasToken()) {
            logout()
        }
    }

    fun deposito() {
        val intent = Intent(this, FormDeposito::class.java)
        startActivity(intent)
    }
}
