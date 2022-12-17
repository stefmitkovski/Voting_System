package com.example.voting_system;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView voting_polls;
    static PollAdapter pAdapter;
    static List<String> values = new ArrayList<>();
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voting_polls = (RecyclerView) findViewById(R.id.list_voting_polls);

        voting_polls.setLayoutManager(new LinearLayoutManager(this));

        voting_polls.setItemAnimator(new DefaultItemAnimator());

        if(getIntent().getStringExtra("title") != null && getIntent().getStringExtra("timer") != null){
            values.add(getIntent().getStringExtra("title"));

            pAdapter = new PollAdapter(values, R.layout.polls, this,Integer.parseInt(getIntent().getStringExtra("timer")));

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("username",getIntent().getStringExtra("username"));
            intent.putExtra("type",getIntent().getStringExtra("type"));
            startActivity(intent);
        }


            voting_polls.setAdapter(pAdapter);
//
//        db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
//        Cursor items = db.rawQuery("SELECT * FROM polls WHERE visible='yes';",null);
//
//        if(items.moveToFirst()) {
//            while (!items.isAfterLast()) {
//                values.add(items.getString(items.getColumnIndex("title")));
//                items.moveToNext();
//            }
//        }
    }

    public void updateVisibility(String value){
        this.db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
        this.db.execSQL("UPDATE polls SET visible = 'no' WHERE title = '"+ value +"';");
    }

    public void redirect(String title){
        Intent survey_intent = new Intent(this, SurveyVotingActivity.class);
        survey_intent.putExtra("username", getIntent().getStringExtra("username"));
        survey_intent.putExtra("type", getIntent().getStringExtra("type"));
        survey_intent.putExtra("title",title);
        startActivity(survey_intent);
    }
}