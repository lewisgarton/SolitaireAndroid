package com.example.lewis.solitairtest.solitairLogic;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * GameState extracts a JSON save of the current game, or restores the game from another JSON string.
 */
public class GameState {
    public GameSave gSave;
    Gson gson;
    public SolitaireGame game;

    /**
     * Stores a copy of the current game for saving/restoring
     * @param game
     */
    public GameState(SolitaireGame game){
        this.game = game;
        gSave = new GameSave();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    /**
     * Restores the game to the state saved in the jsonSave String
     * @param jsonSave
     * @return
     */
    public boolean restoreSave(String jsonSave){
        // parse the json save back into an object of GameSave
        gSave = gson.fromJson(jsonSave, GameSave.class);

        // restore the game
        restoreTableaus();
        restoreFoundations();
        restoreStock();
        restoreWaste();
        return true;
    }

    /**
     * Creates a save from the current game and returns it as a JSON string
     * @return
     */
    public String createSave(){
        //Store minimal information
        storeTableaus();
        storeFoundations();
        storeStock();
        storeWaste();

        //Convert to JSON
        String jsonSave;
        jsonSave = gson.toJson(gSave);
        return jsonSave;
    }

    private void restoreTableaus(){
        ArrayList<Tableau> createdTableaus = new ArrayList<>();
        for(int i = 0; i < gSave.tableaus.size(); i++){
            ArrayList<String> gSaveTableau = gSave.tableaus.get(i);
            Tableau gameTableau = new Tableau();
            for(int j = 0; j < gSaveTableau.size(); j+=2){
                String cardId = gSaveTableau.get(j);
                String cardDirection = gSaveTableau.get(j+1);
                gameTableau.pushCard(new Card(cardId, cardDirection));
            }
            createdTableaus.add(gameTableau);
        }
        game.tableaus = createdTableaus;
    }

    private void restoreFoundations(){
        ArrayList<Foundation> createdFoundations = new ArrayList<>();
        for(int i = 0; i < gSave.foundations.size(); i++){
            ArrayList<String> gSaveFoundation = gSave.foundations.get(i);
            Foundation gameFoundation = new Foundation();
            for(int j = 0; j < gSaveFoundation.size(); j+=2){
                String cardId = gSaveFoundation.get(j);
                String cardDirection = gSaveFoundation.get(j+1);
                gameFoundation.pushCard(new Card(cardId, cardDirection));
            }
            createdFoundations.add(gameFoundation);
        }
        game.foundations = createdFoundations;
    }

    private void restoreStock(){
        Deck createdStock = new Deck();
        for(int i = 0; i < gSave.stock.size(); i+=2){
            String cardId = gSave.stock.get(i);
            String cardDirection = gSave.stock.get(i+1);
            createdStock.pushTop(new Card(cardId, cardDirection));
        }
        game.stock = createdStock;
    }

    private void restoreWaste(){
        Deck createdWaste = new Deck();
        for(int i = 0; i < gSave.waste.size(); i+=2){
            String cardId = gSave.waste.get(i);
            String cardDirection = gSave.waste.get(i+1);
            createdWaste.pushTop(new Card(cardId, cardDirection));
        }
        game.wastePile = createdWaste;
    }

    private void storeStock(){
        ArrayList<String> cardList = new ArrayList<>();
        for(int i = 0; i < game.stock.size(); i++){
            Card c = game.stock.cardAt(i);
            cardList.add(""+c.getCardId());
            cardList.add(String.valueOf(c.isFaceUp));
        }
        gSave.stock = cardList;
    }

    private void storeWaste(){
        ArrayList<String> cardList = new ArrayList<>();
        for(int i = 0; i < game.wastePile.size(); i++){
            Card c = game.wastePile.cardAt(i);
            cardList.add(""+c.getCardId());
            cardList.add(String.valueOf(c.isFaceUp));
        }
        gSave.waste = cardList;
    }

    private void storeTableaus(){
        ArrayList<String> cardList;
        for(int i = 0; i < game.tableaus.size(); i++){
            cardList = new ArrayList<>();
            Tableau t = game.tableaus.get(i);
            for(int j = 0; j < t.size(); j++){  //Store each cards id and face up value as a list of strings
                Card c = t.cardAt(j);
                cardList.add(""+c.getCardId());
                cardList.add(String.valueOf(c.isFaceUp));
            }
            gSave.tableaus.add(cardList);
        }
    }

    private void storeFoundations(){
        ArrayList<String> cardList;
        for(int i = 0; i < game.foundations.size(); i++){
            cardList = new ArrayList<>();
            Foundation f = game.foundations.get(i);
            for(int j = 0; j < f.size(); j++){  //Store each cards id and face up value as a list of strings
                Card c = f.cardAt(j);
                cardList.add(""+c.getCardId());
                cardList.add(String.valueOf(c.isFaceUp));
            }
            gSave.foundations.add(cardList);
        }
    }
}
