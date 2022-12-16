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

import java.util.Random;


public class LoginFragment extends Fragment {

    private SQLiteDatabase db;

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
        this.db = getActivity().openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
        this.db.execSQL("CREATE TABLE IF NOT EXISTS users(username VARCHAR PRIMARY KEY, password VARCHAR NOT NULL, type VARCHAR NOT NULL, latitude INT, longitude INT);");
        this.db.execSQL("CREATE TABLE IF NOT EXISTS polls(title VARCHAR NOT NULL PRIMARY KEY, visible VARCHAR NOT NULL);");
        this.db.execSQL("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR NOT NULL, question VARCHAR NOT NULL, choises VARCHAR NOT NULL," +
                "FOREIGN KEY(title) REFERENCES polls(title) ON DELETE CASCADE);");
        this.db.execSQL("CREATE TABLE IF NOT EXISTS summary_poll(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, title VARCHAR NOT NULL, question VARCHAR NOT NULL, chose VARCHAR NOT NULL," +
                "FOREIGN KEY(title) REFERENCES polls(title) ON DELETE CASCADE," +
                "FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE);");

        Cursor c = this.db.rawQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password +"'",null);
        if(c.moveToFirst()){
            getLocation(username);
            int i = c.getColumnIndex("type");
            if(c.getString(i).equals("Администратор")){
                Intent intent = new Intent(getActivity(), ResultActivity.class);
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

    private void getLocation(String username){
        Random random = new Random();
        int lat = random.nextInt(47-39) + 39;
        int lon = random.nextInt(31-14) + 14;

        Toast.makeText(getActivity(), lat+","+lon, Toast.LENGTH_SHORT).show();
        this.db.execSQL("UPDATE users SET latitude='" + lat +"', longitude='" + lon + "' WHERE username='" + username + "';");
    }


}