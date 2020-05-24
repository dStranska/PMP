package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DoneActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    DatabaseHelper mDatabaseHelper;
    Double totalPrice;


    private ArrayList<ShopItem> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        mDatabaseHelper = new DatabaseHelper(this);

        getData();
        listView=findViewById(R.id.doneListView);
        adapter = new ListViewAdapter(items,this);
        listView.setAdapter(adapter);

        Button buttonDone = findViewById(R.id.button_back);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public void getData(){
        totalPrice=0.0;
       items.clear();
        Cursor data = mDatabaseHelper.getDone();
        while (data.moveToNext()) {
            ShopItem item = new ShopItem(data.getInt(0),data.getString(1),data.getDouble(3),(data.getInt(2)==1?true:false));
            items.add(item);
            totalPrice+=data.getDouble(3);
        }
        TextView price = (TextView) findViewById(R.id.textView_price);
        price.setText(String.format("%.2f",totalPrice)+" Kč");
    }
}
