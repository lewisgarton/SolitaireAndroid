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
    public ArrayList<ImageView> cardViews = new ArrayList<ImageView>();
    public FrameLayout deckFrame, deckTopFrame;
    //public ArrayList<FrameLayout> foundationFrames = new ArrayList<FrameLayout>();
    //public ArrayList<LinearLayout> tableauLayouts = new ArrayList<LinearLayout>();
    public SolitaireGame game;
    public CardInfo selectedCard, destinationCard;
    public boolean stateChanged;

    public ArrayList<FrameLayout> foundationFrames;
    public ArrayList<LinearLayout> tableauLayouts;



    public int deviceWidth;
    public static int deviceHeight;
    public int colSize;
    public int rowSize;
    public static int cardHeight;
    public CardInfo cardInfo;

    /**
     * Finds views and populates view lists "foundationFrames" and "tableauLayouts".
     */
    protected void getLayouts(){
        foundationFrames = new ArrayList<>();
        deckFrame = ((FrameLayout) findViewById(R.id.frame_deck));
        deckTopFrame = ((FrameLayout) findViewById(R.id.frame_top_card));
        foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation_3));
        foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation_4));
        foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation_5));
        foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation_6));

        tableauLayouts = new ArrayList<>();
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_0));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_1));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_2));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_3));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_4));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_5));
        tableauLayouts.add((LinearLayout) findViewById(R.id.layout_tableau_6));
    }

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


        setContentView(R.layout.activity_main);
        game = new SolitaireGame();

        if(savedInstanceState != null){
            GameState gState = new GameState(game);
            gState.restoreSave(savedInstanceState.getString("SAVE"));
        } else {
            game.resetBoard();
        }



        stateChanged = true;
        selectedCard = null;
        destinationCard = null;
        getLayouts();
        update();

        /*
        LinearLayout top_layout = (LinearLayout) findViewById(R.id.layout_foundationFrames);
        LinearLayout bottom_layout = (LinearLayout) findViewById(R.id.layout_tableaus);


        for (int i = 0; i < 7; i++) {
            // Set up to row layouts
            FrameLayout l = new FrameLayout(this);
            l.setLayoutParams(new LinearLayout.LayoutParams(colSize, ViewGroup.LayoutParams.MATCH_PARENT));
            if (i == 0) deckFrame = l;
            if (i == 1) deckTopFrame = l;
            top_layout.addView(l);

            // Set up bottom layouts
            LinearLayout k = new LinearLayout(this);
            k.setLayoutParams(new LinearLayout.LayoutParams(colSize, ViewGroup.LayoutParams.MATCH_PARENT));
            k.setOrientation(LinearLayout.VERTICAL);
            bottom_layout.addView(k);
            tableauLayouts.add(k);
        }



        //foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation1));
        //foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation2));
        //foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation3));
        //foundationFrames.add((FrameLayout) findViewById(R.id.frame_foundation4));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau1));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau2));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau3));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau4));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau5));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau6));
        //tableauLayouts.add((LinearLayout) findViewById(R.id.layout_Tableau7));
        //deckFrame = foundationFrames.get(0);
        //deckTopFrame = foundationFrames.get(1);

        */

    }


    /**
     * Update communicates with the game logic when the user selects a card or drags card(s) onto
     * another card, update then calls drawTableus and drawfoundationFrames as necessary.     *
     */
    public void update() {
        if (selectedCard != null && destinationCard == null) {
            game.cardClicked(selectedCard);
            selectedCard = null;
        } else if (selectedCard != null && destinationCard != null) {
            game.cardClicked(selectedCard, destinationCard);
            selectedCard = null;
            destinationCard = null;
        }
        clear();
        drawTableus();
        drawfoundationFrames();
    }


    /**
     * drawTableus retrieves the content's of each tableau from the game, creates and adds
     * views representing each card.
     */
    public void drawTableus() {
        // Create the card placement frames

        PlayingCardView cardView = null;
        boolean top = false;

        if (game.stock.size() > 0) {
            Card c = game.stock.viewTop();
            cardInfo = new CardInfo(c.getCardId(), false, 0, 0, SolitaireGame.Location.STOCK);
            deckFrame.addView(new PlayingCardView(this, cardInfo, true));
        } else {
            cardInfo = new CardInfo(-1, true, 0, 0, SolitaireGame.Location.STOCK);
            deckFrame.addView(new PlayingCardView(this, cardInfo, true));
        }

        if (game.wastePile.size() > 0) {
            Card c = game.wastePile.viewTop();
            cardInfo = new CardInfo(c.getCardId(), c.isFaceUp, 0, 0, SolitaireGame.Location.WASTEPILE);
            deckTopFrame.addView(new PlayingCardView(this, cardInfo, true));
        }

        PlayingCardView currentCardView, prevCardView = null;

        Tableau t;
        for (int i = 0; i < game.tableaus.size(); i++) {
            t = game.tableaus.get(i);
            if (t.size() == 0) {
                cardInfo = new CardInfo(-1, true, i, -1, SolitaireGame.Location.TABLEAU);
                tableauLayouts.get(i).addView(new PlayingCardView(this, cardInfo, true));
            } else {
                top = false;
                for (int j = 0; j < t.size(); j++) {
                    Card c = t.cardAt(j);
                    cardInfo = new CardInfo(c.getCardId(), c.isFaceUp, i, j, SolitaireGame.Location.TABLEAU);

                    //flag the top card
                    if (j == t.size() - 1) top = true;

                    currentCardView = new PlayingCardView(this, cardInfo, top);
                    //If its the first card, add it to the tableau linear layout
                    //If its not then add the card to the previous cards layout
                    if (j == 0) tableauLayouts.get(i).addView(currentCardView);
                    else prevCardView.addView(currentCardView);

                    prevCardView = currentCardView;

                }
            }
        }
    }

    /**
     * clear removes all views from the layouts so they are ready to be redrawn.
     */
    public void clear() {
        for (int i = 0; i < tableauLayouts.size(); i++) {
            tableauLayouts.get(i).removeAllViews();
        }

        for (int i = 0; i < foundationFrames.size(); i++) {
            foundationFrames.get(i).removeAllViews();
        }
        deckFrame.removeAllViews();
        deckTopFrame.removeAllViews();
    }

    /**
     * drawFoundation retrieves the content's of each foundation from the game, creates and adds
     * views representing each card.
     */
    public void drawfoundationFrames() {
        Foundation f;

        for (int i = 0; i < game.foundations.size(); i++) {
            f = game.foundations.get(i);
            if (f.size() > 0) {
                Card c = f.viewTopCard();
                cardInfo = new CardInfo(c.getCardId(), c.isFaceUp, i, 0, SolitaireGame.Location.FOUNDATION);
                foundationFrames.get(i).addView(new PlayingCardView(this, cardInfo, true));
            } else {
                cardInfo = new CardInfo(-1, true, i, -1, SolitaireGame.Location.FOUNDATION);
                foundationFrames.get(i).addView(new PlayingCardView(this, cardInfo, true));
            }
        }
    }

    /**
     * log is a simple wrapper for debug logging, debug will be better implementer in the future.
     * @param id Type of message.
     * @param msg Message.
     */
    public static void log(String id, String msg) {
        Log.i(id, msg);
        //Log.i("2nd card: ", "" + destinationCard.cardId);
    }
}
