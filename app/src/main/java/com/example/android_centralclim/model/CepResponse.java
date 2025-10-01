package com.example.android_centralclim.model;

public class CepResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    // Getters
    public String getLogradouro() { return logradouro; }
    public String getBairro() { return bairro; }
    // Adicione outros getters se precisar
}