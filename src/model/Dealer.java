package model;

import java.util.ArrayList;

public class Dealer extends Player{

	public Dealer(int card1, int card2, int capitale) {
		super(card1, card2, capitale);
		// TODO Auto-generated constructor stub
	}
	
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
	public boolean hasBlackJack() {
		return (this.getSum() == 21 && this.getCards(0).size() == 2);
	}
	
	
	
}
