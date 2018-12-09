package com.example.charles.runescapeappp2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
            try {
                myDbHelper.createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myDbHelper.openDataBase();


            if(newItem == ""){


                }else{
                    Log.d("database entry", "database insert");
                myDbHelper.insertQueryItem(newItem);
               // myDbHelper.insertQueryBuy(newItem,Integer.parseInt(buyAmount),Integer.parseInt(buyPrice));
                myDbHelper.close();
            }

            }




    }


