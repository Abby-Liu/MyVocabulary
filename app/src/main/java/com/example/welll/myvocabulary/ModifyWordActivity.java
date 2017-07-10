package com.example.welll.myvocabulary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyWordActivity extends Activity{
    private EditText ed_en, ed_cn, ed_notes;
    private Button btn_update, btn_delete;
    private long _ID;
    private WordDbHelper wordDb;
    private String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify Word Record");
        setContentView(R.layout.activity_modify_record);

        wordDb = new WordDbHelper(this);
        SQLiteDatabase db = wordDb.getWritableDatabase();

        ed_en = findViewById(R.id.mod_en);
        ed_cn = findViewById(R.id.mod_cn);
        ed_notes = findViewById(R.id.mod_notes);

        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        String en = intent.getStringExtra("ENGLISH");
        String cn = intent.getStringExtra("CHINESE");

//        Log.v("Log id=",id);
//        Log.v("Log en=",en);
//        Log.v("Log cn=",id);
        Toast.makeText(getApplicationContext(), id+" "+en+" "+cn,Toast.LENGTH_SHORT).show();

        _ID = Long.parseLong(id);
        ed_en.setText(en);
        ed_cn.setText(cn);

        Cursor c = SelectNotesCursor(db,id);
        String notes = c.getString(c.getColumnIndex(WordContract.WordEntry.COL_3));

        ed_notes.setText(notes);

//        Cursor c2 = TestToeflCursor(db, "1");
//        String testToeflStr = c2.getString(c.getColumnIndex(WordContract.ToeflEntry.COL_1));
//        Toast.makeText(getApplicationContext(), testToeflStr ,Toast.LENGTH_LONG).show();

        //Toast.makeText(getApplicationContext(), notes,Toast.LENGTH_LONG).show();
        //Log.v("Log string=",str);


        btn_update.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = wordDb.getWritableDatabase();
                ContentValues cv = new ContentValues();

                String en = ed_en.getText().toString();
                String cn = ed_cn.getText().toString();
                String notes = ed_notes.getText().toString();

                cv.put("ENGLISH", en);
                cv.put("CHINESE", cn);
                cv.put("NOTES", notes);

                db.update(WordContract.WordEntry.TABLE, cv, "_ID="+_ID, null);
                Toast.makeText(getApplicationContext(), "You want to update to " + notes,Toast.LENGTH_SHORT).show();
                returnHome();
            }
        });

        btn_delete.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                SQLiteDatabase db = wordDb.getWritableDatabase();
                //ContentValues cv = new ContentValues();
                db.delete(WordContract.WordEntry.TABLE, "_ID=?", new String[] {String.valueOf(_ID)});
                returnHome();
            }
        });

    }

    public Cursor SelectNotesCursor(SQLiteDatabase db, String id){
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + WordContract.WordEntry.TABLE
                +" WHERE _ID = ? ", new String[] {id});
        if(cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor TestToeflCursor (SQLiteDatabase db, String id){
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + WordContract.ToeflEntry.TABLE
                +" WHERE _ID = ? ", new String[] {id});
        if(cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

//    @Override
//    public void onClick(View v) {
//        ContentValues cv = new ContentValues();
//        SQLiteDatabase db = wordDb.getWritableDatabase();
//        switch (v.getId()) {
//            case R.id.btn_update:
//                Toast.makeText(getApplicationContext(), "You want to update",Toast.LENGTH_SHORT).show();
//                String en = ed_en.getText().toString();
//                String cn = ed_cn.getText().toString();
//                String notes = ed_notes.getText().toString();
//                cv.put("ENGLISH", en);
//                cv.put("CHINESE", cn);
//                cv.put("NOTES", notes);
//                db.update(WordContract.WordEntry.TABLE, cv, "_ID"+_ID, null);
//                this.returnHome();
//                break;
//            case R.id.btn_delete:
//                db.delete(WordContract.WordEntry.TABLE, "_ID=?", new String[] {String.valueOf(_ID)});
//                this.returnHome();
//                break;
//
//        }
//
//    }
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), PageView.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

    //Query Arguments: 7 args
    //    String table , String[] columns,
    //    String selection, String[]selectionArgs,
    //    String groupBy, String having, String orderBy
}
