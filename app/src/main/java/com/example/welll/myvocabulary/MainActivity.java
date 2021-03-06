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
    Button btn_view, btn_viewtoefl, btn_mem, btn_add;
    EditText ed_en, ed_cn, ed_notes;
    private ListView mWordListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        wordDb = new WordDbHelper(this);
        //TODO

        btn_view = (Button)findViewById(R.id.btn_view);
        btn_viewtoefl = (Button)findViewById(R.id.btn_viewtoefl);
        btn_mem = (Button)findViewById(R.id.btn_mem);
        btn_add = (Button)findViewById(R.id.btn_add);
        ed_en = (EditText) findViewById(R.id.ed_en);
        ed_cn = (EditText) findViewById(R.id.ed_cn);
        ed_notes = (EditText) findViewById(R.id.ed_notes);

        AddWord();
        HideKeyboard();

        btn_view.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent();
                it1.setClass(MainActivity.this, PageView.class);

                startActivity(it1);  //切換到intent View1
                //MainActivity.this.finish(); //關閉MainActivity
            }
        });

        btn_viewtoefl.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PageViewToefl.class);
                startActivity(intent);
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
