package com.example.lewis.solitairtest.solitairLogic;

import java.util.ArrayList;

/**
 * GameSave is a bare bones object that stores only the vital
 * information for reconstructing a game as lists of strings.
 */
public class GameSave {
    ArrayList<ArrayList<String>> tableaus = new ArrayList<>();
    ArrayList<ArrayList<String>> foundations = new ArrayList<>();
    ArrayList<String> stock = new ArrayList<>();
    ArrayList<String> waste = new ArrayList<>();
}
