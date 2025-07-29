package com.nilson.miappfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfesionAdapter extends RecyclerView.Adapter<ProfesionAdapter.ProfesionViewHolder> {

    private ArrayList<Profesion> listaProfesiones;

    public ProfesionAdapter(ArrayList<Profesion> listaProfesiones) {
        this.listaProfesiones = listaProfesiones;
    }

    @NonNull
    @Override
    public ProfesionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profesion, parent, false);
        return new ProfesionViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesionViewHolder holder, int position) {
        Profesion profesion = listaProfesiones.get(position);
        holder.tvNombre.setText(profesion.getNombre());
    }

    @Override
    public int getItemCount() {
        return listaProfesiones.size();
    }

    public static class ProfesionViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;

        public ProfesionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProfesion);
        }
    }
}
