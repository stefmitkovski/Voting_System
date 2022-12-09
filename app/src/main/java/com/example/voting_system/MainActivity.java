package com.example.voting_system;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView voting_polls;
    PollAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> values = Arrays.asList("1", "2", "3",
                "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16");

        voting_polls = (RecyclerView) findViewById(R.id.list_voting_polls);

        voting_polls.setLayoutManager(new LinearLayoutManager(this));

        voting_polls.setItemAnimator(new DefaultItemAnimator());

        pAdapter = new PollAdapter(values, R.layout.polls, this);

        voting_polls.setAdapter(pAdapter);
    }
}