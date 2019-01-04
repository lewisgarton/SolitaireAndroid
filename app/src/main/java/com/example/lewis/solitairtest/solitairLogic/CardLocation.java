package com.example.lewis.solitairtest.solitairLogic;

public class CardLocation {
    public int cardId, col, row, stackType;

    public CardLocation(int cardId, int col, int row, int stackType) {
        this.cardId = cardId;
        this.col = col;
        this.row = row;
        this.stackType = stackType;
    }

    @Override
    public String toString(){
        return "CardInfo[CardId: " + cardId + ", stackType: " + stackType + " Col: " + col + " Row: " + row + " ]";
    }
}
