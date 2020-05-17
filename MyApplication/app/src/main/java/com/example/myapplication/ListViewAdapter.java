package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<ShopItem>
{
    private List<ShopItem> Fruits = new ArrayList<>();
    private List<ShopItem> Done = new ArrayList<>();
    private Context context;

    public ListViewAdapter(List<ShopItem> Fruits,ArrayList<ShopItem> Done,Context context){
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

        CheckBox checkBox = row.findViewById(R.id.checkBox);
        checkBox.setTag(position);

        if(MainActivity.isActionMode){
            checkBox.setVisibility(View.VISIBLE);
        }else{
            checkBox.setVisibility(View.GONE);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int)buttonView.getTag();
                if(MainActivity.UserSelection.contains(Fruits.get(position))){
                    MainActivity.UserSelection.remove(Fruits.get(position));
                }else{
                    MainActivity.UserSelection.add(Fruits.get(position));
                }
                MainActivity.actionMode.setTitle(MainActivity.UserSelection.size() +" items selected");
            }
        });
        return row;

    }

    public void removeItems(List<ShopItem> items){
        for(ShopItem item : items){
            Fruits.remove(item);
        }
        notifyDataSetChanged();
    }
    public void doneItems(List<ShopItem> items){

        for(ShopItem item : items){
            Done.add(item);
            Fruits.remove(item);
        }
        notifyDataSetChanged();
    }
}
