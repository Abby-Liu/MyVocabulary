//ViewAll
package com.example.welll.myvocabulary;
import android.content.Intent;
import android.database.Cursor;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PageView extends Activity {
    private WordDbHelper wordDb; //mHelper

    private ListView mWordListView; // for ArrayAdapter
    private ListView listView; //for SimpleCursorAdapter
    private SimpleCursorAdapter adapter;
    private ArrayAdapter<String> mAdapter;
    final String[] from = new String[] {
            WordContract.WordEntry._ID, WordContract.WordEntry.COL_1, WordContract.WordEntry.COL_2
    };
    final int[] to = new int[] {R.id.id, R.id.en, R.id.cn};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.empty_list);
        super.onCreate(savedInstanceState);

        wordDb = new WordDbHelper(this);
        mWordListView = (ListView)findViewById(R.id.listView);
        listView = findViewById(R.id.list_view); // @R.layout.empty_list
        SimpleCursorAdapterView();
        ModifyWord();
    }
    public void SimpleCursorAdapterView(){
        SQLiteDatabase db = wordDb.getReadableDatabase();
        Cursor cursor = db.query(WordContract.WordEntry.TABLE,
                new String []{WordContract.WordEntry._ID, WordContract.WordEntry.COL_1,
                        WordContract.WordEntry.COL_2},
                null, null, null, null, null);

        listView.setEmptyView(findViewById(R.id.empty)); // @R.layout.empty_list

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor,
                from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

    }

    private void ModifyWord(){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView enTextView = (TextView) view.findViewById(R.id.en);
                TextView cnTextView = (TextView) view.findViewById(R.id.cn);

                String id = idTextView.getText().toString();
                String en = enTextView.getText().toString();
                String cn = cnTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyWordActivity.class);
                modify_intent.putExtra("ENGLISH", en);
                modify_intent.putExtra("CHINESE", cn);
                modify_intent.putExtra("ID", id);

                startActivity(modify_intent);
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
//            int idx = cursor.getColumnIndex(WordContract.WordEntry._ID);
            //Add by me
            int idx_en = cursor.getColumnIndex(WordContract.WordEntry.COL_1);
//            int idx_cn = cursor.getColumnIndex(WordContract.WordEntry.COL_2);
//            arrayList.add(cursor.getString(idx));
            arrayList.add(cursor.getString(idx_en));
//            arrayList.add(cursor.getString(idx_cn));
        }

        if(mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this, R.layout.activity_view_record, R.id.en, arrayList);
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

}
