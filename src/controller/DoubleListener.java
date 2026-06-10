package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;
import view.UserPanel;

/**
 * Action listener for the "Double Down" action in the game.
 * Handles the logic for doubling the bet and dealing exactly one more card.
 */
public class DoubleListener implements ActionListener{
	private TableView tv;
	private ModelManager mm = ModelManager.getInstance();
	
	/**
	 * Constructs a new DoubleListener.
	 *
	 * @param tv the main table view of the game
	 */
	public DoubleListener(TableView tv) {
		this.tv = tv;
	}

	/**
	 * Doubles the player's current bet, disables the split option, deals one final card,
	 * checks for a bust, and then automatically forces the player to stand.
	 *
	 * @param e the action event triggered by clicking the double button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mm.raddoppia(0);
		tv.getUserPanel().enableSplit(false);
		mm.dealToPlayer(0);
		int sum =  mm.getSum(0);
		System.out.println("Somma delle carte " + sum + " , valore carte: " + mm.getValueOfCardsOfPlayer(0));
		if(sum > 21) {
			mm.bust(0);
		}
		new StaiListener(tv).actionPerformed(e);
		}

}
