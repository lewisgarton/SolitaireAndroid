package com.example.lewis.solitairtest.solitairLogicTest;

import android.support.test.runner.AndroidJUnit4;

import com.example.lewis.solitairtest.solitairLogic.Card;
import com.example.lewis.solitairtest.solitairLogic.Tableau;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TableauTest {


    @Test
    public void falseTestPushCheck() {
        Tableau h = new Tableau();


        String[][] tests = {
                {Card.SPADES, "3", Card.SPADES, "2"},    // card1: suit, id, card2: suit, id
                {Card.SPADES, "3", Card.CLUBS, "2"},
                {Card.SPADES, "3", Card.HEARTS, "3"},
                {Card.SPADES, "1", Card.HEARTS, "13"},
                {Card.SPADES, "12", Card.HEARTS, "13"},
                {Card.DIAMONDS, "10", Card.SPADES, "11"},
                {Card.DIAMONDS, "10", Card.SPADES, "11"}
        };

        for (String[] test : tests) {
            h.pushCard(new Card(test[0], Integer.parseInt(test[1])));
            boolean result = h.pushCheck(new Card(test[2], Integer.parseInt(test[3])));
            assertFalse(result);
        }

    }


    @Test
    public void trueTestPushCheck() {        // Check the push check method works according to the rules of solitaire
        Tableau h = new Tableau();

        String[][] tests = {
                {Card.SPADES, "3", Card.HEARTS, "2"},        // card1: suit, id, card2: suit, id
                {Card.SPADES, "3", Card.DIAMONDS, "2"},
                {Card.SPADES, "3", Card.HEARTS, "2"},
                {Card.SPADES, "2", Card.HEARTS, "1"},
                {Card.SPADES, "13", Card.HEARTS, "12"},
                {Card.DIAMONDS, "11", Card.SPADES, "10"},
                {Card.DIAMONDS, "10", Card.SPADES, "9"}
        };

        for (String[] test : tests) {
            h.pushCard(new Card(test[0], Integer.parseInt(test[1])));
            assertTrue(h.pushCheck(new Card(test[2], Integer.parseInt(test[3]))));
        }
    }

    @Test
    public void trueTestEmptyStackPushCheck() {
        String[][] tests = {
                {Card.HEARTS, "13"},    //Suit, id
                {Card.SPADES, "13"},
                {Card.CLUBS, "13"},
                {Card.DIAMONDS, "13"}
        };


        for (String[] test : tests) {
            Tableau h = new Tableau();
            assertTrue(h.pushCheck(new Card(test[0], Integer.parseInt(test[1]))));
        }
    }

    @Test
    public void falseTestEmptyStackPushCheck() {
        String[][] tests = {
                {Card.HEARTS, "12"},    //Suit, id
                {Card.SPADES, "1"},
                {Card.CLUBS, "11"},
                {Card.DIAMONDS, "10"}
        };


        for (String[] test : tests) {
            Tableau h = new Tableau();
            assertFalse(h.pushCheck(new Card(test[0], Integer.parseInt(test[1]))));
        }
    }

}