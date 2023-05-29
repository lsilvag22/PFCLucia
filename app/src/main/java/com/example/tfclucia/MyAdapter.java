package com.example.tfclucia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Film> filmArrayList;
    private RecyclerViewClickListener listener;

    public MyAdapter(Context context, ArrayList<Film> filmArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.filmArrayList = filmArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder,int position) {

        Film film = filmArrayList.get(position);
        holder.titulo.setText(film.titulo);
        holder.sinopsis.setText(film.sinopsis);
        Glide.with(context).load(film.getFoto())
                .into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return filmArrayList.size();
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
   public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titulo,sinopsis;
        ImageView foto;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           titulo = itemView.findViewById(R.id.filmTitulo);
           sinopsis = itemView.findViewById(R.id.filmSinopsis);
           foto = itemView.findViewById(R.id.filmFoto);
           itemView.setOnClickListener(this);
       }


       @Override
       public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
       }
   }
}
