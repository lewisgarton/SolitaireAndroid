package com.example.lewis.solitairtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity {

    private ScreensPagerAdapter screensPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        screensPagerAdapter = new ScreensPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.fragment_container);
        initViewPager(viewPager);
    }

    private void initViewPager(ViewPager viewPager){
        ScreensPagerAdapter adapter = new ScreensPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment());
        viewPager.setAdapter(adapter);
        //adapter.addFragment(new GameFragment());
        //adapter.addFragment(new ScoresFragment());
        //adapter.addFragment(new SettingsFragment());
    }

    public void setViewPager(int fragNumber){
        viewPager.setCurrentItem(fragNumber);
    }

}
