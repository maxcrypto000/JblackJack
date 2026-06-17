package model;

import java.util.ArrayList;

/**
 * Data Transfer Object representing the complete state of the game.
 * Used by the ModelManager to pass the state to the views via the Observer pattern.
 */
public class GameState {
    
    public final String userName;
    public final int capitale;
    public final int wins;
    public final ArrayList<Integer> cardsOfPlayer;
    public final int puntataOfPlayer;
    public final int sum;
    
    public final boolean isSplit;
    public final ArrayList<Integer> cardsOfPlayerSplit1;
    public final ArrayList<Integer> cardsOfPlayerSplit2;
    public final int puntataOfPlayerSplit1;
    public final int puntataOfPlayerSplit2;
    public final int sumSplit1;
    public final int sumSplit2;
    
    public final boolean isEnded;
    public final int result;
    public final int resultSplit1;
    public final int resultSplit2;
    
    public final ArrayList<Integer> cardsOfDealer;
    public final int sumOfDealer;
    public final boolean bust;
    public final boolean playersEmpty;
    
    public GameState(
            String userName, int capitale, int wins, ArrayList<Integer> cardsOfPlayer,
            int puntataOfPlayer, int sum, boolean isSplit,
            ArrayList<Integer> cardsOfPlayerSplit1, ArrayList<Integer> cardsOfPlayerSplit2,
            int puntataOfPlayerSplit1, int puntataOfPlayerSplit2, int sumSplit1, int sumSplit2,
            boolean isEnded, int result, int resultSplit1, int resultSplit2,
            ArrayList<Integer> cardsOfDealer, int sumOfDealer, boolean bust, boolean playersEmpty) {
        
        this.userName = userName;
        this.capitale = capitale;
        this.wins = wins;
        this.cardsOfPlayer = cardsOfPlayer;
        this.puntataOfPlayer = puntataOfPlayer;
        this.sum = sum;
        this.isSplit = isSplit;
        this.cardsOfPlayerSplit1 = cardsOfPlayerSplit1;
        this.cardsOfPlayerSplit2 = cardsOfPlayerSplit2;
        this.puntataOfPlayerSplit1 = puntataOfPlayerSplit1;
        this.puntataOfPlayerSplit2 = puntataOfPlayerSplit2;
        this.sumSplit1 = sumSplit1;
        this.sumSplit2 = sumSplit2;
        this.isEnded = isEnded;
        this.result = result;
        this.resultSplit1 = resultSplit1;
        this.resultSplit2 = resultSplit2;
        this.cardsOfDealer = cardsOfDealer;
        this.sumOfDealer = sumOfDealer;
        this.bust = bust;
        this.playersEmpty = playersEmpty;
    }
}
