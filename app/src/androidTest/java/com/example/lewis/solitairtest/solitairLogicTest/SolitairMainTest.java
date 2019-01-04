package com.example.lewis.solitairtest.solitairLogicTest;

import android.support.test.runner.AndroidJUnit4;

import com.example.lewis.solitairtest.solitairLogic.SolitaireGame;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class SolitairMainTest {
    @Test
    public void TableauSizeTest() {
        SolitaireGame game = new SolitaireGame();
        game.resetBoard();
        assertEquals(1, game.tableaus.get(0).size());
        assertEquals(1, game.tableaus.get(1).size());
        assertEquals(3, game.tableaus.get(2).size());
        assertEquals(4, game.tableaus.get(3).size());
        assertEquals(5, game.tableaus.get(4).size());
        assertEquals(6, game.tableaus.get(5).size());
        assertEquals(7, game.tableaus.get(6).size());

        //Deck size should also be 24 (one card is drawn)
        assertEquals(24, game.stock.cardsRemaining());

    }

    @Test
    public void testMove() {
        SolitaireGame game = new SolitaireGame();
        game.resetBoard();
        game.move(412, 113);
        //game.tableaus(4)
        assertEquals(412, game.tableaus.get(6).popCard().getCardId());
    }
}
