package com.example.welll.myvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    WordDbHelper wordDb;
    Button btn_view, btn_mem, btn_add;
    EditText ed_en, ed_cn, ed_notes;
    private ListView mWordListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordDb = new WordDbHelper(this);
        //TODO
        mWordListView = (ListView) findViewById(R.id.list_main);

        btn_view = (Button)findViewById(R.id.btn_view);
        btn_mem = (Button)findViewById(R.id.btn_mem);
        btn_add = (Button)findViewById(R.id.btn_add);
        ed_en = (EditText) findViewById(R.id.ed_en);
        ed_cn = (EditText) findViewById(R.id.ed_cn);
        ed_notes = (EditText) findViewById(R.id.ed_notes);

        //TestView();
        AddWord();
        HideKeyboard();

        //Get to view all words
        btn_view.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent();
                it1.setClass(MainActivity.this, PageView.class);
                //切換到intent View1
                startActivity(it1);

                //關閉MainActivity
                //MainActivity.this.finish();
            }
        });

        btn_mem.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it2 = new Intent();
                it2.setClass(MainActivity.this, PageMem.class);
                startActivity(it2);
            }
        });



    }
    public void TestView(){
        /*** Declaration in global: ***/
//        private ListView mWordListView;
//        private ArrayAdapter<String> mAdapter;
//        wordDb = new WordDbHelper(this);
        /*** Declaration in onCreate() ***/
        //mWordListView = (ListView) findViewById(R.id.list_main);
        // R.id.list_main is the ListView id of current layout.

        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = wordDb.getReadableDatabase();
        Cursor cursor = db.query(WordContract.WordEntry.TABLE,
                new String []{WordContract.WordEntry._ID, WordContract.WordEntry.COL_1,
                        WordContract.WordEntry.COL_2},
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(WordContract.WordEntry.COL_1);
            arrayList.add(cursor.getString(idx));
        }

        if(mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this, R.layout.activity_view_record, R.id.id, arrayList);
            mWordListView.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(arrayList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }
    public void AddWord(){
        btn_add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = wordDb.insertWord(ed_en.getText().toString(), ed_cn.getText().toString(), ed_notes.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this, "Word:"+ed_en.getText()+" is Added!", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void HideKeyboard(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ed_en.getWindowToken(),0);
                imm.hideSoftInputFromWindow(ed_cn.getWindowToken(),0);
                imm.hideSoftInputFromWindow(ed_notes.getWindowToken(),0);
            }
        });
    }


}
