package com.example.lewis.solitairtest.solitairLogic;

import java.util.ArrayList;

/**
 * Tableau is an extension of CardStack, it represents a tableau in the game and ensures
 * the game legal constraints are met.
 */
public class Tableau extends CardStack {

    /**
     * Constructor initialises inner cards list.
     */
    public Tableau() {
        cards = new ArrayList<Card>();
    }


    /**
     * pushCheck will check if a given card is allowed to be pushed onto the tableau subject to game
     * legal constraints.
     * @param card card to be pushed.
     * @return
     */
    @Override
    public boolean pushCheck(Card card) {
        boolean possible = false;

        if (cards.size() > 0) {                                                //stack is not empty
            Card topCard = cards.get(size() - 1);
            if (topCard.getColor() != card.getColor()) {                    //Cards are different color
                if (topCard.getNumber() == card.getNumber() + 1) {        //Top card is 1 less than the card being added
                    possible = true;
                }
            }
        } else if (card.getNumber() == Card.KING) {
            possible = true;                           //If stack is empty, king is possible
        }
        return possible;
    }

    /**
     * popCheck will check if a card at a given index on the tableau is allowed to be removed from
     * the tableau subject to game legal constraints.
     * @param i index of card to check removal
     * @return
     */
    @Override
    public boolean popCheck(int i) {
        boolean possible = false;
        if (i >= 0) {
            if (cards.get(i).isFaceUp){
                possible = true;
            }
        }
        return possible;
    }

    /**
     * popCard will remove and return a card from the tableu.
     * @param i index of card to be removed.
     * @return
     */
    public Card popCard(int i){
        return cards.remove(i);
    }

}
