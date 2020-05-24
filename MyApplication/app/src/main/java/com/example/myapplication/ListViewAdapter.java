package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<ShopItem> {
    private List<ShopItem> Items = new ArrayList<>();
    private Context context;

    public ListViewAdapter(List<ShopItem> Items, Context context) {
        super(context, R.layout.item_layout, Items);
        this.context = context;
        this.Items = Items;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.item_layout, parent, false);
        final TextView name = row.findViewById(R.id.fruit_name);


        name.setText(Items.get(position).getName() + " ( " + Items.get(position).getPrice().toString() + " )");

        CheckBox checkBox = row.findViewById(R.id.checkBox);
        Button button = row.findViewById(R.id.button_edit);
        button.setTag(position);
        checkBox.setTag(position);


        if (MainActivity.isActionMode) {
            checkBox.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                if (MainActivity.UserSelection.contains(Items.get(position))) {
                    MainActivity.UserSelection.remove(Items.get(position));
                } else {
                    MainActivity.UserSelection.add(Items.get(position));
                }
                MainActivity.actionMode.setTitle(MainActivity.UserSelection.size() + " items selected");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                ShopItem item = (ShopItem) Items.get(position);

                Intent intent = new Intent(context, EditActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", item.getId());
                context.startActivity(intent);
            }
        });

        return row;

    }
}
