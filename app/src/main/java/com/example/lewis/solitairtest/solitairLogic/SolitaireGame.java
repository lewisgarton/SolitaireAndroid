package com.example.lewis.solitairtest.solitairLogic;

import com.example.lewis.solitairtest.MainActivity;

import java.util.ArrayList;

/**
 * SolitaireGame is the main 'driver' of the solitaire game.
 * It sets up a 'board' for the game to be played, deals and movers the cards in response to the
 * users input from the GUI.
 * Information from the gui representing cards must be in the form of a cardInfo.
 */
public class SolitaireGame {
    public final boolean DEBUG_MODE = true;
    public enum Location {
        TABLEAU, FOUNDATION, STOCK, WASTEPILE
    }

    private final int NUM_TABLEAUS = 7;
    private final int NUM_FOUNDATIONS = 4;

    public Deck stock = new Deck();
    public Deck wastePile = new Deck();
    public ArrayList<Foundation> foundations = new ArrayList<>();
    public ArrayList<Tableau> tableaus = new ArrayList<>();

    public boolean firstCardSelected = false;
    public CardInfo firstCard = null;
    public CardInfo secondCard = null;


    /**
     * resetBoard creates a new game, this involves creating a new deck and dealing the
     * cards on the tableau's.
     * This method is called on creation and also when the game is restarted.
     */
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

    /**
     * cardClicked is a temporary wrapper overload for the single card cardClicked method.
     * When a user drags a card onto another the single cardClicked is called twice with each
     * card.
     * @param cardA Card(s) being moved
     * @param cardB Card receiving cardA
     */
    public void cardClicked(CardInfo cardA, CardInfo cardB){
        firstCard = null;
        cardClicked(cardA);
        cardClicked(cardB);
    }

    /**
     * cardClicked interprets card clicks from the user to game actions such as clicking a face down
     * card, moving a card or cards to other positions on the board.
     * @param cardInfo
     */
    public void cardClicked(CardInfo cardInfo) {
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


    /**
     * flips the cardinfo boolean that indicates if the the card is face up.
     * @param cardInfo
     */
    public void flip(CardInfo cardInfo){
        tableaus.get(cardInfo.col).cardAt(cardInfo.row).isFaceUp = true;
    }

    /**
     * log is a wrapper for debugging with log
     * @param id
     * @param msg
     */
    public void log(String id, String msg){
        //MainActivity.log(id, msg);
    }

    public boolean isComplete(){
        boolean result = false;

        for(Foundation f : foundations) {
            if (f.size() ==  13) result = true;
        }

        return result;

    }

    /**
     * move moves cards on the table, it will call isLegalMove with the corresponding elements
     * of the table to ensure the removal and placement of the card(s) is legal then move the card(s).
     * firstCard should already be set if this method has been called.
     * @param cardBInfo destination cardInfo for firstCard
     */
    public void move(CardInfo cardBInfo) {
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

    /**
     * isLegalMove will ensure the proposed cad movement is a game legal action.
     * firstCard must already be set if this method is being called.
     * @param cardBInfo destination card info
     * @return true if the move is legal, false otherwise.
     */
    public boolean isLegalMove(CardInfo cardBInfo){
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

    /**
     * removePossible will ensure the proposed card to be moved is a game legal move.
     * @param cardInfo info of the card to be removed.
     * @return true if remove is possible, false otherwise.
     */
    public boolean removePossible(CardInfo cardInfo){
        boolean possible = false;
        // Removing card from the top of the wastepile
        if(cardInfo.location == Location.WASTEPILE && wastePile.viewTop() != null) {
            possible = true;
        // If stack is not empty
        }else if(cardInfo.row >= 0) {
            // Check remove from tableau
            if (cardInfo.location == Location.TABLEAU) {
                possible = tableaus.get(cardInfo.col).popCheck(cardInfo.row);
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
