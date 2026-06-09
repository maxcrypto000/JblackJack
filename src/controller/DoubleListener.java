package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;
import view.UserPanel;

public class DoubleListener implements ActionListener{
	private TableView tv;
	private ModelManager mm = ModelManager.getInstance();
	
	
	public DoubleListener(TableView tv) {
		this.tv = tv;
	}

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
