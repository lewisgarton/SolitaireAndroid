package com.example.lewis.solitairtest;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {
    public Button btnNewGame, btnResume, btnStats, btnSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        btnNewGame = (Button) v.findViewById(R.id.btn_new_game);
        btnResume = (Button) v.findViewById(R.id.btn_resume_game);
        btnStats = (Button) v.findViewById(R.id.btn_stats);
        btnSettings = (Button) v.findViewById(R.id.btn_settings);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((MainActivity) getContext()).newGameAction();
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).resumeGameAction();
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).statsAction();

            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).settingsAction();
            }
        });


        return v;
    }

}

