package com.example.android_centralclim;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_centralclim.model.CepResponse;
import com.example.android_centralclim.model.UsuarioCadastroRequest;
import com.example.android_centralclim.model.UsuarioResponse;
import com.example.android_centralclim.network.ApiService;
import com.example.android_centralclim.network.RetrofitClient;
import com.example.android_centralclim.network.ViaCepService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormClaCli extends AppCompatActivity {

    // Componentes da interface
    private EditText editNome, editNumero, editCEP, editLogradouro, editBairro, editCpf, numeroCasa, referencia;
    private Button buttonCadastro;

    // Cliente Retrofit específico para a API ViaCEP
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cla_cli);

        // 1. INICIALIZAÇÃO DOS COMPONENTES
        inicializarComponentes();

        // 2. INICIALIZAÇÃO DO SERVIÇO DE CEP
        inicializarViaCepService();

        // 3. CONFIGURAÇÃO DOS LISTENERS
        configurarListeners();
    }

    private void inicializarComponentes() {
        editNome = findViewById(R.id.nomeEditText);
        editNumero = findViewById(R.id.celularEditText);
        editCEP = findViewById(R.id.cepEditText);
        editLogradouro = findViewById(R.id.logradouroEditText);
        editBairro = findViewById(R.id.bairroEditText); // Novo campo
        editCpf = findViewById(R.id.cpfEditText);
        numeroCasa = findViewById(R.id.numeroEditText);
        referencia = findViewById(R.id.referenciaEditText);
        buttonCadastro = findViewById(R.id.registrarButton);
    }

    private void inicializarViaCepService() {
        Retrofit retrofitViaCep = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        viaCepService = retrofitViaCep.create(ViaCepService.class);
    }

    private void configurarListeners() {
        // Listener para o botão de cadastro
        buttonCadastro.setOnClickListener(v -> realizarCadastro());

        // Listener para o campo de CEP para buscar o endereço automaticamente
        editCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Quando o CEP tiver 8 dígitos, busca o endereço
                if (s.length() == 8) {
                    buscarEnderecoPorCep(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void buscarEnderecoPorCep(String cep) {
        viaCepService.getAddressByCep(cep).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse cepResponse = response.body();
                    // Preenche os campos com a resposta da API
                    editLogradouro.setText(cepResponse.getLogradouro());
                    editBairro.setText(cepResponse.getBairro());
                    // Move o foco para o campo de número, para o usuário continuar
                    numeroCasa.requestFocus();
                } else {
                    Toast.makeText(FormClaCli.this, "CEP não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                Toast.makeText(FormClaCli.this, "Falha ao buscar CEP. Verifique a internet.", Toast.LENGTH_SHORT).show();
                Log.e("VIACEP_FAILURE", "Erro ao buscar CEP", t);
            }
        });
    }

    private void realizarCadastro() {
        // Coleta os dados dos campos
        String nome = editNome.getText().toString().trim();
        String telefone = editNumero.getText().toString().trim();
        String cep = editCEP.getText().toString().trim();
        String logradouro = editLogradouro.getText().toString().trim();
        String cpf = editCpf.getText().toString().trim();
        String numeroCasaStr = numeroCasa.getText().toString().trim();
        String referenciaStr = referencia.getText().toString().trim();
        // Incluir o bairro no cadastro (opcional, dependendo da sua API)
        // String bairro = editBairro.getText().toString().trim();

        if (nome.isEmpty() || telefone.isEmpty() || cep.isEmpty() || cpf.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonCadastro.setEnabled(false);

        // Crie o request para sua API
        UsuarioCadastroRequest request = new UsuarioCadastroRequest(nome, telefone, cep, cpf, numeroCasaStr, referenciaStr, logradouro);

        // Chama sua API para cadastrar o cliente
        ApiService apiService = RetrofitClient.getInstance();
        Call<UsuarioResponse> call = apiService.cadastrarUsuario(request);
        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                buttonCadastro.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(FormClaCli.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
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