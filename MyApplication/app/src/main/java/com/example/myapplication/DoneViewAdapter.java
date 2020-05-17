package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DoneViewAdapter extends ArrayAdapter<ShopItem>
{
    private ArrayList<ShopItem> Fruits = new ArrayList<>();
    private Context context;

    public DoneViewAdapter(ArrayList<ShopItem> Fruits, Context context){
        super(context,R.layout.item_layout,Fruits);
        this.context=context;
        this.Fruits=Fruits;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(R.layout.item_layout,parent,false);
        final TextView FruitName = row.findViewById(R.id.fruit_name);
        FruitName.setText(Fruits.get(position).getName());

        return row;

    }
}
