package com.example.voting_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String username = "";
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        type = intent.getStringExtra("type");
        if(username == null || type == null){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            Toolbar top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
            top_toolbar.setTitle(type);
            setSupportActionBar(top_toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);

        Toolbar bottom_menu = (Toolbar) findViewById(R.id.bottom_toolbar);
        bottom_menu.inflateMenu(R.menu.bottom_menu);
        if(type.equals("Гласач")){
            MenuItem item = bottom_menu.getMenu().findItem(R.id.create_pools);
            item.setVisible(false);
        }else{
            MenuItem item = bottom_menu.getMenu().findItem(R.id.current_polls);
            item.setVisible(false);
        }

        bottom_menu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        if(item.getItemId() == R.id.logout){
            username="";
            type = "";
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}