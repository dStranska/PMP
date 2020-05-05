package com.example.pmp_2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView number;
    Button numberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        number= (TextView) findViewById(R.id.textView_numberPicker);
//        numberPicker= (Button) findViewById(R.id.numberPicker);
//        numberPicker.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        numberPickerDialog();
    }
    private  void numberPickerDialog(){
        NumberPicker mynumberPicker = new NumberPicker(this);
        mynumberPicker.setMinValue(1);
        mynumberPicker.setMaxValue(10);
        NumberPicker.OnValueChangeListener myValChangeListener = new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
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

        TextView textType1 =(TextView) findViewById(R.id.textViewCount1);
        TextView textType2 =(TextView) findViewById(R.id.textViewCount2);
        switch(view.getId()) {
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
