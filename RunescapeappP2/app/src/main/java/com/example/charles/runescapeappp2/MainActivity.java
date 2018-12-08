package com.example.charles.runescapeappp2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Cursor dbCursor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Button button = (Button) findViewById(R.id.BuyItemButton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText newItems = (EditText) findViewById(R.id.newItem);
                String newItem = newItems.getText().toString();
                EditText buyPrices = (EditText) findViewById(R.id.buyPrice);
                String buyPrice = buyPrices.getText().toString();
                EditText buyAmounts = (EditText) findViewById(R.id.buyAmount);
                String buyAmount = buyPrices.getText().toString();
                //Spinner spinner = (Spinner)findViewById(R.id.buyItemOldText);
               // String oldText = spinner.getSelectedItem().toString();



                if(newItem != ""){


                }else{
                myDbHelper.insertQueryItem(newItem);
                myDbHelper.insertQueryBuy(newItem,Integer.getInteger(buyAmount),Integer.getInteger(buyPrice));
                }

            }
        });
        mTextMessage = (TextView) findViewById(R.id.message);


    }

}
