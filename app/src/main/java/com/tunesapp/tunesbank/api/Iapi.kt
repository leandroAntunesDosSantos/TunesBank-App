package com.tunesapp.tunesbank.api

import com.tunesapp.tunesbank.View.UsuarioView
import com.tunesapp.tunesbank.model.DepositoModel
import com.tunesapp.tunesbank.model.LoginResponse
import com.tunesapp.tunesbank.model.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Iapi {
    @POST("login")
    fun login(@Body usuario: UsuarioModel): Call<LoginResponse>

    @POST("conta")
    fun cadastrar(@Body usuario: UsuarioModel): Call<UsuarioModel>

    @GET("conta")
    fun getConta(@Header("Authorization") token: String): Call<UsuarioView>

    @POST("depositar")
    fun depositar(@Header("Authorization") token: String, @Body deposito: DepositoModel): Call<UsuarioView>
}

