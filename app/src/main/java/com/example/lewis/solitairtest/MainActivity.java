package com.example.lewis.solitairtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.lewis.solitairtest.solitairLogic.*;
import java.util.ArrayList;

public class MainActivity extends Activity {
    public ArrayList<ImageView> cardViews = new ArrayList<ImageView>();
    public FrameLayout deckFrame, deckTopFrame;
    //public ArrayList<FrameLayout> foundations = new ArrayList<FrameLayout>();
    //public ArrayList<LinearLayout> tableauLayouts = new ArrayList<LinearLayout>();
    public SolitaireGame game;
    public CardLocation selectedCard, destinationCard;
    public boolean stateChanged;
    public ArrayList<FrameLayout> foundations = new ArrayList<>();
    public ArrayList<LinearLayout> tableauLayouts = new ArrayList<LinearLayout>();
    public int deviceWidth, deviceHeight, colSize, rowSize;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get the device resolution
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
        colSize = (int) deviceWidth/7;
        rowSize = (int) deviceHeight/15;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new SolitaireGame();
        game.resetBoard();
        stateChanged = true;
        selectedCard = null;
        destinationCard = null;
        LinearLayout top_layout = (LinearLayout) findViewById(R.id.layout_foundations);
        LinearLayout bottom_layout = (LinearLayout) findViewById(R.id.layout_tableaus);



        for(int i = 0; i < 7; i++) {
            // Set up to row layouts
            FrameLayout l = new FrameLayout(this);
            l.setLayoutParams(new LinearLayout.LayoutParams(colSize, ViewGroup.LayoutParams.MATCH_PARENT));
            if(i == 0) deckFrame = l;
            if(i == 1) deckTopFrame = l;
            if(i > 2) foundations.add(l);
            top_layout.addView(l);

            // Set up bottom layouts
            LinearLayout k = new LinearLayout(this);
            k.setLayoutParams(new LinearLayout.LayoutParams(colSize, ViewGroup.LayoutParams.MATCH_PARENT));
            k.setOrientation(LinearLayout.VERTICAL);
            bottom_layout.addView(k);
            tableauLayouts.add(k);
        }


        //foundations.add((FrameLayout) findViewById(R.id.frame_foundation1));
        //foundations.add((FrameLayout) findViewById(R.id.frame_foundation2));
        //foundations.add((FrameLayout) findViewById(R.id.frame_foundation3));
        //foundations.add((FrameLayout) findViewById(R.id.frame_foundation4));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau1));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau2));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau3));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau4));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau5));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau6));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau7));
        //deckFrame = foundations.get(0);
        //deckTopFrame = foundations.get(1);
        update();
    }

    public void update() {
        if(selectedCard != null && destinationCard == null){
            game.cardClicked(selectedCard);
            selectedCard = null;
        } else if(selectedCard != null && destinationCard != null){
            game.cardClicked(selectedCard, destinationCard);
            selectedCard = null;
            destinationCard = null;
        }
        updateTableus();
        updateFoundations();
    }

    public void updateTableus() {
        // Create the card placement frames
        clear();


        if(game.stock.size() > 0) {
            Card c = game.stock.viewTop();
            deckFrame.addView(new CardImageView(this, c.getCardId(), false, 0, 0, SolitaireGame.Location.STOCK));
        } else {
            deckFrame.addView(new CardImageView(this, -1, true, 0, 0, SolitaireGame.Location.STOCK));
        }

        if(game.wastePile.size() > 0) {
            Card c = game.wastePile.viewTop();
            deckTopFrame.addView(new CardImageView(this, c.getCardId(), c.isFaceUp,  0, 0, SolitaireGame.Location.WASTEPILE));
        }

        // linking to child views
        CardImageView prevCardView = null;

        Tableau t;
        for (int i = 0; i < game.tableaus.size(); i++) {
            t = game.tableaus.get(i);
            prevCardView = null;
            if(t.size() == 0){
                tableauLayouts.get(i).addView(new CardImageView(this, -1, true, i, -1, SolitaireGame.Location.TABLEAU));
            }else {
                for (int j = 0; j < t.size(); j++) {
                    Card c = t.cardAt(j);
                    CardImageView cardView = new CardImageView(this, c.getCardId(), c.isFaceUp, i, j, SolitaireGame.Location.TABLEAU);
                    if(prevCardView != null) prevCardView.child = cardView;
                    prevCardView = cardView;
                    tableauLayouts.get(i).addView(cardView);
                }
            }
        }
    }

    public void clear() {
        for (int i = 0; i < tableauLayouts.size(); i++) {
            tableauLayouts.get(i).removeAllViews();
        }

        for (int i = 0; i < foundations.size(); i++) {
            foundations.get(i).removeAllViews();
        }
        deckFrame.removeAllViews();
        deckTopFrame.removeAllViews();
    }

    public void updateFoundations() {
        Foundation f;

        for (int i = 0; i < game.foundations.size(); i++) {
            f = game.foundations.get(i);
            if (f.size() > 0) {
                Card c = f.viewTopCard();
                foundations.get(i).addView(new CardImageView(this, c.getCardId(), c.isFaceUp, i, 0, SolitaireGame.Location.FOUNDATION));
            } else {
                foundations.get(i).addView(new CardImageView(this, -1, true, i, -1, SolitaireGame.Location.FOUNDATION));
            }
        }
    }

    public static void log(String id, String msg) {
        Log.i(id, msg);
        //Log.i("2nd card: ", "" + destinationCard.cardId);
    }
}
