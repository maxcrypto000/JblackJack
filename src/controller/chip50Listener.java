package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;

public class chip50Listener implements ActionListener{
	
	private TableView tv;
	
	public  chip50Listener(TableView tv) {
		this.tv = tv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ModelManager.getInstance().punta(0, 50);
		tv.getUserPanel().enableDeal(true);
		
	}
	
}
