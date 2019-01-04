package com.example.lewis.solitairtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lewis.solitairtest.solitairLogic.CardImageView;
import com.example.lewis.solitairtest.solitairLogic.CardLocation;
import com.example.lewis.solitairtest.solitairLogic.Deck;
import com.example.lewis.solitairtest.solitairLogic.Tableau;
import com.example.lewis.solitairtest.solitairLogic.Foundation;
import com.example.lewis.solitairtest.solitairLogic.SolitaireGame;

import java.util.ArrayList;

public class MainActivity extends Activity {
    public ArrayList<ImageView> cardViews = new ArrayList<ImageView>();

    // Frames for card stacks
    public FrameLayout deckFrame, deckTopFrame;
    public ArrayList<FrameLayout> foundations = new ArrayList<FrameLayout>();
    public ArrayList<LinearLayout> tableauLayouts = new ArrayList<LinearLayout>();
    public ArrayList<ArrayList<CardView>> tablist = new ArrayList<ArrayList<CardView>>();
    public SolitaireGame game;

    public boolean cardSelected = false;
    public CardLocation selectedCard, destinationCard;
    public boolean stateChanged;

    // cards
    public CardView myCard;
    public Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new SolitaireGame();
        game.resetBoard();
        stateChanged = true;
        selectedCard = null;
        destinationCard = null;

        foundations.add((FrameLayout) findViewById(R.id.frame_foundation1));
        foundations.add((FrameLayout) findViewById(R.id.frame_foundation2));
        foundations.add((FrameLayout) findViewById(R.id.frame_foundation3));
        foundations.add((FrameLayout) findViewById(R.id.frame_foundation4));

        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau1));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau2));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau3));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau4));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau5));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau6));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau7));

        deckFrame = findViewById(R.id.frame_deck);
        deckTopFrame = findViewById(R.id.frame_deckTop);

        update();
    }

    public void update() {
        if(selectedCard != null){
            game.cardClicked(selectedCard);
            selectedCard = null;
        }




        updateTableus();
        updateFoundations();
        stateChanged = false;
        log();

    }

    public void updateTableus() {
        // Create the card placement frames
        clear();

        if(game.stock.size() > 0) {
            deckFrame.addView(new CardImageView(this, "DECK", SolitaireGame.FACE_DOWN, -1, -1, SolitaireGame.DECK));
        } else {
            deckFrame.addView(new CardImageView(this, "EMPTY", SolitaireGame.EMPTY_CARD, -1, -1, SolitaireGame.DECK));
        }

        if(game.wastePile.size() > 0) {
            deckTopFrame.addView(new CardImageView(this, game.wastePile.viewTop().toString(), game.wastePile.viewTop().getCardId(), 0, -1, SolitaireGame.TOP_DECK));
        }

        Tableau t;
        for (int i = 0; i < game.tableaus.size(); i++) {
            t = game.tableaus.get(i);
            if(t.size() == 0){
                tableauLayouts.get(i).addView(new CardImageView( this, "Empty", SolitaireGame.EMPTY_CARD, i, -1, SolitaireGame.TABLEAU));
            }else {
                for (int j = 0; j < t.size(); j++) {
                    tableauLayouts.get(i).addView(new CardImageView(this, t.cardAt(j).toString(), t.cardAt(j).getCardId(), i, j, SolitaireGame.TABLEAU));
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
                foundations.get(i).addView(new CardImageView(this, f.viewTopCard().toString(), f.viewTopCard().getCardId(), i, 0, SolitaireGame.FOUNDATION));
            } else {
                foundations.get(i).addView(new CardImageView(this, "Empty", SolitaireGame.EMPTY_CARD, i, -1, SolitaireGame.FOUNDATION));
            }
        }
    }


    public void log() {
        //Log.i("1st card: ", "" + selectedCard.cardId);
        //Log.i("2nd card: ", "" + destinationCard.cardId);
    }
}
