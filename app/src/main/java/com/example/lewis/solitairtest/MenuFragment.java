package com.example.lewis.solitairtest;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {
    public Button btnNewGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        btnNewGame = (Button) v.findViewById(R.id.button);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((MainScreen)getActivity()).setViewPager(1);
            }
        });
        return v;
    }

}

