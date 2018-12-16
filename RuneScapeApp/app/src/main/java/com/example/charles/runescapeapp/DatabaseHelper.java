package com.example.charles.runescapeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "rsItemPrice";

    private static final int DB_VERSION = 10;

    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        copyDataBase();
        this.getReadableDatabase();
    }

    public void updateDataBase() {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();
            copyDataBase();
            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }


    public void insertQueryItem(Item item) {

        ContentValues test = new ContentValues();
        test.put("item_name", item.getItemName());
        test.put("item_min", item.getBuyAmount());
        test.put("item_max", item.getBuyAmount());
        SQLiteDatabase myDataBase = this.getWritableDatabase();
        Log.d("database entry", "" + myDataBase.isReadOnly());

        try {
            myDataBase.beginTransaction();
            myDataBase.insertOrThrow("ITEM", null, test);
        } finally {
            myDataBase.setTransactionSuccessful();
            myDataBase.endTransaction();
        }
        myDataBase.close();
    }

    public void insertQueryBuy(String item,int buyAmount, int buyPrice) {
        SQLiteDatabase myDataBase = this.getWritableDatabase();
        myDataBase.beginTransaction();
        try {
            myDataBase.execSQL("insert into ITEM_BUY(item_name, buy_amount, buy_price) values( "
                    + "'"+item+ "'"
                    + ", " + buyAmount +
                    ", " + buyPrice + ")");
        }finally{
            myDataBase.setTransactionSuccessful();
            myDataBase.endTransaction();
        }
        myDataBase.close();



    }
    public Spinner updateScrollPane(Spinner spinner, Context main){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select  _id, item_name from ITEM Order by item_name ASC;";
        Cursor cursor = db.rawQuery(query, null);
        String[] item = {"item_name"};
        int[] toView = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(main,R.layout.support_simple_spinner_dropdown_item, cursor, item, toView,0);
        spinner.setAdapter(adapter);
        db.close();
        return spinner;
    }

    public Item checkMinAndMax(Item item){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT _id, item_min, item_max, item_name FROM ITEM WHERE item_name = '" + item.getItemName() + "';";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        item.setMin(cursor.getInt(cursor.getColumnIndex("item_min")));
        item.setMax(cursor.getInt(cursor.getColumnIndex("item_max")));
        cursor.close();
        db.close();
        db = this.getWritableDatabase();
        if (item.getMin() <=item.getBuyPrice()  && item.getBuyPrice() <= item.getMax()){
        }else if (item.getMin() > item.getBuyPrice()){
            //update query to new min price

            item.setMin(item.getBuyPrice());
            query = "update ITEM set item_min = " + item.getBuyPrice() + " where item_name = '" + item.getItemName() + "';";
            db.beginTransaction();
            try{
                db.execSQL(query);
            }finally {
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        }else{
            //update quer to new max price
            item.setMax(item.getBuyPrice());
            query = "update ITEM set item_max = " + item.getBuyPrice() + " where item_name = '" + item.getItemName() + "';";
            db.beginTransaction();
            try{
                db.execSQL(query);
            }finally {
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        }
        return item;
    }

}

