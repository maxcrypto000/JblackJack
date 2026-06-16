package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Timer;

import model.ModelManager;
import model.RandomCardSelector;
import view.TableView;

/**
 * Action listener for the "Deal" (Carta / Hit) action in the game.
 * Handles dealing initial cards or dealing an additional card to the player.
 */
public class DealActionListener implements ActionListener {

	private TableView tv;
	private boolean dealt;

	private ModelManager mm = ModelManager.getInstance();

	/**
	 * Constructs a new DealActionListener.
	 *
	 * @param tv the main table view of the game
	 */
	public DealActionListener(TableView tv) {
		this.tv = tv;
	}

	/**
	 * Performs the deal logic. If initial cards haven't been dealt, it deals the
	 * starting hands.
	 * If they have, it acts as a "Hit" and deals one card to the player, handling
	 * busts
	 * and split hands.
	 *
	 * @param e the action event triggered by clicking the deal/hit button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (dealt) {
			tv.getUserPanel().enableSplit(false);
			int sum;
			if (mm.isSplit(0)) {
				mm.dealToPlayer(0, tv.getUserPanel().getSide());
				sum = mm.getSum(0, tv.getUserPanel().getSide());
				if (sum >= 21) {
					if (tv.getUserPanel().getSide() == 1) {
						tv.getUserPanel().setSide(2);
					} else {
						new StaiListener(tv).actionPerformed(e);
					}

				}

			} else {
				mm.dealToPlayer(0);
				sum = mm.getSum(0);
				if (sum > 21) {
					mm.bust(0);
					tv.getUserPanel().enableStai(false);
					tv.getUserPanel().enableDeal(false);
					tv.getUserPanel().enableDouble(false);
					tv.getUserPanel().enableSplit(false);

				} else if (sum == 21) {
					new StaiListener(tv).actionPerformed(e);
				}
			}

			// System.out.println("SOMMA delle carte " + sum + " , valore carte: " +
			// mm.getValueOfCardsOfPlayer(0));

		} else {

			for (int i = 0; i < 2; i++) {

				deal();

				mm.dealToDealer();
			}
			/**
			 * switch buttons
			 */
			tv.getUserPanel().switchDealForCarta();
			/**
			 * if both cards are the same enable the split button
			 */
			if (mm.getValueOfCardsOfPlayer(0).get(0) == mm.getValueOfCardsOfPlayer(0).get(1)) {
				tv.getUserPanel().enableSplit(true);
			}
			/**
			 * set dealt
			 */
			dealt = true;
			tv.getUserPanel().setInstruction("Scegli la tua mossa dai pulsanti di controllo");
			/**
			 * if blackJack call action performed of stai
			 */
			if (mm.getSum(0) == 21) {
				new StaiListener(tv).actionPerformed(e);
			}

		}
		if (mm.isEnded()) {
			tv.getUserPanel().setInstruction("Partita terminata! Clicca su 'Play Again' per rigiocare");
		}
	}

	/**
	 * Deals the initial round of cards to all players and the dealer.
	 */
	private void deal() {
		/**
		 * add to Deal cards to model
		 */
		for (int i = 0; i < mm.getnPlayers(); i++) {
			int newCard = mm.dealToPlayer(i);
			System.out.println(newCard);
			// try {
			// Thread.sleep(1000);
			// } catch (InterruptedException e) {
			// System.out.print("sleep error in deal, dealActionManager");
			//
			// }

		}

	}

	/**
	 * Resets the dealt flag, allowing for a new round of initial dealing.
	 */
	public void resetDealt() {
		dealt = false;
	}

}
