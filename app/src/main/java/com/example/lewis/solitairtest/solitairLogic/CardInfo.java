package com.example.lewis.solitairtest.solitairLogic;


/**
 * CardInfo is a simple class that contains all the required information about a card that the
 * GUI should need, this could do with rethinking in the future
 */
public class CardInfo {
    public int cardId, col, row;
    SolitaireGame.Location location;
    public boolean faceUp;

    public CardInfo(int cardId, boolean faceUp, int col, int row, SolitaireGame.Location location) {
        this.cardId = cardId;
        this.col = col;
        this.row = row;
        this.location = location;
        this.faceUp = faceUp;

    }

    @Override
    public String toString(){
        return "CardInfo[CardId: " + cardId + ", stackType: " + location + " Col: " + col + " Row: " + row + " ]";
    }
}
