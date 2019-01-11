package com.example.lewis.solitairtest.solitairLogic;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    enum DeckType {STANDARD_NO_JOKERS}

    public Deck() {
        cards = new ArrayList<Card>();
    }

    public Deck(DeckType type) {
        cards = new ArrayList<Card>();
        switch (type) {
            case STANDARD_NO_JOKERS:
                for (Card.Suits suit : Card.Suits.values()) {
                    for (int i = 1; i < 14; i++) {
                        cards.add(new Card(suit, i));
                    }
                } break;
        }
    }

    public int size() {
        return cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card popTop() {
        return cards.remove(cards.size() - 1);
    }

    public void pushTop(Card card) {cards.add(card);}

    public void pushBottom(Card card) {
        cards.add(0, card);
    }

    public Card viewTop() {
        return cards.get(cards.size() - 1);
    }

    public Card cardAt(int i){
        return cards.get(i);
    }
    public int cardsRemaining() {
        return cards.size();
    }
}