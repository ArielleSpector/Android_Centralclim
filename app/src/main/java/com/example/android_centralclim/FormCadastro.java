package com.example.android_centralclim;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_centralclim.model.UsuarioCadastroRequest;
import com.example.android_centralclim.model.UsuarioResponse;
import com.example.android_centralclim.network.ApiService;
import com.example.android_centralclim.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormCadastro extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editConfirmarSenha, editCpf;
    private AutoCompleteTextView autoCompleteCargo;
    private Button buttonCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // O nome do seu layout pode ser diferente, ajuste se necessário
        setContentView(R.layout.activity_form_cadastro);

        // Inicializar os componentes da tela
        editNome = findViewById(R.id.nomepessoa);
        editEmail = findViewById(R.id.editTextEmail);
        editSenha = findViewById(R.id.editTextPassword);
        editConfirmarSenha = findViewById(R.id.editTextPassword2);
        editCpf = findViewById(R.id.cpf);
        autoCompleteCargo = findViewById(R.id.cargo);
        buttonCadastro = findViewById(R.id.buttonCadastro);

        // Configurar o AutoCompleteTextView para o cargo
        String[] cargos = new String[]{"Técnico", "Gestor", "Cliente"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cargos);
        autoCompleteCargo.setAdapter(adapter);

        // Configurar o clique do botão
        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        // 1. Coletar os dados dos campos
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String confirmarSenha = editConfirmarSenha.getText().toString().trim();
        String cpf = editCpf.getText().toString().trim();
        String cargo = autoCompleteCargo.getText().toString().trim();

        // 2. Validar os dados
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || cargo.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            return;
        }


        UsuarioCadastroRequest request = new UsuarioCadastroRequest(nome, email, senha, cpf, cargo);


        ApiService apiService = RetrofitClient.getInstance();


        Call<UsuarioResponse> call = apiService.cadastrarUsuario(request);
        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                // A API respondeu (com sucesso ou erro)
                if (response.isSuccessful() && response.body() != null) {
                    // HTTP 200-299: Sucesso
                    Toast.makeText(FormCadastro.this, "Usuário cadastrado com sucesso! ID: " + response.body().getId(), Toast.LENGTH_LONG).show();
                    // Opcional: navegar para outra tela ou limpar os campos
                    // finish();
                } else {
                    // A API retornou um código de erro (4xx, 5xx)
                    String erroMsg = "Erro no cadastro. Código: " + response.code();
                    // Tenta ler a mensagem de erro do corpo da resposta, se houver
                    try {
                        if (response.errorBody() != null) {
                            erroMsg += " - " + response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(FormCadastro.this, erroMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                // Falha de rede (sem internet, servidor offline, URL errada)
                Toast.makeText(FormCadastro.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}