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
import android.widget.TextView;
import android.widget.Toast;



public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            Button submit = (Button) getActivity().findViewById(R.id.submit_login);
            TextView redirect = (TextView) getActivity().findViewById(R.id.redirect_register);
            EditText username = (EditText) getActivity().findViewById(R.id.username_login);
            EditText password = (EditText) getActivity().findViewById(R.id.password_login);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
                        Toast.makeText(getActivity(), "Заборавивте да внесете корисничко име или лозинка", Toast.LENGTH_SHORT).show();
                    }else{
                        checkCredentials(username.getText().toString().trim(),password.getText().toString().trim());
                    }
                }
            });

            redirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(),RegisterActivity.class));
                }
            });
    }

    public void checkCredentials(String username, String password) {
        SQLiteDatabase db;
        db = getActivity().openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password VARCHAR NOT NULL, type VARCHAR NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS polls(title VARCHAR NOT NULL PRIMARY KEY, visible VARCHAR NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR NOT NULL, question VARCHAR NOT NULL, choises VARCHAR NOT NULL, FOREIGN KEY(title) REFERENCES polls(title));");

        Cursor c = db.rawQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password +"'",null);
        if(c.moveToFirst()){
            int i = c.getColumnIndex("type");
            if(c.getString(i).equals("Администратор")){
                Intent intent = new Intent(getActivity(), ResultActivity.class); // Треба ResultActivity.class
                intent.putExtra("username",username);
                intent.putExtra("type",c.getString(i));
                startActivity(intent);
            }else{
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("type",c.getString(i));
                startActivity(intent);
            }

        }else{
            Toast.makeText(getActivity(), "Погрешно корисничко име или лозинка", Toast.LENGTH_SHORT).show();
        }
        c.close();
    }


}