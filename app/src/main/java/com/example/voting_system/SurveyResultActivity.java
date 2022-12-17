package com.example.voting_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class SurveyResultActivity extends AppCompatActivity implements OnMapReadyCallback  {

    public GoogleMap mMap;
    public SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds moveCamera = new LatLngBounds(new LatLng(39, 14), new LatLng(47, 31));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(moveCamera, 0));

        SQLiteDatabase db = openOrCreateDatabase("voting_system_database", Context.MODE_PRIVATE,null);
        Cursor all_users = db.rawQuery("SELECT DISTINCT username FROM summary_poll WHERE title='" + getIntent().getStringExtra("title") + "';",null);
        if(all_users.moveToFirst()){
            mapFragment.getView().setVisibility(View.VISIBLE);
            while(!all_users.isAfterLast()){
                String user = all_users.getString(all_users.getColumnIndex("username"));
                Cursor bounds = db.rawQuery("SELECT * FROM users WHERE username='" + user + "';",null);
                bounds.moveToFirst();
                int latitude = Integer.parseInt(bounds.getString(bounds.getColumnIndex("latitude")));
                int longitude = Integer.parseInt(bounds.getString(bounds.getColumnIndex("longitude")));
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(user));
                all_users.moveToNext();
            }
        }else{
            mapFragment.getView().setVisibility(View.INVISIBLE);
        }
    }
}