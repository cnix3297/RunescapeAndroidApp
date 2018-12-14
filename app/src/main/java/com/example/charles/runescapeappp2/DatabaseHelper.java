package com.example.charles.runescapeappp2;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "rsItemPrice";
    private SQLiteDatabase myDataBase;
    private static final int DB_VERSION = 10;
    private SQLiteDatabase mDataBase;
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

    public void updateDataBase() throws IOException {
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

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }


    public void insertQueryItem(String itemName){
        ContentValues test = new ContentValues();
        test.put("item_name", itemName);
        test.put("item_min", 0);
        test.put("item_max", 0);
        //myDataBase.beginTransaction();
        Log.d("database entry", ""+ myDataBase.isOpen());
        myDataBase.insert("ITEM", null, test);
        /*try {
            //myDataBase.beginTransaction();
            myDataBase.insert("ITEM", null, test);
            Log.d("database entry", "database insert failed");

        } finally {

            myDataBase.endTransaction();
        }*/

    }

    public void insertQueryBuy(String item,int buyAmount, int buyPrice) {

        Log.d("database entry", ""+ myDataBase.isOpen());
        myDataBase.beginTransaction();
        Log.d("database entry", ""+ myDataBase.isOpen());
        try {
            myDataBase.execSQL("insert into ITEM(item_name, item_min, item_max) values("
                    + "'"+item +"'"
                    + ", " + 0 +
                    ", " + 0 + ")");
            Log.d("database entry", ""+ myDataBase.isOpen());
            myDataBase.setTransactionSuccessful();
            Log.d("database entry", ""+ myDataBase.isOpen());
        }finally {
            myDataBase.endTransaction();

        }

        myDataBase.beginTransaction();
        try {
            String selectQuery = "SELECT item_name FROM ITEM WHERE item_name = " + "'" +item +"'";
            Cursor c = myDataBase.rawQuery(selectQuery,null);
            String test = c.getString(c.getPosition());
            Log.d("database entry", test);
            myDataBase.setTransactionSuccessful();
        }finally{
            myDataBase.endTransaction();
        }


        /* myDataBase.execSQL("insert into ITEM_BUY(item_name, buy_amount, buy_price) values( "
                 + "'"+item+ "'"
                 + ", " + buyAmount +
                 ", " + buyPrice + ")");*/
         /*myDataBase.setTransactionSuccessful();
         myDataBase.endTransaction();*/
         Log.d("database entry", "database insert");
    }
}