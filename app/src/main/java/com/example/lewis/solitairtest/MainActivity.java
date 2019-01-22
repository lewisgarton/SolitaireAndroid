package com.example.lewis.solitairtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {
    private Bundle savedInstanceState;
    private ScreensPagerAdapter screensPagerAdapter;
    private ViewPager viewPager;
    public GameFragment gameFragment;
    public MenuFragment menuFragment;
    public ScreensPagerAdapter adapter;
    public boolean gameInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.main_screen);
        screensPagerAdapter = new ScreensPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.fragment_container);
        initViewPager(viewPager);

    }

    private void initViewPager(ViewPager viewPager){
        adapter = new ScreensPagerAdapter(getSupportFragmentManager());
        gameFragment = new GameFragment();
        menuFragment = new MenuFragment();
        adapter.addFragment(menuFragment);
        adapter.addFragment(gameFragment);
        viewPager.setAdapter(adapter);
        //adapter.addFragment(new GameFragment());
        //adapter.addFragment(new ScoresFragment());
        //adapter.addFragment(new SettingsFragment());
    }

    public void setViewPager(int fragNumber){
        viewPager.setCurrentItem(fragNumber);
    }

    public void newGameAction(){
        if(gameInProgress) {
            // Alert and confirm user wants to start a new game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.confirm_new_title));
            builder.setMessage(getString(R.string.confirm_new_msg));
            builder.setCancelable(false);

            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    gameFragment.newGame = true;
                    gameFragment.update();
                    setViewPager(1);

                }
            });

            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                }
            });

            builder.show();
        } else {
            gameInProgress = true;
            setViewPager(1);
        }
    }


    public void resumeGameAction(){
        if(gameInProgress) {
            setViewPager(1);
        }else {
                String msg = (String) getText(R.string.not_save);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void statsAction(){
        String msg = (String) getText(R.string.not_implemented);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void settingsAction(){
        String msg = (String) getText(R.string.not_implemented);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
