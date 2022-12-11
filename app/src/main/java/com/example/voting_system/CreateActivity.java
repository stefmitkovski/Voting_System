package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    Integer i = 1;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button create = findViewById(R.id.create_button);
        Button submit = findViewById(R.id.submit_button);
        LinearLayout layout = findViewById(R.id.linear_layout);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText question = new EditText(view.getContext());
                question.setGravity(View.TEXT_ALIGNMENT_CENTER);
                question.setHint("Внеси го текстот за " + i + "-то прашање");
                EditText chooses = new EditText(view.getContext());
                chooses.setGravity(View.TEXT_ALIGNMENT_CENTER);
                chooses.setHint("Внесиги можните избори одалечени со ,");

                i++;
                layout.addView(question);
                layout.addView(chooses);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
                EditText title = (EditText) findViewById(R.id.title);
                Spinner spinner = findViewById(R.id.timer);
                String timer = spinner.getSelectedItem().toString();

                switch (timer){
                    case "5 минути":
                        timer = "5";
                        break;
                    case "10 минути":
                        timer = "10";
                        break;
                    case "15 минути":
                        timer = "15";
                        break;
                    case "30 минути":
                        timer = "30";
                        break;
                    case "60 минути":
                        timer = "60";
                        break;
                    default: timer = "5";
                }

                if(!title.getText().toString().trim().isEmpty()) {
                    Cursor c = db.rawQuery("SELECT * FROM polls WHERE title='" + title.getText().toString() + "'", null);
                    if (c.moveToFirst()) {
                        Toast.makeText(CreateActivity.this, "Веќе постои таква анкета", Toast.LENGTH_SHORT).show();
                    } else {
                        db.execSQL("INSERT INTO polls (title, visible) VALUES('" + title.getText().toString() + "', '" + "yes" + "');");
                        for (int k = 1; k < layout.getChildCount(); k += 2) {
                            EditText question = (EditText) layout.getChildAt(k - 1);
                            EditText choices = (EditText) layout.getChildAt(k);
                            if (question.getText().toString().trim().isEmpty() || choices.getText().toString().trim().isEmpty()) {
                                continue;
                            }

                            List<String> list = new ArrayList<String>(Arrays.asList(choices.getText().toString().trim().split(",")));
                            if (list.size() < 5) {
                                Toast.makeText(CreateActivity.this, "Внесовте помалце од 5 можности на " + k + "-то прашање", Toast.LENGTH_SHORT).show();
                            } else {
                                db.execSQL("INSERT INTO questions (title, question, choises) VALUES ('" + title.getText().toString() + "', '" + question.getText().toString() + "', '" + choices.getText().toString() + "');");
                            }
                        }
                        Cursor check = db.rawQuery("SELECT * FROM polls WHERE title='" + title.getText().toString() + "'", null);
                        if (check.getCount() > 0) {
                            Intent successful_intent = new Intent(view.getContext(), MainActivity.class);
                            successful_intent.putExtra("type", getIntent().getStringExtra("type"));
                            successful_intent.putExtra("username", getIntent().getStringExtra("username"));
                            successful_intent.putExtra("title", title.getText().toString());
                            successful_intent.putExtra("timer", timer);
                            startActivity(successful_intent);
                        }
                    }
                } else{
                    Toast.makeText(CreateActivity.this, "Заборавивте да внесете наслов", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}