package model;

import java.util.ArrayList;

/**
 * Represents the dealer in the Blackjack game.
 * Inherits from the Player class but includes specific rules for the dealer's behavior.
 */
public class Dealer extends Player{

	/**
	 * Constructs a new Dealer with initial cards and capital.
	 *
	 * @param card1 the value of the first card
	 * @param card2 the value of the second card
	 * @param capitale the initial bankroll of the dealer
	 */
	public Dealer(int card1, int card2, int capitale) {
		super(card1, card2, capitale);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Executes the dealer's logic for hitting or standing.
	 * The dealer must hit if their current sum is strictly less than 18.
	 *
	 * @return true if the dealer drew a new card, false if the dealer stands
	 */
	public boolean hitOrStand() {
		if (getSum() < 18) {
			addCard(RandomCardSelector.getInstance().selectCard());
			if(getSum() > 21) {
				setBust(true);
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks if the dealer has a Blackjack.
	 * A Blackjack is exactly a sum of 21 formed by the initial two cards.
	 *
	 * @return true if the dealer has a Blackjack, false otherwise
	 */
	public boolean hasBlackJack() {
		return (this.getSum() == 21 && this.getCards(0).size() == 2);
	}
	
	
	
}
