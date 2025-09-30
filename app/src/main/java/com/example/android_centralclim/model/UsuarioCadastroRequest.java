package com.example.android_centralclim.model;

import android.widget.EditText;

public class UsuarioCadastroRequest {

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String cargo;

    // Campos do FormClaCli
    private String telefone; // ou "numero"
    private String cep;
    private String logradouro;
    private String numero_casa; // ou "n_casa"
    private String referencia; // ou "ref"


    public UsuarioCadastroRequest(String nome, String email, String senha, String cpf, String cargo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.cargo = cargo;
    }

    public UsuarioCadastroRequest(String nome, String telefone, String cep, String cpf, String numero_casa, String referencia, String logradouro) {
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.cpf = cpf;
        this.numero_casa = numero_casa;
        this.referencia = referencia;
        this.logradouro = logradouro;
        this.cargo = "Cliente"; // Define o cargo fixo para este formulário
    }

}