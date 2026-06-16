package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;

/**
 * Action listener for the "Stand" (Stai) action in the game.
 * Handles the logic when a player decides to keep their current hand and end their turn.
 */
public class StaiListener implements ActionListener {

	private TableView tv;

	/**
	 * Constructs a new StaiListener.
	 *
	 * @param tv the main table view of the game
	 */
	public StaiListener(TableView tv) {
		this.tv = tv;
	}

	/**
	 * Executes the stand logic. If the player split their hand, it switches to the second hand.
	 * Otherwise, it disables the player actions, triggers the dealer's turn,
	 * determines the winner, and updates the view to show the dealer's hidden card.
	 *
	 * @param e the action event triggered by clicking the stand button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ModelManager.getInstance().isSplit(0)) {
			if (tv.getUserPanel().getSide() == 1) {
				tv.getUserPanel().setSide(2);
				ModelManager.getInstance().updateView();
			} else {
				tv.getUserPanel().enableStai(false);
				tv.getUserPanel().enableDeal(false);
				tv.getUserPanel().enableDouble(false);
				tv.getUserPanel().enableSplit(false);
				ModelManager.getInstance().hitOrStand();
				tv.getDealerPanel().setShowCard(true);
			}

		} else {
			tv.getUserPanel().enableStai(false);
			tv.getUserPanel().enableDeal(false);
			tv.getUserPanel().enableDouble(false);
			tv.getUserPanel().enableSplit(false);
			ModelManager.getInstance().hitOrStand();
			System.out.println("Calling declare");
			int result = ModelManager.getInstance().getResult(0);
			tv.getCenterPanel().setWinner(result);
			tv.getDealerPanel().setShowCard(true);
		}

		if (ModelManager.getInstance().isEnded()) {
			tv.getUserPanel().setInstruction("Partita terminata! Clicca su 'Play Again' per rigiocare");
		}

	}

}
