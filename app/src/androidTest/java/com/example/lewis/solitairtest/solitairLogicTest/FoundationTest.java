package com.example.lewis.solitairtest.solitairLogicTest;

import android.support.test.runner.AndroidJUnit4;

import com.example.lewis.solitairtest.solitairLogic.Card;
import com.example.lewis.solitairtest.solitairLogic.Foundation;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class FoundationTest {
/*
    @Test
    public void falseTestPushCheck() {
        Foundation h = new Foundation();


        String[][] tests = {
                {Card.Suits.SPADES, "2", Card.CLUBS, "3"},
                {Card.Suits.SPADES, "2", Card.HEARTS, "3"},
                {Card.Suits.SPADES, "2", Card.DIAMONDS, "3"},
                {Card.Suits.SPADES, "12", Card.HEARTS, "13"},
                {Card.Suits.SPADES, "2", Card.Suits.SPADES, "1"},
                {Card.Suits.SPADES, "1", Card.Suits.SPADES, "13"}
        };

        for (String[] test : tests) {
            h.pushCard(new Card(test[0], Integer.parseInt(test[1])));
            boolean result = h.pushCheck(new Card(test[2], Integer.parseInt(test[3])));
            assertFalse(result);
        }

    }


    @Test
    public void trueTestPushCheck() {        // Check the push check method works according to the rules of solitaire
        Foundation h = new Foundation();

        String[][] tests = {
                {Card.Suits.SPADES, "1", Card.Suits.SPADES, "2"},        // card1: suit, id, card2: suit, id
                {Card.Suits.SPADES, "2", Card.Suits.SPADES, "3"},
                {Card.Suits.SPADES, "10", Card.Suits.SPADES, "11"},
                {Card.Suits.SPADES, "12", Card.Suits.SPADES, "13"}
        };

        for (String[] test : tests) {
            h.pushCard(new Card(test[0], Integer.parseInt(test[1])));
            assertTrue(h.pushCheck(new Card(test[2], Integer.parseInt(test[3]))));
        }
    }

    @Test
    public void trueTestEmptyStackPushCheck() {
        String[][] tests = {
                {Card.HEARTS, "1"},    //Suit, id
                {Card.Suits.SPADES, "1"},
                {Card.CLUBS, "1"},
                {Card.DIAMONDS, "1"}
        };


        for (String[] test : tests) {
            Foundation h = new Foundation();
            assertTrue(h.pushCheck(new Card(test[0], Integer.parseInt(test[1]))));
        }
    }

    @Test
    public void falseTestEmptyStackPushCheck() {
        String[][] tests = {
                {Card.HEARTS, "2"},    //Suit, id
                {Card.Suits.SPADES, "13"},
                {Card.CLUBS, "12"},
                {Card.DIAMONDS, "5"}
        };


        for (String[] test : tests) {
            Foundation h = new Foundation();
            assertFalse(h.pushCheck(new Card(test[0], Integer.parseInt(test[1]))));
        }
    }
*/

}