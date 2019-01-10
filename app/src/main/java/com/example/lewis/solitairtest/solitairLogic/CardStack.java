package com.example.lewis.solitairtest.solitairLogic;

import java.util.ArrayList;

public abstract class CardStack {
    protected ArrayList<Card> cards;

    public abstract boolean pushCheck(Card card);

    public abstract boolean popCheck(int i);

    public void pushCard(Card card) {
        cards.add(card);
    }

    public Card popCard() {
        return cards.remove(cards.size() - 1);
    }

    public Card viewTopCard() {
        return cards.get(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }

    public Card cardAt(int i) {
        return cards.get(i);
    }
}
