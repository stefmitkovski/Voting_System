package com.example.voting_system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    RecyclerView voting_polls;
    static PollAdapter pAdapter;
    static List<String> values = new ArrayList<>();
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationService.ALERT_DONE);
        registerReceiver(new MyReceiver(), filter);

//        long millis = (System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)) - System.currentTimeMillis();
//        Toast.makeText(this, TimeUnit.MILLISECONDS.toMinutes(millis)+"", Toast.LENGTH_SHORT).show();

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

        if(getIntent().getStringExtra("username") != null && getIntent().getStringExtra("type").equals("Гласач")) {
            this.db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE, null);
            Cursor notifications = this.db.rawQuery("SELECT * FROM notifications", null);
            if (notifications.moveToFirst()) {
                while (!notifications.isAfterLast()) {
                    String title = notifications.getString(notifications.getColumnIndex("title"));
                    long created_time = notifications.getLong(notifications.getColumnIndex("created_time"));
                    int timer = Integer.parseInt(notifications.getString(notifications.getColumnIndex("duration")));
                    long current_time = System.currentTimeMillis();
                    if(TimeUnit.MILLISECONDS.toSeconds(current_time - created_time) < TimeUnit.SECONDS.toSeconds(timer)){
                        Intent intent = new Intent(this, NotificationService.class);
                        intent.putExtra("username", getIntent().getStringExtra("username"));
                        intent.putExtra("type", getIntent().getStringExtra("type"));
                        intent.putExtra("title", title);
                        intent.putExtra("time",String.valueOf((TimeUnit.SECONDS.toSeconds(timer) - TimeUnit.MILLISECONDS.toSeconds(current_time - created_time))));
                        intent.setAction(NotificationService.ALERT);
                        startService(intent);
//                        Toast.makeText(this, "Има уште: "+(TimeUnit.SECONDS.toSeconds(timer) - TimeUnit.MILLISECONDS.toSeconds(current_time - created_time)), Toast.LENGTH_SHORT).show();
                    }
                    notifications.moveToNext();
                }
            }
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

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

    private class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(NotificationService.ALERT_DONE)){
            }
        }
    }
}