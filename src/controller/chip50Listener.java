package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;

/**
 * Action listener for the 50 chip button.
 * Handles the betting logic when the user selects a 50 value chip.
 */
public class chip50Listener implements ActionListener{
	
	private TableView tv;
	
	/**
	 * Constructs a new chip50Listener.
	 *
	 * @param tv the main table view of the game
	 */
	public  chip50Listener(TableView tv) {
		this.tv = tv;
	}

	/**
	 * Places a bet of 50 for the player and enables the deal button.
	 *
	 * @param e the action event triggered by clicking the button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ModelManager.getInstance().punta(0, 50);
		tv.getUserPanel().enableDeal(true);
		
	}
	
}
