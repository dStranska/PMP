package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private List<ShopItem> shopItemsActive = new ArrayList<>();
    private ArrayList<ShopItem> shopItemsDone = new ArrayList<>();
    public static List<ShopItem> UserSelection = new ArrayList<>();

    public static boolean isActionMode = false;
    public static ActionMode actionMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getItems();
        listView = findViewById(R.id.mListView);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

        adapter = new ListViewAdapter(shopItemsActive, shopItemsDone, this);
        listView.setAdapter(adapter);

        Button buttonDone = findViewById(R.id.button_done);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(shopItemsDone.size()!=0) {
                    Intent intent = new Intent(MainActivity.this, DoneActivity.class);
                    intent.putExtra("list", shopItemsDone);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "There is no checked item", Toast.LENGTH_LONG).show();

                }



            }
        });
    }

    ///Data z xml
    private void getItems() {
        String[] items = getResources().getStringArray(R.array.fruits);

        for (String item : items) {
            ShopItem shopItem = new ShopItem(item, 23.1);
            shopItemsActive.add(shopItem);
        }
    }
    //Menu add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add new item");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ShopItem shopItem= new ShopItem(input.getText().toString(),55.5);
                    shopItemsActive.add(shopItem);
                    listView.setAdapter(adapter);
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
                    adapter.removeItems(shopItemsActive);
                    listView.setAdapter(adapter);
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



    ///Menu up
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.context_menu, menu);

            isActionMode = true;
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (UserSelection.size() > 0) {
                switch (item.getItemId()) {
                    case (R.id.action_delete):
                        adapter.removeItems(UserSelection);
                        Toast.makeText(getApplicationContext(), "Deleted: " + MainActivity.UserSelection.size() + " items", Toast.LENGTH_LONG).show();
                        mode.finish();
                        return true;
                    case (R.id.action_done):
                        adapter.doneItems(UserSelection);
                        for(ShopItem shopItem : UserSelection){
                            shopItemsDone.add(shopItem);
                        }
                        adapter.removeItems(UserSelection);

                        Toast.makeText(getApplicationContext(), "Checked " + MainActivity.UserSelection.size() + " new items", Toast.LENGTH_LONG).show();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "You must select min 1 item", Toast.LENGTH_LONG).show();

            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            UserSelection.clear();
            isActionMode = false;
            actionMode = null;
        }
    };
}
