package com.witim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CardFind extends RecyclerView.Adapter<CardFind.CardViewViewHolder>{

    private Context context;
    private ArrayList<Model> listMahasiswa;

    private ArrayList<Model> getListMahasiswa() {
        return listMahasiswa;
    }
    public void setListMahasiswa(ArrayList<Model> listMahasiswa) {
        this.listMahasiswa = listMahasiswa;
    }
    public CardFind(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_find, parent, false);
        return new CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CardFind.CardViewViewHolder holder, int position) {
        Model m = getListMahasiswa().get(position);
        holder.txt_nama.setText(m.getNama());
        holder.txt_role.setText(m.getRole());
        holder.txt_category.setText(m.getCategory());
        holder.txt_city.setText(m.getCity());
        holder.txt_country.setText(m.getCountry());
    }

    @Override
    public int getItemCount() {
        return getListMahasiswa().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView txt_nama;
        TextView txt_category;
        TextView txt_role;
        TextView txt_city;
        TextView txt_country;

        CardViewViewHolder(View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_name);
            txt_role = itemView.findViewById(R.id.txt_role);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_city = itemView.findViewById(R.id.txt_city);
            txt_category = itemView.findViewById(R.id.txt_category);

        }
    }
}

