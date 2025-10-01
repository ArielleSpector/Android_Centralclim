package com.example.android_centralclim.network;

import com.example.android_centralclim.model.CepResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("ws/{cep}/json/")
    Call<CepResponse> getAddressByCep(@Path("cep") String cep);
}