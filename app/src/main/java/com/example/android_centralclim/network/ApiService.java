package com.example.android_centralclim.network;

import com.example.android_centralclim.model.UsuarioCadastroRequest;
import com.example.android_centralclim.model.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // O valor de @POST deve ser o caminho do seu endpoint de cadastro na API
    @POST("api/usuarios/cadastrar")
    Call<UsuarioResponse> cadastrarUsuario(@Body UsuarioCadastroRequest usuario);
}