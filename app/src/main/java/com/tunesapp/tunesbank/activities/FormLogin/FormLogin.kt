package com.tunesapp.tunesbank.activities.FormLogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tunesapp.tunesbank.R
import com.tunesapp.tunesbank.activities.FormCadastro.FormCadastro
import com.tunesapp.tunesbank.databinding.ActivityFormLoginBinding

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
    }
}