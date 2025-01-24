package com.tunesapp.tunesbank.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tunesapp.tunesbank.R
import com.tunesapp.tunesbank.View.UsuarioView
import com.tunesapp.tunesbank.activities.homeBank.HomeBank
import com.tunesapp.tunesbank.activities.token.TokenManager
import com.tunesapp.tunesbank.api.Iapi
import com.tunesapp.tunesbank.databinding.ActivityFormDepositoBinding
import com.tunesapp.tunesbank.model.DepositoModel
import com.tunesapp.tunesbank.model.UsuarioModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class FormDeposito : AppCompatActivity() {

    lateinit var binding: ActivityFormDepositoBinding
    lateinit var tokenManager: TokenManager
    lateinit var api: Iapi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormDepositoBinding.inflate(layoutInflater)
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


        binding.btnDepositar.setOnClickListener {
            depositar()
        }

    }

    fun depositar() {
        val deposito = DepositoModel(binding.edtValor.text.toString().toDouble())
        val token = tokenManager.getToken()
        val call = api.depositar("Bearer $token", deposito)
        call.enqueue(object : retrofit2.Callback<UsuarioView> {
            override fun onResponse(call: retrofit2.Call<UsuarioView>, response: retrofit2.Response<UsuarioView>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@FormDeposito, HomeBank::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@FormDeposito, "Erro ao depositar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<UsuarioView>, t: Throwable) {
                Toast.makeText(this@FormDeposito, "Erro ao depositar", Toast.LENGTH_SHORT).show()
            }
        })
    }

}