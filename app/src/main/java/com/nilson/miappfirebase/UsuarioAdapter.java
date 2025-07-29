package com.nilson.miappfirebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private ArrayList<Usuario> listaUsuarios;
    private Context context;

    public UsuarioAdapter(Context context, ArrayList<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);

        // ✅ Mostrar nombre y nombre de profesión
        holder.tvNombre.setText(usuario.getNombre());  // nombre del usuario
        holder.tvProfesion.setText("Profesión: " + usuario.getProfesion());  // nombre de la profesión ya resuelto

        // Menú contextual (editar o eliminar)
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu menu = new PopupMenu(context, holder.itemView);
            MenuInflater inflater = menu.getMenuInflater();
            inflater.inflate(R.menu.menu_usuario, menu.getMenu());

            menu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menuEditar) {
                    Intent intent = new Intent(context, FormularioUsuarioActivity.class);
                    intent.putExtra("usuario_id", usuario.getId());
                    intent.putExtra("nombre", usuario.getNombre());
                    intent.putExtra("profesion_id", usuario.getProfesion_id()); // todavía se necesita el ID para editar
                    context.startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.menuEliminar) {
                    if (context instanceof CrudUsuariosActivity) {
                        ((CrudUsuariosActivity) context).eliminarUsuario(usuario.getId());
                    }
                    return true;
                }
                return false;
            });

            menu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvProfesion;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvProfesion = itemView.findViewById(R.id.tvProfesion);
        }
    }
}
