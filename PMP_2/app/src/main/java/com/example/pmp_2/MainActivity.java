package com.example.pmp_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView number;
    Button numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectBetter() {
        this.setCLean();
        EditText quantityInput1 = (EditText) findViewById(R.id.editText);
        EditText quantityInput2 = (EditText) findViewById(R.id.editText2);

        EditText priceInput1 = (EditText) findViewById(R.id.textViewPrice1);
        EditText priceInput2 = (EditText) findViewById(R.id.textViewPrice2);

        LinearLayout lin_layout1 = (LinearLayout) findViewById(R.id.lin_layout1);
        LinearLayout lin_layout2 = (LinearLayout) findViewById(R.id.lin_layout2);


        if (quantityInput1.getText().toString().matches("") ||
                quantityInput2.getText().toString().matches("") ||
                priceInput1.getText().toString().matches("") ||
                priceInput2.getText().toString().matches("")
        ) {
            Toast.makeText(MainActivity.this, "You must fill all inputs", Toast.LENGTH_LONG).show();
        } else {
            float count1 = Float.parseFloat(quantityInput1.getText().toString());
            float count2 = Float.parseFloat(quantityInput2.getText().toString());
            float price1 = Float.parseFloat(priceInput1.getText().toString());
            float price2 = Float.parseFloat(priceInput2.getText().toString());

            GradientDrawable border = new GradientDrawable();
            border.setColor(getColor(R.color.successBackground)); //white background
            border.setStroke(2, getColor(R.color.successBorder)); //black border with full opacity

            if (price1 / count1 < price2 / count2) {
                lin_layout1.setBackground(border);
                Toast.makeText(MainActivity.this, "Pí produkt je výhodnější.", Toast.LENGTH_LONG).show();
            } else if (price1 / count1 == price2 / count2) {
                Toast.makeText(MainActivity.this, "Oba produkty mají stejnou cenu.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Minecraft produkt je výhodnější.", Toast.LENGTH_LONG).show();
                lin_layout2.setBackground(border);
            }
        }
    }

    public void setCLean() {
        GradientDrawable border = new GradientDrawable();
        border.setColor(getColor(R.color.colorPrimaryDark)); //white background
        border.setStroke(2, getColor(R.color.colorPrimaryDark)); //black border with full opacity

        LinearLayout lin_layout1 = (LinearLayout) findViewById(R.id.lin_layout1);
        LinearLayout lin_layout2 = (LinearLayout) findViewById(R.id.lin_layout2);
        lin_layout1.setBackground(border);
        lin_layout2.setBackground(border);
    }

    @Override
    public void onClick(View v) {
        selectBetter();
    }

    private void numberPickerDialog() {
        NumberPicker mynumberPicker = new NumberPicker(this);
        mynumberPicker.setMinValue(1);
        mynumberPicker.setMaxValue(10);
        NumberPicker.OnValueChangeListener myValChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                number.setText(String.valueOf(newVal));
            }

        };
        mynumberPicker.setOnValueChangedListener(myValChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mynumberPicker);
        builder.setTitle("Select Value");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        TextView textType1 = (TextView) findViewById(R.id.textViewCount1);
        TextView textType2 = (TextView) findViewById(R.id.textViewCount2);
        switch (view.getId()) {
            case R.id.radio_g:
                if (checked)
                    textType1.setText(getString(R.string.g_text));
                textType2.setText(getString(R.string.g_text));
                break;
            case R.id.radio_ks:
                if (checked)
                    textType1.setText(getString(R.string.ks_text));
                textType2.setText(getString(R.string.ks_text));
                break;
            case R.id.radio_kg:
                if (checked)
                    textType1.setText(getString(R.string.kg_text));
                textType2.setText(getString(R.string.kg_text));
                break;
            case R.id.radio_ml:
                if (checked)
                    textType1.setText(getString(R.string.ml_text));
                textType2.setText(getString(R.string.ml_text));
                break;
            case R.id.radio_l:
                if (checked)
                    textType1.setText(getString(R.string.l_text));
                textType2.setText(getString(R.string.l_text));
                break;
        }
    }
}
