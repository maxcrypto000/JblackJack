package controller;

import view.CounterView;
import view.TableView;
import model.ModelManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for the submit button in the setup menu.
 * Initializes the game session with the user's settings.
 */
public class SubmitActionListener implements ActionListener{
	
	private TableView tableView;
	private CounterView view;

	/**
	 * Constructs a new SubmitActionListener.
	 *
	 * @param view the menu view containing the login and setup form
	 * @param tableView the main table view to switch to once setup is complete
	 */
	SubmitActionListener(CounterView view, TableView tableView) {
		this.view = view;
		this.tableView = tableView;
	}

	/**
	 * Processes the user inputs (username, bankroll), sets up the model,
	 * and transitions from the menu view to the main game table view.
	 *
	 * @param e the action event triggered by clicking the submit button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ModelManager.getInstance().setLevel(1);
		ModelManager.getInstance().setnPlayers(1);
		ModelManager.getInstance().setUsername(view.getUsername());
		ModelManager.getInstance().initialSetup(view.getInitialBankroll());
		view.changeView(tableView);
		
		
		
	}
	/**
	 * Retrieves the main table view associated with this listener.
	 *
	 * @return the main TableView
	 */
	public TableView getTableView() {
		return tableView;
	}
	

}
