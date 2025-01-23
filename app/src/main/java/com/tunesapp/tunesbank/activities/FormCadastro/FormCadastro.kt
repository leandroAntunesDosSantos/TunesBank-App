package com.tunesapp.tunesbank.activities.FormCadastro

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tunesapp.tunesbank.R
import com.tunesapp.tunesbank.activities.api.Iapi
import com.tunesapp.tunesbank.activities.homeBank.HomeBank
import com.tunesapp.tunesbank.activities.model.Usuario
import com.tunesapp.tunesbank.databinding.ActivityFormCadastroBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class FormCadastro : AppCompatActivity() {
    lateinit var binding: ActivityFormCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener() {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://tunesbank-backend-dwcchyeud5efg8c8.brazilsouth-01.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create<Iapi>()
            val nome = binding.edtNome.text.toString()
            if (nome.isEmpty()) {
                binding.txtErro.text = "Nome não pode ser vazio"
                return@setOnClickListener
            }
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
            val senha2 = binding.edtSenhaConfirmacao.text.toString()
            if (senha2.isEmpty()) {
                binding.txtErro.text = "Confirmação de senha não pode ser vazia"
                return@setOnClickListener
            }
            if (senha != senha2) {
                binding.txtErro.text = "Senhas não conferem"
                return@setOnClickListener
            }
            val usuario = Usuario()
            usuario.nome = nome
            usuario.email = email
            usuario.senha = senha

            val call = api.cadastrar(usuario)

            call.enqueue(object : retrofit2.Callback<Usuario> {
                override fun onResponse(
                    call: retrofit2.Call<Usuario>,
                    response: retrofit2.Response<Usuario>
                ) {
                    if (response.code() == 200) {
                        binding.txtErro.text = "Cadastro feito com sucesso"
                        binding.txtErro.setTextColor(getColor(R.color.green))
                        val intent = Intent(this@FormCadastro, HomeBank::class.java)
                        startActivity(intent)
                    } else {
                        binding.txtErro.text = "Erro ao cadastrar"
                    }
                }

                override fun onFailure(call: retrofit2.Call<Usuario>, t: Throwable) {
                    println("Erro ao cadastrar")
                }
            })
        }
    }
}