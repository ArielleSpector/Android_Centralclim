package com.example.android_centralclim;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Imports para a comunicação com a API
import com.example.android_centralclim.model.UsuarioCadastroRequest;
import com.example.android_centralclim.model.UsuarioResponse;
import com.example.android_centralclim.network.ApiService;
import com.example.android_centralclim.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormClaCli extends AppCompatActivity {


    private EditText editNome, editNumero, editCEP, editLogradouro, editCpf, numeroCasa, referencia;
    private Button buttonCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cla_cli);


        editNome = findViewById(R.id.nomeEditText);
        editNumero = findViewById(R.id.celularEditText);
        editCEP = findViewById(R.id.cepEditText);
        editLogradouro = findViewById(R.id.logradouroEditText);
        editCpf = findViewById(R.id.cpfEditText);
        numeroCasa = findViewById(R.id.numeroEditText);
        referencia = findViewById(R.id.referenciaEditText);
        buttonCadastro = findViewById(R.id.registrarButton);


        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realizarCadastro();
            }
        });
    }

    private void realizarCadastro() {

        String nome = editNome.getText().toString().trim();
        String telefone = editNumero.getText().toString().trim();
        String cep = editCEP.getText().toString().trim();
        String logradouro = editLogradouro.getText().toString().trim();
        String cpf = editCpf.getText().toString().trim();

        String numeroCasaStr = numeroCasa.getText().toString().trim();
        String referenciaStr = referencia.getText().toString().trim();


        if (nome.isEmpty() || telefone.isEmpty() || cep.isEmpty() || cpf.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }


        buttonCadastro.setEnabled(false);

        UsuarioCadastroRequest request = new UsuarioCadastroRequest(nome, telefone, cep, cpf, numeroCasaStr, referenciaStr, logradouro);


        ApiService apiService = RetrofitClient.getInstance();
        Call<UsuarioResponse> call = apiService.cadastrarUsuario(request);


        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {

                buttonCadastro.setEnabled(true);

                if (response.isSuccessful()) {

                    Toast.makeText(FormClaCli.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    Log.d("API_SUCCESS", "Cliente cadastrado, ID: " + response.body().getId());
                    finish();
                } else {

                    String erroMsg = "Erro no cadastro. Código: " + response.code();
                    Log.e("API_ERROR", erroMsg);
                    Toast.makeText(FormClaCli.this, erroMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                buttonCadastro.setEnabled(true);

                String failureMsg = "Falha na comunicação: " + t.getMessage();
                Log.e("API_FAILURE", failureMsg, t);
                Toast.makeText(FormClaCli.this, failureMsg, Toast.LENGTH_LONG).show();
            }
        });
    }
}