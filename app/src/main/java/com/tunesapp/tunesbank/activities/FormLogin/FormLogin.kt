package com.tunesapp.tunesbank.activities.FormLogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tunesapp.tunesbank.R
import com.tunesapp.tunesbank.activities.FormCadastro.FormCadastro
import com.tunesapp.tunesbank.activities.api.Iapi
import com.tunesapp.tunesbank.activities.homeBank.HomeBank
import com.tunesapp.tunesbank.activities.model.Usuario
import com.tunesapp.tunesbank.databinding.ActivityFormLoginBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class FormLogin : AppCompatActivity() {

    lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCadastrar.setOnClickListener() {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btnEntrar.setOnClickListener() {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://tunesbank-backend-dwcchyeud5efg8c8.brazilsouth-01.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
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

            call.enqueue(object : retrofit2.Callback<Usuario> {
                override fun onResponse(call: retrofit2.Call<Usuario>, response: retrofit2.Response<Usuario>) {
                    if (response.code() == 200) {
                        binding.txtErro.text = "Login feito com sucesso"
                        binding.txtErro.setTextColor(getColor(R.color.green))
                        val intent = Intent(this@FormLogin, HomeBank::class.java)
                        startActivity(intent)
                    }else{
                        binding.txtErro.text = "Email ou senha incorretos"
                    }
                }

                override fun onFailure(call: retrofit2.Call<Usuario>, t: Throwable) {
                    println("Erro ao fazer login")
                }
            })
        }

    }
}