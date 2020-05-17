package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DoneActivity extends AppCompatActivity {

    private ListView listView;
    private DoneViewAdapter adapter;


    private ArrayList<ShopItem> shopItemsDone = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        ArrayList<String> allDone = new ArrayList<String>();
        listView=findViewById(R.id.doneListView);


        ArrayList<ShopItem> shopItemsDone =  (ArrayList<ShopItem>)getIntent().getSerializableExtra("list");


        adapter = new DoneViewAdapter(shopItemsDone,this);
        listView.setAdapter(adapter);

        Button buttonDone = findViewById(R.id.button_back);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
