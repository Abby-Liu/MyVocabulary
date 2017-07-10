package com.example.welll.myvocabulary;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class PageViewToefl extends ListActivity {

    //private WordDbHelper wordDb; //mHelper
    private ToeflDbHelper db;
    private Cursor cursor;

    //private ListView listView; //for SimpleCursorAdapter
    //private SimpleCursorAdapter adapter;

//    final String[] from = new String[] {
//            WordContract.ToeflEntry._ID, WordContract.ToeflEntry.COL_1, WordContract.ToeflEntry.COL_2
//    };
//    final int[] to = new int[] {R.id.id, R.id.en, R.id.cn};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toefl_list);

        db = new ToeflDbHelper(this);

        try{
            db.createDataBase();
        } catch (IOException e){
            throw new Error("Unable to create db");
        }

        try {
            db.openDataBase();
        } catch (SQLException e){
            throw e;
        }
    }


}
