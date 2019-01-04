package com.example.lewis.solitairtest.solitairLogic;

public class Card {
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 1;

    public enum Suits {
        CLUBS, SPADES, HEARTS, DIAMONDS
    }
    public  enum CardColor {
        RED, BLACK
    }

    public boolean isFaceUp = false;

    private Suits suit;
    private int number;

    private int cardId;

    public void turnCard(){
        if(isFaceUp) isFaceUp = false;
        else isFaceUp = true;
    }

    public Card(Suits suit, int number) {
        //TODO check suit, throw exception
        this.suit = suit;
        //TODO check bounds of the number, throw exception
        this.number = number;
        genCardId();
    }

    public int getCardId() {
        //Todo clean this up
        if(!isFaceUp){
            return SolitaireGame.FACE_DOWN;
        }
        return cardId;
    }

    private void genCardId() {
        switch(suit) {
            case CLUBS: cardId = number + 100; break;
            case SPADES: cardId = number + 200; break;
            case DIAMONDS: cardId = number + 300; break;
            case HEARTS: cardId = number + 400; break;
        }
    }

    public boolean isColor(CardColor color) {
        boolean result = false;
        if(color == CardColor.RED && (suit == Suits.HEARTS || suit == Suits.DIAMONDS)){
            result = true;
        } else if(color == CardColor.RED && (suit == Suits.HEARTS || suit == Suits.DIAMONDS)){
            result = true;
        }
        return result;
    }

    public CardColor getColor() {
        if(suit == Suits.DIAMONDS || suit == Suits.HEARTS) return CardColor.RED;
        return CardColor.BLACK;
    }

    public Suits getSuit() {
        return suit;
    }

    public void setSuit(Suits suit) {
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

}