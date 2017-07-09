package com.example.welll.myvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class WordDbHelper  extends SQLiteOpenHelper{

    public WordDbHelper(Context context) {
        super(context, WordContract.DB_NAME, null, WordContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + WordContract.WordEntry.TABLE + " ( "
                + WordContract.WordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordContract.WordEntry.COL_1 + " TEXT NOT NULL,"
                + WordContract.WordEntry.COL_2 + " TEXT NOT NULL,"
                + WordContract.WordEntry.COL_3 + " TEXT);";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WordContract.WordEntry.TABLE);
        onCreate(db);
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
}
