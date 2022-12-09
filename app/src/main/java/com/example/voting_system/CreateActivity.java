package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    Integer i = 1;

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
                for(int k=1;k<layout.getChildCount();k+=2){
                    EditText question = (EditText) layout.getChildAt(k-1);
                    EditText chooses = (EditText) layout.getChildAt(k);
                    if(question.getText().toString().trim().isEmpty() || chooses.getText().toString().trim().isEmpty()){
                        continue;
                    }
                    List<String> list = new ArrayList<String>(Arrays.asList(chooses.getText().toString().trim().split(",")));
                    if(list.size() < 5){
                        Toast.makeText(CreateActivity.this, "Внесовте помалце од 5 можности на " + k  + "-то прашање", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CreateActivity.this, "Прашање " + question.getText().toString(), Toast.LENGTH_SHORT).show();
                        for(i=0;i<list.size();i++){
                            Toast.makeText(CreateActivity.this, "Можности " + list.get(i), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}