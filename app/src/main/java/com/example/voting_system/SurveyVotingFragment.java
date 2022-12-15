package com.example.voting_system;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SurveyVotingFragment extends Fragment {

    public SurveyVotingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_voting, container, false);
    }//

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String title_text = getActivity().getIntent().getStringExtra("title");
        SQLiteDatabase db = getActivity().openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
        Cursor questions = db.rawQuery("SELECT question,choises FROM questions WHERE title='" + title_text.trim() + "';",null);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linear_layout_voting);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView title = (TextView) getActivity().findViewById(R.id.title_survey);
        title.setText(title_text);

        if(questions.moveToFirst()){
            while(!questions.isAfterLast()){
                TextView question = new TextView(getContext());
                question.setGravity(View.TEXT_ALIGNMENT_CENTER);
                question.setGravity(View.TEXT_ALIGNMENT_CENTER);
                question.setText(questions.getString(questions.getColumnIndex("question")));

                RadioGroup rgroup = new RadioGroup(getContext());
                rgroup.setOrientation(LinearLayout.HORIZONTAL);
                String[] choises = questions.getString(questions.getColumnIndex("choises")).split(",");
                for(int i=0;i<choises.length;i++){
                    RadioButton button = new RadioButton(getContext());
                    button.setText(choises[i]);
                    rgroup.addView(button);
                    if(i == 0){
                        rgroup.check(button.getId());
                    }
                }

                layout.addView(question);
                layout.addView(rgroup);
                questions.moveToNext();
            }
        }
        db.close();
    }
}