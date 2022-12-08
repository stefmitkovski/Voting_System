package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    RecyclerView voting_polls;
    PollAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        List<String> values = Arrays.asList("16", "15", "14",
                "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1");

        voting_polls = (RecyclerView) findViewById(R.id.list_result_polls);

        voting_polls.setLayoutManager(new LinearLayoutManager(this));

        voting_polls.setItemAnimator(new DefaultItemAnimator());

        pAdapter = new PollAdapter(values, R.layout.polls, this);

        voting_polls.setAdapter(pAdapter);
    }
}