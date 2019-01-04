package com.example.lewis.solitairtest.solitairLogicTest;

import android.support.test.runner.AndroidJUnit4;

import com.example.lewis.solitairtest.solitairLogic.Card;
import com.example.lewis.solitairtest.solitairLogic.Deck;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class DeckTest {

    @Test
    public void testDeckSize() {
        Deck d = new Deck();
        int deckSize = 52;
        assertEquals(deckSize, d.size());
    }

    @Test
    public void testDeckComplete() {
        // Check to ensure the stock creates a correct set of cards.
        // very clunky!

        ArrayList<Integer> clubs = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
        @SuppressWarnings("unchecked")
        ArrayList<Integer> spades = (ArrayList<Integer>) clubs.clone();
        @SuppressWarnings("unchecked")
        ArrayList<Integer> hearts = (ArrayList<Integer>) clubs.clone();
        @SuppressWarnings("unchecked")
        ArrayList<Integer> diamonds = (ArrayList<Integer>) clubs.clone();

        int deckSize = 52;
        Deck d = new Deck();
        Card c;
        String suit;
        d.shuffle();

        for (int i = 0; i < deckSize; i++) {
            c = d.popTop();
            suit = c.getSuit();
            if (suit == Card.CLUBS) clubs.remove(new Integer(c.getNumber()));
            else if (suit.equals(Card.SPADES)) spades.remove(new Integer(c.getNumber()));
            else if (suit.equals(Card.DIAMONDS)) diamonds.remove(new Integer(c.getNumber()));
            else if (suit.equals(Card.HEARTS)) hearts.remove(new Integer(c.getNumber()));
        }

        assertEquals(0, spades.size());
        assertEquals(0, clubs.size());
        assertEquals(0, diamonds.size());
        assertEquals(0, hearts.size());
    }

}