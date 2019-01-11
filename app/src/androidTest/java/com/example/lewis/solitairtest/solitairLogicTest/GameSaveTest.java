package com.example.lewis.solitairtest.solitairLogicTest;

import android.support.test.runner.AndroidJUnit4;

import com.example.lewis.solitairtest.solitairLogic.Card;
import com.example.lewis.solitairtest.solitairLogic.Deck;
import com.example.lewis.solitairtest.solitairLogic.Foundation;
import com.example.lewis.solitairtest.solitairLogic.GameState;
import com.example.lewis.solitairtest.solitairLogic.SolitaireGame;
import com.example.lewis.solitairtest.solitairLogic.Tableau;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GameSaveTest {
    @Test
    public void testDefaultGameSave(){
        SolitaireGame game1 = new SolitaireGame();
        SolitaireGame game2 = new SolitaireGame();
        GameState gState;

        game1.resetBoard();
        gState = new GameState(game1);
        String save = gState.createSave();

        gState = new GameState(game2);
        gState.restoreSave(save);
        for(int i = 0; i < game1.tableaus.size(); i++){
            Tableau t1 = game1.tableaus.get(i);
            Tableau t2 = game2.tableaus.get(i);
            for(int j = 0; j < t1.size(); j++){
                assertTrue(t1.cardAt(j).getCardId() == t2.cardAt(j).getCardId());
                assertTrue(t1.cardAt(j).getSuit() == t2.cardAt(j).getSuit());
            }
        }

        for(int i = 0; i < game1.foundations.size(); i++){
            Foundation t1 = game1.foundations.get(i);
            Foundation t2 = game2.foundations.get(i);
            for(int j = 0; j < t1.size(); j++){
                assertTrue(t1.cardAt(j).getCardId() == t2.cardAt(j).getCardId());
                assertTrue(t1.cardAt(j).getSuit() == t2.cardAt(j).getSuit());
            }
        }

        Deck s1 = game1.stock;
        Deck s2 = game2.stock;
        for(int i = 0; i < game1.stock.size(); i++){
            assertTrue(s1.cardAt(i).getCardId() == s2.cardAt(i).getCardId());
            assertTrue(s1.cardAt(i).getSuit() == s2.cardAt(i).getSuit());
        }

        Deck w1 = game1.wastePile;
        Deck w2 = game2.wastePile;
        for(int i = 0; i < game1.wastePile.size(); i++){
            assertTrue(w1.cardAt(i).getCardId() == w2.cardAt(i).getCardId());
            assertTrue(w1.cardAt(i).getSuit() == w2.cardAt(i).getSuit());
        }

    }
}
