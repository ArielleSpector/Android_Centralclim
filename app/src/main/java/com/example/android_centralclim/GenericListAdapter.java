package com.example.android_centralclim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // Adicionar importação
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_centralclim.model.Cliente;
import com.example.android_centralclim.model.Tecnico;
import java.util.ArrayList;
import java.util.List;

public class GenericListAdapter extends RecyclerView.Adapter<GenericListAdapter.ViewHolder> {

    private List<?> items = new ArrayList<>();
    private OnItemClickListener listener; // Nosso novo listener

    // 1. Interface para comunicação
    public interface OnItemClickListener {
        void onEditClick(Object item);
        void onDeleteClick(Object item);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 2. Adicionar os ImageButtons ao ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textMain;
        public final TextView textSecondary;
        public final ImageButton btnEdit;
        public final ImageButton btnDelete;

        public ViewHolder(View view) {
            super(view);
            textMain = view.findViewById(R.id.item_text_main);
            textSecondary = view.findViewById(R.id.item_text_secondary);
            btnEdit = view.findViewById(R.id.btn_edit);
            btnDelete = view.findViewById(R.id.btn_delete);
        }


        public void bind(final Object item, final OnItemClickListener listener) {
            btnEdit.setOnClickListener(v -> listener.onEditClick(item));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(item));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);

        if (item instanceof Cliente) {
            Cliente cliente = (Cliente) item;
            holder.textMain.setText(cliente.getNome());
            holder.textSecondary.setText(cliente.getTelefone());
        } else if (item instanceof Tecnico) {
            Tecnico tecnico = (Tecnico) item;
            holder.textMain.setText(tecnico.getNome());
            holder.textSecondary.setText(tecnico.getEspecialidade());
        }

        // 3. Vincular o listener de clique se ele existir
        if (listener != null) {
            holder.bind(item, listener);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<?> newItems) {
        this.items = (newItems != null) ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }
}