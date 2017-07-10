package com.example.welll.myvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class WordDbHelper  extends SQLiteOpenHelper{
    private Context myContext;
    public static final String DB_PATH  = "/databases";
    public static final String DB_NAME = "toefl.sql";

    public WordDbHelper(Context context){
        super(context, WordContract.DB_NAME, null, WordContract.DB_VERSION);
        this.myContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableWord = "CREATE TABLE " + WordContract.WordEntry.TABLE + " ( "
                + WordContract.WordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordContract.WordEntry.COL_1 + " TEXT NOT NULL,"
                + WordContract.WordEntry.COL_2 + " TEXT NOT NULL,"
                + WordContract.WordEntry.COL_3 + " TEXT);";
        String createTableToefl = "CREATE TABLE IF NOT EXISTS" + WordContract.ToeflEntry.TABLE + " ( "
                + WordContract.ToeflEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordContract.ToeflEntry.COL_1 + " TEXT NOT NULL,"
                + WordContract.ToeflEntry.COL_2 + " TEXT NOT NULL,"
                + WordContract.ToeflEntry.COL_3 + " TEXT);";

        db.execSQL(createTableWord);
        db.execSQL(createTableToefl);
        //Toast.makeText(myContext, "You Created Tables",Toast.LENGTH_SHORT).show();
        Log.v("Log ","kkk");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WordContract.WordEntry.TABLE);
        onCreate(db);
        Log.v("Log ","kkk2");
    }

    public boolean insertWord(String en, String cn, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result;
        if(en.isEmpty() || cn.isEmpty()){
            return false;
        }
        else{
            contentValues.put(WordContract.WordEntry.COL_1, en);
            contentValues.put(WordContract.WordEntry.COL_2, cn);
            contentValues.put(WordContract.WordEntry.COL_3, notes);
            result = db.insert(WordContract.WordEntry.TABLE, null, contentValues);
            return true;
        }
    }

//    public Cursor getCursor(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {WordContract.WordEntry._ID, WordContract.WordEntry.COL_1,
//                WordContract.WordEntry.COL_2};
//        Cursor cursor = db.query(WordContract.WordEntry.TABLE, columns, null, null, null, null, null);
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

//    public Cursor getAllWord() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + WordContract.WordEntry.TABLE, null);
//        return res;
//    }

    public class Configuration {
        public static final int DB_VERSION = 1;
        public static final int oldVersion = -1;
    }
}
