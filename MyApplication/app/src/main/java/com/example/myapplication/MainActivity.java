package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;


    private ListView listView;
    private ListViewAdapter adapter;
    private List<ShopItem> items = new ArrayList<>();
    public static List<ShopItem> UserSelection = new ArrayList<>();

    public static boolean isActionMode = false;
    public static ActionMode actionMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabaseHelper = new DatabaseHelper(this);
        // mDatabaseHelper.initTable();

        listView = findViewById(R.id.mListView);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
        getData();
        adapter = new ListViewAdapter(items, this);
        listView.setAdapter(adapter);

        Button buttonDone = findViewById(R.id.button_done);


        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DoneActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData() {
        items.clear();
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()) {
            ShopItem item = new ShopItem(data.getInt(0), data.getString(1), data.getDouble(3), (data.getInt(2) == 1 ? true : false));
            items.add(item);
        }
    }

    public void addItem(String text, Double price) {
        boolean insertData = mDatabaseHelper.addData(text, price);
        if (insertData) {
            toastMessage("Object was successfully added");
        } else {
            toastMessage("Something went wrong");
        }
        getData();
        listView.setAdapter(adapter);
    }

    public void seletectDone(ShopItem item) {
        boolean done = mDatabaseHelper.done(item.getId());
    }

    public void toastMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
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

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText input = new EditText(this);
            input.setHint("Name");
            final EditText input_price = new EditText(this);
            input_price.setHint("Price");
            input_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //for decimal numbers

            layout.addView(input);
            layout.addView(input_price);

            builder.setView(layout);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addItem(input.getText().toString(), Double.valueOf(input_price.getText().toString()));

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

                    mDatabaseHelper.deleteAll();
                    getData();
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
                        for (ShopItem shopItem : UserSelection) {
                            mDatabaseHelper.deleteById(shopItem.getId());
                        }
                        getData();
                        listView.setAdapter(adapter);
                        toastMessage("Deleted: " + MainActivity.UserSelection.size() + " items");
                        mode.finish();
                        return true;
                    case (R.id.action_done):

                        for (ShopItem shopItem : UserSelection) {
                            seletectDone(shopItem);
                        }
                        getData();
                        listView.setAdapter(adapter);
                        toastMessage("Checked: " + MainActivity.UserSelection.size() + " items");

                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            } else {
                toastMessage("You must select min 1 item");

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

    public void openEdit(){
        toastMessage("edit");
    }


}
