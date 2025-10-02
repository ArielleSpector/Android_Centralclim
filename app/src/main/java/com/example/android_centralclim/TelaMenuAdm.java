package com.example.android_centralclim;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_centralclim.model.Cliente;
import com.example.android_centralclim.model.Tecnico;
import com.example.android_centralclim.network.ApiService;
import com.example.android_centralclim.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaMenuAdm extends AppCompatActivity {

    private Button btnCriarCliente, btnCriarServico, btnCriarFuncionario, btnRelatorios;
    private SearchView searchViewClientes, searchViewTecnicos;
    private ApiService apiService;

    private RecyclerView recyclerViewClientes, recyclerViewTecnicos;
    private GenericListAdapter clienteAdapter, tecnicoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_menu_adm);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiService = RetrofitClient.getInstance();

        inicializarComponentes();
        configurarRecyclerViews();
        configurarListeners();
    }

    private void inicializarComponentes() {
        btnCriarCliente = findViewById(R.id.btn_criar_cliente);
        btnCriarServico = findViewById(R.id.btn_criar_servico);
        btnCriarFuncionario = findViewById(R.id.btn_criar_funcionario);
        btnRelatorios = findViewById(R.id.btn_relatorios);

        searchViewClientes = findViewById(R.id.search_view_clientes);
        searchViewTecnicos = findViewById(R.id.search_view_tecnicos);

        recyclerViewClientes = findViewById(R.id.recycler_view_clientes);
        recyclerViewTecnicos = findViewById(R.id.recycler_view_tecnicos);
    }

    private void configurarRecyclerViews() {
        // Configuração para a lista de clientes
        clienteAdapter = new GenericListAdapter();
        recyclerViewClientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewClientes.setAdapter(clienteAdapter);
        // Registrar a Activity como ouvinte dos eventos de clique do adapter
        clienteAdapter.setOnItemClickListener(new GenericListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Object item) {
                // TODO: Implementar lógica para abrir a tela de edição de cliente
                Cliente cliente = (Cliente) item;
                Toast.makeText(TelaMenuAdm.this, "Editar cliente: " + cliente.getNome(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(Object item) {
                Cliente cliente = (Cliente) item;
                mostrarDialogoDelecao(cliente);
            }
        });

        // Configuração para a lista de técnicos
        tecnicoAdapter = new GenericListAdapter();
        recyclerViewTecnicos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTecnicos.setAdapter(tecnicoAdapter);
        // Registrar a Activity como ouvinte dos eventos de clique do adapter
        tecnicoAdapter.setOnItemClickListener(new GenericListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Object item) {
                // TODO: Implementar lógica para abrir a tela de edição de técnico
                Tecnico tecnico = (Tecnico) item;
                Toast.makeText(TelaMenuAdm.this, "Editar técnico: " + tecnico.getNome(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(Object item) {
                Tecnico tecnico = (Tecnico) item;
                mostrarDialogoDelecao(tecnico);
            }
        });
    }

    private void configurarListeners() {
        // --- Listeners dos Botões ---
        btnCriarCliente.setOnClickListener(v -> {
            // Lógica para criar cliente
            Toast.makeText(this, "Abrir tela de criar cliente...", Toast.LENGTH_SHORT).show();
        });

        // ... (outros listeners de botão)

        // --- Listeners das Barras de Pesquisa ---
        searchViewClientes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarClientes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarClientes(newText);
                return true;
            }
        });

        searchViewTecnicos.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarTecnicos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarTecnicos(newText);
                return true;
            }
        });
    }

    // --- MÉTODOS PARA CHAMAR A API ---

    private void buscarClientes(String nomeQuery) {
        if (nomeQuery == null || nomeQuery.trim().isEmpty()) {
            clienteAdapter.updateData(new ArrayList<>());
            return;
        }
        apiService.getClientes(nomeQuery).enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clienteAdapter.updateData(response.body());
                    Log.d("API_CLIENTES", "Clientes encontrados: " + response.body().size());
                } else {
                    clienteAdapter.updateData(new ArrayList<>());
                    Log.e("API_CLIENTES", "Erro na resposta do servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                clienteAdapter.updateData(new ArrayList<>());
                Log.e("API_CLIENTES", "Falha na conexão: " + t.getMessage());
                Toast.makeText(TelaMenuAdm.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarTecnicos(String nomeQuery) {
        if (nomeQuery == null || nomeQuery.trim().isEmpty()) {
            tecnicoAdapter.updateData(new ArrayList<>());
            return;
        }
        apiService.getTecnicos(nomeQuery).enqueue(new Callback<List<Tecnico>>() {
            @Override
            public void onResponse(Call<List<Tecnico>> call, Response<List<Tecnico>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tecnicoAdapter.updateData(response.body());
                    Log.d("API_TECNICOS", "Técnicos encontrados: " + response.body().size());
                } else {
                    tecnicoAdapter.updateData(new ArrayList<>());
                    Log.e("API_TECNICOS", "Erro na resposta do servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Tecnico>> call, Throwable t) {
                tecnicoAdapter.updateData(new ArrayList<>());
                Log.e("API_TECNICOS", "Falha na conexão: " + t.getMessage());
            }
        });
    }

    // --- MÉTODOS DE AÇÃO (EDITAR/DELETAR) ---

    private void mostrarDialogoDelecao(Object item) {
        String nome;
        if (item instanceof Cliente) {
            nome = ((Cliente) item).getNome();
        } else if (item instanceof Tecnico) {
            nome = ((Tecnico) item).getNome();
        } else {
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Tem certeza que deseja deletar " + nome + "?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    if (item instanceof Cliente) {
                        deletarClienteApi(((Cliente) item).getId());
                    } else if (item instanceof Tecnico) {
                        deletarTecnicoApi(((Tecnico) item).getId());
                    }
                })
                .setNegativeButton("Não", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deletarClienteApi(Long id) {
        apiService.deleteCliente(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TelaMenuAdm.this, "Cliente deletado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Re-executa a busca para atualizar a lista na tela
                    buscarClientes(searchViewClientes.getQuery().toString());
                } else {
                    Toast.makeText(TelaMenuAdm.this, "Erro ao deletar cliente.", Toast.LENGTH_SHORT).show();
                    Log.e("API_DELETE", "Erro ao deletar cliente: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TelaMenuAdm.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_DELETE", "Falha na conexão ao deletar cliente: " + t.getMessage());
            }
        });
    }

    private void deletarTecnicoApi(Long id) {
        apiService.deleteTecnico(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TelaMenuAdm.this, "Técnico deletado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Re-executa a busca para atualizar a lista na tela
                    buscarTecnicos(searchViewTecnicos.getQuery().toString());
                } else {
                    Toast.makeText(TelaMenuAdm.this, "Erro ao deletar técnico.", Toast.LENGTH_SHORT).show();
                    Log.e("API_DELETE", "Erro ao deletar técnico: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TelaMenuAdm.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_DELETE", "Falha na conexão ao deletar técnico: " + t.getMessage());
            }
        });
    }
}
