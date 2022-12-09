package com.example.voting_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
                    if(item.getTitle().toString().equals("Гласање")){
                        if(!getActivity().getClass().getSimpleName().equals("MainActivity")){
                        Intent intent_redirect = new Intent(getActivity(), MainActivity.class);
                        intent_redirect.putExtra("type",type);
                        intent_redirect.putExtra("username", username);
                        startActivity(intent_redirect);
                        }
                    }else if (item.getTitle().toString().equals("Резултати")){
                        if(!getActivity().getClass().getSimpleName().equals("ResultActivity")){
                        Intent intent_redirect = new Intent(getActivity(), ResultActivity.class);
                        intent_redirect.putExtra("type",type);
                        intent_redirect.putExtra("username", username);
                        startActivity(intent_redirect);
                        }
                    }else if(item.getTitle().toString().equals("Креирај")){
                        if(!getActivity().getClass().getSimpleName().equals("CreateActivity")){
                        Intent intent_redirect = new Intent(getActivity(), CreateActivity.class);
                        intent_redirect.putExtra("type",type);
                        intent_redirect.putExtra("username", username);
                        startActivity(intent_redirect);
                        }
                    }
                    return true;
                }
            });
        }
    }
}