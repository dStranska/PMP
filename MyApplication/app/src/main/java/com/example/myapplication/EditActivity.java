package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;

    ShopItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        int id = getIntent().getIntExtra("id",0);
        if(id ==0){
            goBack();
        }


        getData(id);
        TextView input_name=findViewById(R.id.edit_name);
        TextView input_price=findViewById(R.id.edit_price);

        input_name.setText(item.getName());
        input_price.setText(item.getPrice().toString());

        Button backButton = (Button) findViewById(R.id.button_edit_back);
        backButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });



        Button saveButton = (Button) findViewById(R.id.button_edit_save);
        saveButton.setOnClickListener( new View.OnClickListener() {

            TextView input_name=findViewById(R.id.edit_name);
            TextView input_price=findViewById(R.id.edit_price);


            @Override
            public void onClick(View v) {
                boolean result = mDatabaseHelper.update(input_name.getText().toString(),Double.valueOf(input_price.getText().toString()),item.getId());
                if(result){
                    toastMessage("Item was successfully edited");
                }else{
                    toastMessage("Something went wrong");
                }
                goBack();

            }
        });



    }
    public void getData(int id){

        Cursor data = mDatabaseHelper.getById(id);
        while (data.moveToNext()) {
            item = new ShopItem(data.getInt(0), data.getString(1), data.getDouble(3), (data.getInt(2) == 1 ? true : false));
        }
    }

    public void toastMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public void goBack(){
        MainActivity.isActionMode=false;
        Intent i = new Intent(EditActivity.this,MainActivity.class);
        startActivity(i);
    }
}
