package com.example.von.prova2;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.von.prova2.modelo.Food;

import java.io.File;
import java.util.ArrayList;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    private ArrayList<Food> food;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView desc;
        public ImageView imagem;
        public TextView rating;
        public TextView tipo;

        public ViewHolder(View v) {
            super(v);
            desc = (TextView) v.findViewById(R.id.txtDesc);
            imagem = (ImageView) v.findViewById(R.id.imageView2);
            rating = (TextView) v.findViewById(R.id.txtRating);
            tipo = (TextView) v.findViewById(R.id.txtTipo);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterFood(ArrayList<Food> food) {
        this.food = food;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterFood.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foods, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String desc = food.get(position).getDesc();
        String imagem = food.get(position).getImagem();
        Integer rate = food.get(position).getRate();
        String tipo = food.get(position).getTipo();

        Uri uriFotografia = Uri.fromFile(new File(imagem));

        holder.desc.setText(desc);
        holder.tipo.setText(tipo);
        holder.imagem.setImageURI(uriFotografia);
        holder.rating.setText("Avaliado com " + rate + " estrelas");
    }

    @Override
    public int getItemCount() {
        return food.size();
    }
}