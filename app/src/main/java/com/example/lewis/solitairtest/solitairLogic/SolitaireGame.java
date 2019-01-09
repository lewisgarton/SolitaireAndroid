package com.example.lewis.solitairtest.solitairLogic;

import android.util.Log;

import java.util.ArrayList;

public class SolitaireGame {
    public final boolean DEBUG_MODE = true;
    public enum Location {
        TABLEAU, FOUNDATION, STOCK, WASTEPILE
    }

    private final int NUM_TABLEAUS = 7;
    private final int NUM_FOUNDATIONS = 4;

    public Deck stock, wastePile;
    public ArrayList<Foundation> foundations;
    public ArrayList<Tableau> tableaus;

    public boolean firstCardSelected = false;
    public CardLocation firstCard = null;
    public CardLocation secondCard = null;

    public void resetBoard() {
        // Setup the decks
        stock = new Deck(Deck.DeckType.STANDARD_NO_JOKERS);
        //stock.shuffle();
        wastePile = new Deck();

        //setup the Foundations
        foundations = new ArrayList<>();
        for(int i = 0; i < NUM_FOUNDATIONS; i++) foundations.add(new Foundation());

        //setup the Tableau's
        tableaus = new ArrayList<>();
        for(int i = 0; i < NUM_TABLEAUS; i++){
            Tableau t = new Tableau();
            for(int j = 0; j <= i; j++){
                t.pushCard(stock.popTop());
                if(i < 2) break;    //first two only have one card.
            }
            t.viewTopCard().turnCard(); //turn the last card on the stack face up
            tableaus.add(t);
        }
    }

    public void cardClicked(CardLocation cardInfo) {
        int tabsize = 0;

        //Return if fist card selected is an empty slot
        if(firstCard == null && cardInfo.row == -1) return;

        // Stock clicked to draw next card
        if (cardInfo.location == Location.STOCK) {
            drawCardFromDeck();
            firstCard = null;
            if(DEBUG_MODE) log("CARD CLICK", "Card drawn from deck.");

        // Tableau face down card selected
        } else if(cardInfo.location == Location.TABLEAU && !cardInfo.faceUp) {
            // Flip the card if it is at the top of the tableau
            tabsize = tableaus.get(cardInfo.col).size();
            if (tabsize >= 0 && tabsize - 1 == cardInfo.row) {
                flip(cardInfo);
                firstCard = null;
                if(DEBUG_MODE) log("CARD CLICK", "Face down card turned over.");
            }

        // Selecting first card
        } else if (firstCard == null && removePossible(cardInfo)) {
            firstCard = cardInfo;
            if(DEBUG_MODE) log("CARD CLICK", "Card with id: " + cardInfo.cardId + " set as first card.");

        // Second card selected and move legal
        } else if (firstCard != null && isLegalMove(cardInfo)){
            if(DEBUG_MODE) log("CARD CLICK", "Moving card with id: " + firstCard.cardId + " on to card with id: " + cardInfo.cardId);
            move(cardInfo);
            if(DEBUG_MODE) log("CARD MOVED", "Move successful");
            firstCard = null;

        // Unknown action release card if selected
        } else {
           firstCard = null;
            if(DEBUG_MODE) log("CLICK INVALID", "First card released");
        }
    }


    public void flip(CardLocation cardInfo){
        tableaus.get(cardInfo.col).cardAt(cardInfo.row).isFaceUp = true;
    }

    public void log(String id, String msg){
        Log.d(id, msg);
    }

    public void move(CardLocation cardBInfo) {
        Card cardA = null;
        CardStack stack;

        // find the stack to push the cards
        if(cardBInfo.location == Location.TABLEAU){
            stack = tableaus.get(cardBInfo.col);
        }else{
            stack = foundations.get(cardBInfo.col);
        }

        // find the first card
        if(firstCard.location == Location.WASTEPILE){
            stack.pushCard(wastePile.popTop());
        }else if(firstCard.location == Location.TABLEAU){
            Tableau t = tableaus.get(firstCard.col);
            // Push all cards on top of first card to new position
            while(t.size()-1 >= firstCard.row){
                stack.pushCard(t.popCard(firstCard.row));
            }
        }else if(firstCard.location == Location.FOUNDATION){
            Foundation f = foundations.get(firstCard.col);
            stack.pushCard(f.popCard());
        }
    }

    public boolean isLegalMove(CardLocation cardBInfo){
        Card cardA = null;
        boolean isLegal = false;

        // Find the card on the board
        if(firstCard.location == Location.WASTEPILE) {
            cardA = wastePile.viewTop();
        }else if(firstCard.location == Location.TABLEAU) {
            cardA = tableaus.get(firstCard.col).cardAt(firstCard.row);
        }else if (firstCard.location == Location.FOUNDATION) {
            cardA = foundations.get(firstCard.col).viewTopCard();
        }else {
            return isLegal;
        }

        // Find the stack card is being moved to and test
        if(cardBInfo.location == Location.TABLEAU) {
            isLegal = tableaus.get(cardBInfo.col).pushCheck(cardA);
        }else if(cardBInfo.location == Location.FOUNDATION) {
            isLegal = foundations.get(cardBInfo.col).pushCheck(cardA);
        }
        return isLegal;
    }

    public boolean removePossible(CardLocation cardInfo){
        boolean possible = false;
        // Removing card from the top of the wastepile
        if(cardInfo.location == Location.WASTEPILE && wastePile.viewTop() != null) {
            possible = true;
        // If stack is not empty
        }else if(cardInfo.row >= 0) {
            // Check remove from tableau
            if (cardInfo.location == Location.TABLEAU) {
                possible = tableaus.get(cardInfo.col).isCardRemovable(cardInfo.row);
                // Check remove from foundation
            } else if (cardInfo.location == Location.FOUNDATION) {
                possible = true;
            }
        }
        return possible;
    }

    public void drawCardFromDeck(){
        //TODO: If deck empty need to swap them.
        if(stock.cardsRemaining() == 0){
            if(wastePile.size() == 0) return; // Waste pile and deck both empty.
            while (wastePile.cardsRemaining() != 0){
                Card c = wastePile.popTop();
                c.turnCard();
                stock.pushTop(c);
            }
        }
        Card c = stock.popTop();
        c.turnCard();
        wastePile.pushTop(c);
    }
}
