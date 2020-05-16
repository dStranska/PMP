package com.example.pmp_3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.PrecomputedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> shoppingList = null;
    ArrayAdapter<String> adapter = null;
    ListView lv = null;
    public int listPosition;
    static boolean[] tickMarkVisibileListPosition=new boolean[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = getArrayVal(getApplicationContext());
        Collections.sort(shoppingList);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                if (position %2 == 1){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                }
                else{
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
                return view;
            }
        };
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                listPosition = position - lv.getFirstVisiblePosition();
                if (lv.getChildAt(listPosition).findViewById(R.id.tick_mark).getVisibility() == View.INVISIBLE)
                {
                    lv.getChildAt(listPosition).findViewById(R.id.tick_mark).setVisibility(View.VISIBLE);
                    tickMarkVisibileListPosition[position]=Boolean.TRUE;
                }
                else
                {
                    lv.getChildAt(listPosition).findViewById(R.id.tick_mark).setVisibility(View.INVISIBLE);
                    tickMarkVisibileListPosition[position]=Boolean.FALSE;
                }
                lv.getChildAt(listPosition).setSelected(true);
            }
        });
        //CustomAdapterListview adapter=new CustomAdapterListview(MainActivity.this,lv);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItem.trim().equals(shoppingList.get(position).trim())) {
                    removedElement(selectedItem, position);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Chyba při odstranění", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }

    public class CustomAdapterListview extends BaseAdapter {
        private String[] itemValues;
        private LayoutInflater inflater;
        public ViewHolder holder=null;
        CustomAdapterListview(Context context,String[] values)
        {
            // this.tcontext=context;  
            this.itemValues=values;
            this.inflater=LayoutInflater.from(context);
        }
        public int getCount() {
            return itemValues.length;
        }
        @Override
        public Object getItem(int position)
        {
            return position;
        }
        @Override
        public long getItemId(int position)
        {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.layout_for_listview,null);
                holder=new ViewHolder();
                holder.tickMark=(ImageView) convertView.findViewById(R.id.tick_mark);
                holder.itemListView= (ListView) convertView.findViewById(R.id.listView);
                convertView.setTag(holder);
            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }
            holder.itemDataView.setText(itemValues[position]);
            if(MainActivity.tickMarkVisibileListPosition[position]==Boolean.TRUE)
                /*Everytime adapter check whether the imageview at particular position have to be made visible or not.
                 */
            {
                holder.tickMark.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.tickMark.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
        class ViewHolder
        {
            public ListView itemListView;
            TextView itemDataView;
            ImageView tickMark;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_sort) {
            Collections.sort(shoppingList);
            lv.setAdapter(adapter);
            return true;
        }

        if (id == R.id.action_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Item");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shoppingList.add(preferredCase(input.getText().toString()));
                    Collections.sort(shoppingList);
                    storeArrayVal(shoppingList, getApplicationContext());
                    lv.setAdapter(adapter);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }

        if (id == R.id.action_clear) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Opravdu chcete smazat celý seznam");
            builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shoppingList.clear();
                    lv.setAdapter(adapter);
                }
            });
            builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String preferredCase(String original){
        if (original.isEmpty())
            return original;

        return original.substring(0,1).toUpperCase() + original.substring(1).toLowerCase();
    }

    public static void storeArrayVal (ArrayList inArrayList, Context context) {
        Set WhatToWrite = new HashSet(inArrayList);
        SharedPreferences WordSearchPutPrefs = context.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = WordSearchPutPrefs.edit();
        prefEditor.putStringSet("myArray", WhatToWrite);
        prefEditor.commit();
    }

    public static ArrayList getArrayVal(Context dan){
        SharedPreferences WordSearchGetPrefs = dan.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        Set tempSet = new HashSet();
        tempSet = WordSearchGetPrefs.getStringSet("myArray", tempSet);
        return new ArrayList<>(tempSet);
    }

    public void removedElement(String selectedItem, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Odstanit " + selectedItem + "?");
        builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shoppingList.remove(position);
                Collections.sort(shoppingList);
                storeArrayVal(shoppingList, getApplicationContext());
                lv.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
