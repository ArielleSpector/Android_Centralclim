package com.example.android_centralclim.network;

import com.example.android_centralclim.model.Cliente;
import com.example.android_centralclim.model.Servico;
import com.example.android_centralclim.model.Tecnico;
import com.example.android_centralclim.model.UsuarioCadastroRequest;
import com.example.android_centralclim.model.UsuarioResponse;
import retrofit2.http.DELETE;
import retrofit2.http.Path;


import java.util.List; //

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @POST("api/usuarios/cadastrar")
    Call<UsuarioResponse> cadastrarUsuario(@Body UsuarioCadastroRequest usuario);


    @GET("api/clientes")
    Call<List<Cliente>> getClientes(@Query("nome") String nome);


    @GET("api/tecnicos")
    Call<List<Tecnico>> getTecnicos(@Query("nome") String nome);


    @GET("api/servicos/recentes")
    Call<List<Servico>> getServicosRecentes();


    @DELETE("api/clientes/{id}")
    Call<Void> deleteCliente(@Path("id") Long id);

    @DELETE("api/tecnicos/{id}")
    Call<Void> deleteTecnico(@Path("id") Long id);
}