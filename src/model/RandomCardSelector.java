package model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * create a card selector using a Singleton design pattern
 */
public class RandomCardSelector {
	/**
	 * instance for singleton
	 */
	private static RandomCardSelector instance;
	/**
	 * Instantiate a SecureRand
	 */
	SecureRandom rand = new SecureRandom();
	/**
	 * number of cards to use, usually 52 + 1
	 */
	private static final int  NOFCARDS = 53; 
	/**
	 * generate the ArrayList of cards using IntStream
	 */
	private ArrayList<Integer> availableCards = new ArrayList<Integer>(IntStream.rangeClosed(1 , NOFCARDS)
		    .boxed().toList());
	
	/**
	 * @return instance of RandomCardSelector (Singleton)
	 */
	public static RandomCardSelector getInstance() {
		if (instance==null) instance=new RandomCardSelector();
		return instance;
		
	}
	
	/**
	 * selects a random number between 0 and the remaining cards size,
	 * then gets and removes the card corresponding to the index from AvailableCards
	 * @return the randomly selected card
	 */
	public int selectCard() {
		
		int randomNumber = rand.nextInt(availableCards.size());
		int card = availableCards.get(randomNumber);
		availableCards.remove(randomNumber);
		return card;
	}
	
	/**
	 * reset avaliableCards to NOFCARDS
	 */
	public void reset() {
		availableCards = new ArrayList<Integer>(IntStream.rangeClosed(1 , NOFCARDS)
			    .boxed().toList());
	}
}
