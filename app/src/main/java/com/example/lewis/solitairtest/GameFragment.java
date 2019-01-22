package com.example.lewis.solitairtest;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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


public class GameFragment extends Fragment {
    public ArrayList<ImageView> cardViews = new ArrayList<ImageView>();
    public FrameLayout deckFrame, deckTopFrame;
    public SolitaireGame game;
    public CardInfo selectedCard, destinationCard;
    public boolean stateChanged;
    public ArrayList<FrameLayout> foundationFrames;
    public ArrayList<LinearLayout> tableauLayouts;
    public CardInfo cardInfo;
    public View v;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_game, container, false);
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
        return v;
    }


    /**
     * Finds views and populates view lists "foundationFrames" and "tableauLayouts".
     */
    protected void getLayouts(){
        foundationFrames = new ArrayList<>();
        deckFrame = ((FrameLayout) v.findViewById(R.id.frame_deck));
        deckTopFrame = ((FrameLayout) v.findViewById(R.id.frame_top_card));
        foundationFrames.add((FrameLayout) v.findViewById(R.id.frame_foundation_3));
        foundationFrames.add((FrameLayout) v.findViewById(R.id.frame_foundation_4));
        foundationFrames.add((FrameLayout) v.findViewById(R.id.frame_foundation_5));
        foundationFrames.add((FrameLayout) v.findViewById(R.id.frame_foundation_6));

        tableauLayouts = new ArrayList<>();
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_0));
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_1));
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_2));
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_3));
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_4));
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_5));
        tableauLayouts.add((LinearLayout) v.findViewById(R.id.layout_tableau_6));
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

    public void drawDeck() {

        if (game.stock.size() > 0) {
            Card c = game.stock.viewTop();
            cardInfo = new CardInfo(c.getCardId(), false, 0, 0, SolitaireGame.Location.STOCK);
            deckFrame.addView(new PlayingCardView(getContext(), cardInfo, true));
        } else {
            cardInfo = new CardInfo(-1, true, 0, 0, SolitaireGame.Location.STOCK);
            deckFrame.addView(new PlayingCardView(getContext(), cardInfo, true));
        }

        if (game.wastePile.size() > 0) {
            Card c = game.wastePile.viewTop();
            cardInfo = new CardInfo(c.getCardId(), c.isFaceUp, 0, 0, SolitaireGame.Location.WASTEPILE);
            deckTopFrame.addView(new PlayingCardView(getContext(), cardInfo, true));
        }
    }

    /**
     * drawTableus retrieves the content's of each tableau from the game, creates and adds
     * views representing each card.
     */
    public void drawTableus() {
        PlayingCardView currentCardView, prevCardView = null;

        Tableau t;
        for (int i = 0; i < game.tableaus.size(); i++) {
            t = game.tableaus.get(i);
            if (t.size() == 0) {
                cardInfo = new CardInfo(-1, true, i, -1, SolitaireGame.Location.TABLEAU);
                tableauLayouts.get(i).addView(new PlayingCardView(getContext(), cardInfo, true));
            } else {
                boolean top = false;
                for (int j = 0; j < t.size(); j++) {
                    Card c = t.cardAt(j);
                    cardInfo = new CardInfo(c.getCardId(), c.isFaceUp, i, j, SolitaireGame.Location.TABLEAU);

                    //flag the top card
                    if (j == t.size() - 1) top = true;

                    currentCardView = new PlayingCardView(getContext(), cardInfo, top);
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
                foundationFrames.get(i).addView(new PlayingCardView(getContext(), cardInfo, true));
            } else {
                cardInfo = new CardInfo(-1, true, i, -1, SolitaireGame.Location.FOUNDATION);
                foundationFrames.get(i).addView(new PlayingCardView(getContext(), cardInfo, true));
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
