package com.example.lewis.solitairtest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;


public class MainActivity extends AppCompatActivity {

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
        adapter.addFragment(new GameFragment());
        viewPager.setAdapter(adapter);
        //adapter.addFragment(new GameFragment());
        //adapter.addFragment(new ScoresFragment());
        //adapter.addFragment(new SettingsFragment());
    }

    public void setViewPager(int fragNumber){
        viewPager.setCurrentItem(fragNumber);
    }


}
