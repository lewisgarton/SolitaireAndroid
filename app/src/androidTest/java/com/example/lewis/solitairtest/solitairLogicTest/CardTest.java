package com.example.lewis.solitairtest.solitairLogicTest;

import android.support.test.runner.AndroidJUnit4;

import com.example.lewis.solitairtest.solitairLogic.Card;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CardTest {

    @Test
    public void testCardColor() {
        // Ensure all cards are assigned the correct value
        assertEquals(new Card(Card.SPADES, Card.ACE).getColor(), Card.BLACK);
        assertEquals(new Card(Card.CLUBS, Card.ACE).getColor(), Card.BLACK);
        assertEquals(new Card(Card.HEARTS, Card.ACE).getColor(), Card.RED);
        assertEquals(new Card(Card.DIAMONDS, Card.ACE).getColor(), Card.RED);
    }

    @Test
    public void testCardIds() {
        Card c1 = new Card(Card.SPADES, Card.ACE);
        Card c2 = new Card(Card.SPADES, Card.ACE);

        assertEquals(1, c1.getCardId());
        assertEquals(2, c2.getCardId());
    }

}