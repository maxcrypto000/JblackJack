package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;

/**
 * Action listener for the 100 chip button.
 * Handles the betting logic when the user selects a 100 value chip.
 */
public class chip100Listener implements ActionListener{
	
	private TableView tv;
	
	/**
	 * Constructs a new chip100Listener.
	 *
	 * @param tv the main table view of the game
	 */
	public  chip100Listener(TableView tv) {
		this.tv = tv;
	}

	/**
	 * Places a bet of 100 for the player and enables the deal button.
	 *
	 * @param e the action event triggered by clicking the button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ModelManager.getInstance().punta(0, 100);
		tv.getUserPanel().setInstruction("Puntata inserita! Clicca su 'Deal' per iniziare la partita o aumenta puntata cliccando sulle chips");
		tv.getUserPanel().enableDeal(true);
	}
	
}
