package model;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

	private ArrayList<ArrayList<Integer>> cardsLists = new ArrayList<ArrayList<Integer>>();
	
	private ArrayList<Integer> cards2 = new ArrayList<Integer>();
	private int capitale;
	private ArrayList<Integer> puntate = new ArrayList<Integer>();
	private boolean bust;
	private boolean split;
	
	
	public Player (int card1, int card2, int capitale){
		for(int i = 0; i < 3; i++) {
			cardsLists.add(new ArrayList<Integer>());
		}
		
		
		cardsLists.get(0).add(card1);
		cardsLists.get(0).add(card2);
		this.capitale = capitale;
		puntate.add(0);
	}
	
	public void addCard(int card) {
		
		if(cardsLists.get(0).get(0) == 0) {
			cardsLists.get(0).remove(0);
			cardsLists.get(0).add(0, card);
		}else if(cardsLists.get(0).get(1) == 0) {
			cardsLists.get(0).remove(1);
			cardsLists.get(0).add(1, card);
		}else {
			cardsLists.get(0).add(card);
		}
		
	}
	public void addCardToSide(int card, int side) {
		cardsLists.get(side).add(card);
	}
	
	public int getSum() {
		return getSum(0);
	}
	public int getSum(int cardsIndex) {
		int sum1 = this.getValueOfCards(cardsIndex).stream().mapToInt(Integer::intValue).sum();
		ArrayList<Integer> values =  this.getValueOfCards(cardsIndex);
		if(values.contains(1)) {
			values.remove((Object)1);
			values.add(11);
		}
		int sum2 = values.stream().mapToInt(Integer::intValue).sum();
		if(sum2 < 22) {
			System.out.print("somma: " + sum2);
			return sum2;
		} else {
			System.out.print("somma: " + sum2);
			return sum1;
		}
	}
	
	public ArrayList<Integer> getValueOfCards(){
		return getValueOfCards(0);
	}
	
	
	public ArrayList<Integer> getValueOfCards(int cardsIndex) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		
		for(int i=0; i<cardsLists.get(cardsIndex).size(); i++) {
			int card = cardsLists.get(cardsIndex).get(i);
			if(card > 4 && card < 21) {
				result.add(10);
			}else if(card > 20 && card < 25	) {
				result.add(9);
			}else if(card > 24 && card < 29	) {
				result.add(8);
			}else if(card > 28 && card < 33	) {
				result.add(7);
			}else if(card > 32 && card < 37	) {
				result.add(6);
			}else if(card > 36 && card < 41	) {
				result.add(5);
			}else if(card > 40 && card < 45	) {
				result.add(4);
			}else if(card > 44 && card < 49	) {
				result.add(3);
			}else if(card > 48 && card < 53	) {
				result.add(2);
			}else if(card > 0 && card < 5	) {
				result.add(1);
			}
		}
		return result;
	}
	public ArrayList<Integer> getCards(int side) {
		return cardsLists.get(side);
		
		
	}


	/**
	 * @return the capitale
	 */
	public int getCapitale() {
		return capitale;
	}

	/**
	 * @param capitale the capitale to set
	 */
	public void setCapitale(int capitale) {
		this.capitale = capitale;
	}
	
	/**
	 * @return the puntata
	 */
	public Integer getPuntata() {
	
		return puntate.get(0);
	}

	/**
	 * @return the puntata
	 */
	public Integer getPuntata(int side) {
	
		return puntate.get(side);
	}

	/**
	 * reset puntata to 0
	 */
	public void resetPuntata() {
		puntate.clear();
		puntate.add(0);
	}
	
	public void resetCards() {
		for (int i = 0; i < cardsLists.size(); i ++) {
			cardsLists.get(i).clear();
		}
		
		cardsLists.get(0).add(0);
		cardsLists.get(0).add(0);
	}
	
	public void punta(int puntata) {
		puntate.set(0, puntate.get(0) + puntata);
		this.capitale -= puntata;
	}
	public void collectWin(int side) {
		this.capitale += puntate.get(side) * 2;
		puntate.set(side, 0);
	}
	public void collectWin() {
		collectWin(0);
	}
	public void collectBlackJack(int side) {
		int win = ((puntate.get(side) * 2) + puntate.get(side) / 2);
		this.capitale += win;
		puntate.set(side, 0);
	}
	public void push(int side) {
		this.capitale += puntate.get(side);
		puntate.set(side, 0);
	}
	public void push() {
		this.capitale += puntate.get(0);
		puntate.set(0, 0);
	}
	

	

	/**
	 * @return the nOfCards
	 */
	public int getnOfCards() {
		return cardsLists.get(0).size();
	}

	/**
	 * @return the bust
	 */
	public boolean isBust() {
		return bust;
	}

	/**
	 * @param bust the bust to set
	 */
	public void setBust(boolean bust) {
		this.bust = bust;
	}

	/**
	 * @return the split
	 */
	public boolean isSplit() {
		return split;
	}

	/**
	 * @param split the split to set
	 */
	public void setSplit(boolean split) {
		if(split) {
			cardsLists.get(1).add(cardsLists.get(0).get(0));
			cardsLists.get(2).add(cardsLists.get(0).get(1));
			Collections.addAll(puntate, getPuntata(),  getPuntata());
			capitale -= getPuntata();
		}
	
		this.split = split;
	}
}
