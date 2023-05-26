package com.example.tfclucia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Film> filmArrayList;

    public MyAdapter(Context context, ArrayList<Film> filmArrayList) {
        this.context = context;
        this.filmArrayList = filmArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Film film = filmArrayList.get(position);

        holder.titulo.setText(film.titulo);
        holder.sinopsis.setText(film.sinopsis);


    }

    @Override
    public int getItemCount() {
        return filmArrayList.size();
    }

   public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo,sinopsis;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           titulo = itemView.findViewById(R.id.filmTitulo);
           sinopsis = itemView.findViewById(R.id.filmSinopsis);
       }
   }
}
