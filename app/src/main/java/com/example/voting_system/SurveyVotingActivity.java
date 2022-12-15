package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyVotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_voting);

        SQLiteDatabase db = openOrCreateDatabase("voting_system_database", MODE_PRIVATE, null);
        Button submit = (Button) findViewById(R.id.submit_survey);
        String title = getIntent().getStringExtra("title");
        String username = getIntent().getStringExtra("username");
        Cursor voted = db.rawQuery("SELECT * FROM summary_poll WHERE username='" + username + "' AND title='" + title + "';", null);
        if (voted.getCount() != 0) {
            Toast.makeText(SurveyVotingActivity.this, "Веќе сте гласале на оваа анкета", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username"));
            intent.putExtra("type", getIntent().getStringExtra("type"));
            startActivity(intent);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = findViewById(R.id.linear_layout_voting);
                for (int k = 1; k < layout.getChildCount(); k += 2) {
                    TextView txt = (TextView) layout.getChildAt(k - 1);
                    RadioGroup rgroup = (RadioGroup) layout.getChildAt(k);
                    RadioButton radio = (RadioButton) rgroup.findViewById(rgroup.getCheckedRadioButtonId());

                    db.execSQL("INSERT INTO summary_poll(username, title, question, chose) VALUES('" + username + "', '" + title + "', '" + txt.getText() + "', '" + radio.getText() + "');");
                }
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(intent);
            }
        });
    }
}