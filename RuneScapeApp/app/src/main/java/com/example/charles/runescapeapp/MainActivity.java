package com.example.charles.runescapeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Cursor dbCursor = null;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelp = new DatabaseHelper(MainActivity.this);
         spinner = (Spinner) findViewById(R.id.ItemSpinner);
         spinner = dbHelp.updateScrollPane(spinner,this);

       //Query portion easily changed for debugging purposes
        /*SQLiteDatabase db = dbHelp.getReadableDatabase();
        String query = "select _id, item_name, item_max from ITEM where item_name = 'chucky';";
        Cursor temp = db.rawQuery(query,null);
        temp.moveToFirst();
        String[] print= {temp.getString(temp.getColumnIndex("item_name")), ( temp.getString(temp.getColumnIndex("item_max")))};
        Log.d("test", print[1] + " " + print[0]);*/

    }



    public void buyOnClick(View v) {
        Item buyItem = new Item();
        EditText newItems = (EditText) findViewById(R.id.newItem);
        buyItem.setItemName(newItems.getText().toString());
        EditText buyPrices = (EditText) findViewById(R.id.buyPrice);
        buyItem.setBuyPrice(Integer.parseInt(buyPrices.getText().toString()));
        EditText buyAmounts = (EditText) findViewById(R.id.buyAmount);
        buyItem.setBuyAmount(Integer.parseInt(buyAmounts.getText().toString()));






        if(buyItem.getItemName().equals("")|| buyItem.getItemName().equals(null)){
            Cursor c = (Cursor) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
            buyItem.setItemName(c.getString(1));
            DatabaseHelper myDbhelper = new DatabaseHelper(this);
            myDbhelper.insertQueryBuy(buyItem.getItemName(),buyItem.getBuyAmount(),buyItem.getBuyPrice());
            buyItem= myDbhelper.checkMinAndMax(buyItem);


        }else{

            Log.d("database entry", "database insert");
            DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
             myDbHelper.insertQueryItem(buyItem);
            myDbHelper.insertQueryBuy(buyItem.getItemName(),buyItem.getBuyAmount(),buyItem.getBuyPrice());
            buyItem= myDbHelper.checkMinAndMax(buyItem);
            spinner = myDbHelper.updateScrollPane(spinner,this);
        }

    }




}