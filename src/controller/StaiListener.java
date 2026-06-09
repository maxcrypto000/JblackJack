package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelManager;
import view.TableView;

public class StaiListener implements ActionListener {
	
	private TableView tv;
	
	public  StaiListener(TableView tv) {
		this.tv = tv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(ModelManager.getInstance().isSplit(0)) {
			if(tv.getUserPanel().getSide() == 1) {
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
			
		}else {
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
		
		
	}
	
	

}
