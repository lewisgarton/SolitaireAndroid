package com.example.lewis.solitairtest.solitairLogic;

import android.util.Log;

import java.util.ArrayList;

public class SolitaireGame {
    enum Location {
        TABLEAU, FOUNDATION, STOCK, WASTEPILE
    }

    private final int NUM_TABLEAUS = 7;
    private final int NUM_FOUNDATIONS = 4;

    public Deck stock, wastePile;
    public ArrayList<Foundation> foundations;
    public ArrayList<Tableau> tableaus;

    public static final int TABLEAU = 1000;
    public static final int FOUNDATION = 1001;
    public static final int DECK = 1002;
    public static final int EMPTY_CARD = 1003;
    public static final int TOP_DECK = 1004;
    public static final int FACE_DOWN = 1005;

    public boolean firstCardSelected = false;
    public CardLocation firstCard = null;
    public CardLocation secondCard = null;

    public void resetBoard() {
        // Setup the decks
        stock = new Deck(Deck.DeckType.STANDARD_NO_JOKERS);
        stock.shuffle();
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

    public void cardClicked(CardLocation cardInfo){
        if(cardInfo.stackType == DECK) {
            drawCardFromDeck();
            log("DECK CLICKED: ", "first card = " + firstCard);
            firstCard = null;
        }else if(firstCard == null && isFaceDown(cardInfo) && cardInfo.stackType == TABLEAU){
            flip(cardInfo);
        }else if(firstCard == null && removePossible(cardInfo)) {
            firstCard = cardInfo;
            log("Remove possible: ", "first card = " + firstCard);
        }else if(firstCard != null && isLegalMove(cardInfo)){
            Log.i("ACTION: ", "Attempting to move card ");
            log("Move possible: ", "first card = " + firstCard);
            log("Moving: ", "first card = " + firstCard + " To " + cardInfo);
            move(cardInfo);
            firstCard = null;
            log("Set first card to null: ", "first card = " + firstCard);
        }else{
           firstCard = null;
            log("Move not possible: ", "first card = " + firstCard);
        }


    }

    public boolean isFaceDown(CardLocation cardInfo) {
        boolean flag = false;
        CardStack stack = tableaus.get(cardInfo.col);
        if(stack.size()-1 == cardInfo.row && !stack.cardAt(cardInfo.row).isFaceUp){ //is the top card and face down
            flag = true;
        }
        return flag;
    }

    public void flip(CardLocation cardInfo){
        tableaus.get(cardInfo.col).cardAt(cardInfo.row).isFaceUp = true;
    }

    public void log(String id, String msg){
        Log.d(id, msg);
    }

    public void move(CardLocation cardBInfo) {
        Card cardA;
        CardStack stackA = findStack(firstCard.col, firstCard.stackType);
        CardStack stackB = findStack(cardBInfo.col, cardBInfo.stackType);
        if(firstCard.stackType == TOP_DECK){
            cardA = wastePile.popTop();
            stackB.pushCard(cardA);
        }else if(firstCard.stackType == TABLEAU){
            while(stackA.size()-1 >= firstCard.row){
                cardA = ((Tableau)stackA).popCard(firstCard.row);    // Left this seperate as will need to change this method later to allow for multiple cards to be moved
                stackB.pushCard(cardA);
            }
        }else{  //is foundation
            cardA = stackA.popCard();
            stackB.pushCard(cardA);
        }

    }

    public boolean isLegalMove(CardLocation cardBInfo){
        Card a = null;
        if(firstCard.stackType == TOP_DECK){
            a = wastePile.viewTop();
        }else if (firstCard.stackType == TABLEAU || firstCard.stackType == FOUNDATION){
            CardStack stackA = findStack(firstCard.col, firstCard.stackType);
            a = stackA.cardAt(firstCard.row);
        }

        CardStack stackB = findStack(cardBInfo.col, cardBInfo.stackType);

        if(a == null) return false;
        else return stackB.pushCheck(a);
    }

    public boolean removePossible(CardLocation cardInfo){
        boolean possible = false;
        int stackType = cardInfo.stackType;

        if(stackType == TOP_DECK && wastePile.viewTop() != null) possible = true;
        else if(stackType == TABLEAU || stackType == FOUNDATION){
            CardStack stack = findStack(cardInfo.col, cardInfo.stackType);
            if(stack.isCardRemovable(cardInfo.row)){
                possible = true;
            }
        }

        return possible;
    }

    public CardStack findStack(int col, int stackType){
        if(stackType == TABLEAU) return tableaus.get(col);
        else return foundations.get(col);
    }

    public void drawCardFromDeck(){
        //TODO: If deck empty need to swap them.
        if(stock.cardsRemaining() == 0){
            returnStock();
        }
        Card c = stock.popTop();
        c.turnCard();
        wastePile.pushTop(c);
    }

    public void returnStock(){
        while (wastePile.cardsRemaining() != 0){
            Card c = wastePile.popTop();
            c.turnCard();
            stock.pushTop(c);
        }
    }

    private void resetSelected(){
        firstCardSelected = false;
        firstCard = null;
        secondCard = null;
    }
}
