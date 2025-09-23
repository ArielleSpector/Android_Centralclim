package com.example.android_centralclim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // Importação adicionada para View.OnClickListener
import android.widget.Button; // Importação corrigida
import android.widget.EditText;
import android.widget.TextView; // Importação adicionada para o texto de cadastro
import android.widget.Toast;  // Importação adicionada

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormLogin extends AppCompatActivity {

    // Simulação de credenciais corretas (substitua por sua lógica de banco de dados real)
    private static final String CORRECT_EMAIL = "user@example.com";
    private static final String CORRECT_PASSWORD = "password123";

    private EditText editTextEmail;    // Nome da variável atualizado para corresponder ao ID comum
    private EditText editTextPassword; // Nome da variável atualizado
    private Button buttonLogin;        // Tipo corrigido e nome da variável atualizado
    private TextView textViewRegister;   // Para o link de "Cadastre-se"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita o Edge-to-Edge display
        setContentView(R.layout.activity_form_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        iniciarComponentes();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });


        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });
    }

    private void iniciarComponentes() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister); // ID do XML para o texto de cadastro
    }

    private void validarLogin() {
        String emailInserido = editTextEmail.getText().toString().trim(); // .trim() remove espaços em branco
        String senhaInserida = editTextPassword.getText().toString();

        if (emailInserido.isEmpty() || senhaInserida.isEmpty()) {
            Toast.makeText(FormLogin.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }


        if (emailInserido.equals(CORRECT_EMAIL) && senhaInserida.equals(CORRECT_PASSWORD)) {

            Toast.makeText(FormLogin.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(FormLogin.this, "Email ou senha incorretos.", Toast.LENGTH_LONG).show();
        }
    }

    private void abrirTelaCadastro() {
        Intent intent = new Intent(FormLogin.this, FormCadastro.class);
        startActivity(intent);
    }

}