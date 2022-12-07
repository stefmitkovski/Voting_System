package com.example.voting_system;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuFragment extends Fragment {

    String username = "";
    String type = "";

    public BottomMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        type = intent.getStringExtra("type");
        if (username == null || type == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_toolbar);

            if (type.equals("Администратор")) {
                bottomNavigationView.getMenu().removeItem(R.id.current_polls);
            } else {
                bottomNavigationView.getMenu().removeItem(R.id.create_pools);
            }

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Toast.makeText(getActivity(), ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}