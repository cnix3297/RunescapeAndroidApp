package com.example.charles.runescapeappp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buyOnClick(View v) {
        EditText newItems = (EditText) findViewById(R.id.newItem);
        String newItem = newItems.getText().toString();
        EditText buyPrices = (EditText) findViewById(R.id.buyPrice);
        String buyPrice = buyPrices.getText().toString();
        EditText buyAmounts = (EditText) findViewById(R.id.buyAmount);
        String buyAmount = buyAmounts.getText().toString();
        //Spinner spinner = (Spinner)findViewById(R.id.buyItemOldText);
        // String oldText = spinner.getSelectedItem().toString();
        DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
        myDbHelper.openDataBase();



        if(newItem == ""){


        }else{

            Log.d("database entry", "database insert");

            // myDbHelper.insertQueryItem(newItem);
            myDbHelper.insertQueryBuy(newItem, Integer.parseInt(buyAmount), Integer.parseInt(buyPrice));

        }

    }
}
