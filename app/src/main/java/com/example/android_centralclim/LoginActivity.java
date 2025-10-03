package com.example.android_centralclim;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText; // Importe esta classe

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    // A LINHA ABAIXO FOI MODIFICADA
    private TextInputEditText passwordEditText; // Alterado de EditText para TextInputEditText
    private Button loginButton;
    private TextView forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("O campo de e-mail é obrigatório.");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Por favor, insira um e-mail válido.");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            // Agora, para definir o erro, usamos o layout pai para um melhor visual
            passwordEditText.setError("O campo de senha é obrigatório.");
            passwordEditText.requestFocus();
            return;
        }

        // Autenticação com o banco de dados
        authenticateUser(email, password);
    }

    private void authenticateUser(String email, String password) {
        Toast.makeText(this, "Verificando credenciais...", Toast.LENGTH_SHORT).show();

        // Sua lógica de autenticação com SQLite permanece a mesma
        /*
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // ... resto do seu código de banco de dados
        */
    }

    // Sua classe DatabaseHelper (comentada) permanece a mesma
    /*
    public class DatabaseHelper extends SQLiteOpenHelper {
        // ...
    }
    */
}