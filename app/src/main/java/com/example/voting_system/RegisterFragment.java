package com.example.voting_system;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class RegisterFragment extends Fragment {

    RadioButton type;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btn = (Button) getActivity().findViewById(R.id.submit_register);
        EditText username  = (EditText) getActivity().findViewById(R.id.username_register);
        EditText password  = (EditText) getActivity().findViewById(R.id.password_register);
        EditText password_confirm  = (EditText) getActivity().findViewById(R.id.password_confirm);
        RadioGroup group = (RadioGroup) getActivity().findViewById(R.id.type_user);
        type = getActivity().findViewById(R.id.voter);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = group.getCheckedRadioButtonId();

                type = (RadioButton) getActivity().findViewById(selectedId);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(username.getText().toString().trim() == "" || password.getText().toString().trim() == ""  || password_confirm.getText().toString().trim() == "") {
                    Toast.makeText(getActivity(), "Заборавивте да внесете корисничко име или лозинка", Toast.LENGTH_SHORT).show();
                }else if(!password.getText().toString().trim().equals(password_confirm.getText().toString().trim())){
                    Toast.makeText(getActivity(), "Внесовте различни лозинки", Toast.LENGTH_SHORT).show();
                }else{
                    createUser(username.getText().toString().trim(), password.getText().toString().trim(), type.getText().toString());
                }
            }
        });
    }

    public void createUser(String username, String password, String type) {
        SQLiteDatabase db;
        db = getActivity().openOrCreateDatabase("users", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password VARCHAR NOT NULL, type VARCHAR);");
        int flag = 0;
        if(type.equals("Администратор")) {
            Cursor search_admin = db.rawQuery("SELECT * FROM users WHERE type='Администратор'", null);
            if (search_admin.moveToFirst()) {
                flag = 1;
            }
            search_admin.close();
        }
        Toast.makeText(getActivity(), ""+flag, Toast.LENGTH_SHORT).show();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password +"'",null);
            if(flag == 1 || c.moveToFirst()){
                Toast.makeText(getActivity(), "Веќе таков корисник веќе постои", Toast.LENGTH_SHORT).show();
            }else{
                db.execSQL("INSERT INTO users (username, password, type) VALUES('" + username + "', '" + password +"', '" + type +"' );");
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        }
    }