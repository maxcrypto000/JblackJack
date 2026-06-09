package controller;

import view.CounterView;
import view.TableView;
import model.ModelManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitActionListener implements ActionListener{
	
	private TableView tableView;
	private CounterView view;
	SubmitActionListener(CounterView view, TableView tableView) {
		this.view = view;
		this.tableView = tableView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ModelManager.getInstance().setLevel(1);
		ModelManager.getInstance().setnPlayers(1);
		ModelManager.getInstance().setUsername(view.getUsername());
		ModelManager.getInstance().initialSetup(view.getInitialBankroll());
		view.changeView(tableView);
		
		
		
	}
	public TableView getTableView() {
		return tableView;
	}
	

}
