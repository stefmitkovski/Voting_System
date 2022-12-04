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

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_toolbar);
            top_toolbar.setTitle(type);
            setSupportActionBar(top_toolbar);

            if(type.equals("Администратор")){
                bottomNavigationView.getMenu().removeItem(R.id.current_polls);
            }else{
                bottomNavigationView.getMenu().removeItem(R.id.create_pools);
            }

            bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    Toast.makeText(MainActivity.this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
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