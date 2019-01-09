package com.example.lewis.solitairtest.solitairLogic;

public class CardLocation {
    public int cardId, col, row;
    SolitaireGame.Location location;
    public boolean faceUp;

    public CardLocation(int cardId, boolean faceUp, int col, int row, SolitaireGame.Location location) {
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
