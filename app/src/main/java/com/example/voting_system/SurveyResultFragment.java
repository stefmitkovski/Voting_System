package com.example.voting_system;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyResultFragment extends Fragment {

    public SurveyResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_result, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SQLiteDatabase db = getActivity().openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linear_layout_result);
        TextView survey_result_title = (TextView) getActivity().findViewById(R.id.title_result_survey);
        String title = getActivity().getIntent().getStringExtra("title");
        Cursor questions = db.rawQuery("SELECT DISTINCT question FROM summary_poll WHERE title='" + title + "';",null);
        if(questions.moveToFirst()) {
            survey_result_title.setText(title);
            while (!questions.isAfterLast()) {
                TextView txt = new TextView(getContext());
                String squestion = questions.getString(questions.getColumnIndex("question"));
                txt.setText("На прашањето: \" " + squestion + " \" ");
                layout.addView(txt);
                Cursor choices = db.rawQuery("SELECT DISTINCT chose FROM summary_poll WHERE title='" + title + "' AND question='" + squestion + "';", null);
                choices.moveToFirst();
                while (!choices.isAfterLast()) {
                    String schoices = choices.getString(choices.getColumnIndex("chose"));
                    TextView total = new TextView(getContext());
                    Cursor users = db.rawQuery("SELECT * FROM summary_poll WHERE title='" + title + "' AND question='" + squestion + "' AND chose='" + schoices + "';", null);
                    total.setText("Како одговор: \" " + schoices + " \" дале вкупно " + users.getCount() + " корисници");
                    layout.addView(total);
                    choices.moveToNext();
                }
                questions.moveToNext();
            }
        }else{
            survey_result_title.setText("Никој не гласал уште");
        }
    }
}