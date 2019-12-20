package com.witim;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter {

    private ArrayList<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(Context context) {
        this.context = context;
    }
    public void setListModels(ArrayList<Model> models) {
        this.models = models;
    }

    @Override
    public int getCount(){
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.portfolio_item, container, false);

        ImageView imageView;
        imageView = view.findViewById(R.id.portfolio_image);
        imageView.setImageResource(models.get(position).getImage());

        container.addView(view, 0);

        return view;
    }

    
    public void DestroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView txt_nama;
        TextView txt_role;
        TextView txt_category;
        TextView txt_city;
        TextView txt_country;

        CardViewViewHolder(View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_name);
            txt_role= itemView.findViewById(R.id.txt_role);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_city = itemView.findViewById(R.id.txt_city);
            txt_country = itemView.findViewById(R.id.txt_country);

        }
    }

}
