package com.tunesapp.tunesbank.activities.api

import com.tunesapp.tunesbank.activities.model.LoginResponse
import com.tunesapp.tunesbank.activities.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Iapi {
    @POST("login")
    fun login(@Body usuario: Usuario): Call<LoginResponse>

    @POST("conta")
    fun cadastrar(@Body usuario: Usuario): Call<Usuario>
}