package com.example.lewis.solitairtest.solitairLogic;

import java.util.ArrayList;

public class Foundation extends CardStack {

    public Foundation() {
        cards = new ArrayList<Card>();
    }

    @Override
    public boolean pushCheck(Card card) {
        boolean possible = false;

        if (cards.size() != 0) {                                        //Stack is not empty
            Card topcard = cards.get(cards.size() - 1);
            if (topcard.getSuit() == card.getSuit()) {                    // Cards must be the same suit
                if (topcard.getNumber() == card.getNumber() - 1) {        //Top card must be one less than card to be added
                    possible = true;
                }
            }
        } else {                                                        //Stack is empty, can add an ace only.
            if (card.getNumber() == Card.ACE) {
                possible = true;
            }
        }
        return possible;
    }

    @Override
    public boolean popCheck(int i) {
        return cards.size() > 0 && i == cards.size() - 1;
    }

}
