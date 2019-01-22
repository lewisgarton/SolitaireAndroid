package com.example.lewis.solitairtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lewis.solitairtest.solitairLogic.Card;
import com.example.lewis.solitairtest.solitairLogic.CardInfo;
import com.example.lewis.solitairtest.solitairLogic.Foundation;
import com.example.lewis.solitairtest.solitairLogic.GameState;
import com.example.lewis.solitairtest.solitairLogic.PlayingCardView;
import com.example.lewis.solitairtest.solitairLogic.SolitaireGame;
import com.example.lewis.solitairtest.solitairLogic.Tableau;

import java.util.ArrayList;

/**
 * MainActivity is the GUI entity for the solitaire game
 * All layout is created in code depending on the device screen resolution
 * Passes messages of to the SolitaireGame class and updates activity on each user input.
 */
public class MainActivity extends Activity {
    public int deviceWidth;
    public static int deviceHeight;
    public int colSize;
    public int rowSize;
    public static int cardHeight;




    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        GameState gameState = new GameState(game);
        savedInstanceState.putString("SAVE", gameState.createSave());
    }


    /**
     * onCreate method sets up the GUI and initializes the game.
     * If a current game is saved in savedInstanceState it will be loaded.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the device resolution
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
        colSize = (int) deviceWidth / 7;
        rowSize = (int) deviceHeight / 15;

        cardHeight = (int)(deviceHeight/4.7);




    }



}
