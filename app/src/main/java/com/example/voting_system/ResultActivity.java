package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    RecyclerView result_polls;
    PollResultAdapter prAdapter;
    List<String> values = new ArrayList<>();
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result_polls = (RecyclerView) findViewById(R.id.list_result_polls);

        result_polls.setLayoutManager(new LinearLayoutManager(this));

        result_polls.setItemAnimator(new DefaultItemAnimator());

        if(getIntent().getStringExtra("type").equals("Администратор")){

            db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);

            c = db.rawQuery("SELECT title FROM polls;",null);

            if(c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    values.add(c.getString(c.getColumnIndex("title")));
                    c.moveToNext();
                }
            }else{
                values = new ArrayList<>();
            }

            prAdapter = new PollResultAdapter(values, R.layout.polls, this);

            result_polls.setAdapter(prAdapter);

        }else{
            db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);

            c = db.rawQuery("SELECT title FROM polls WHERE visible = 'no';",null);

            if(c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    values.add(c.getString(c.getColumnIndex("title")));
                    c.moveToNext();
                }
            }else{
                values = new ArrayList<>();
            }

            prAdapter = new PollResultAdapter(values, R.layout.polls, this);

            result_polls.setAdapter(prAdapter);
        }
    }
}