package controller;

import model.ModelManager;
import view.CounterView;
import view.TableView;

import java.awt.event.ActionListener;



/**
 * The main entry point for the Blackjack application.
 * Initializes the model, creates the views, sets up the Observer pattern,
 * and binds all the action listeners to the UI components.
 */
public class Main {

	/**
	 * The main method to start the game.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		
		// crea il modello
		ModelManager mm=ModelManager.getInstance();
		// crea la view
		
		CounterView cv=new CounterView();
		cv.setVisible(true);
		TableView tv = new TableView();
		tv.setVisible(false);
		
		// innesca il meccanismo Observer-Observable
		mm.addObserver(cv);
		mm.addObserver(tv);
		//aggancia gli ActionListener
		ActionListener submitListener = new SubmitActionListener(cv, tv);
		cv.addSubmitButtonListener(submitListener);
		
		DealActionListener dealListener = new DealActionListener(tv);
		tv.getUserPanel().addDealButtonListener(dealListener);
		tv.getUserPanel().add50ButtonListener(new chip50Listener(tv));
		tv.getUserPanel().add100ButtonListener(new chip100Listener(tv));
		tv.getUserPanel().addStaiListener(new StaiListener(tv));
		tv.getUserPanel().addDoubleListener(new DoubleListener(tv));
		tv.getCenterPanel().addPlayAgainListener(e -> {mm.playAgain();
														tv.reset();
														dealListener.resetDealt();});
		tv.getUserPanel().addSplitListener(e -> {mm.split(0);
												tv.getUserPanel().enableSplit(false);
												tv.getUserPanel().enableDouble(false);});
		
		// gameloop
		
	}

}
