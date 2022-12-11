package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyVotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_voting);

        Button submit = (Button) findViewById(R.id.submit_survey);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(intent);
            }
        });
    }
}