package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("deprecation")
public class TableView extends JFrame implements Observer{
	
	private UserPanel userPanel;
	private DealerPanel dealerPanel;
	private CenterPanel centerPanel;
	private JLabel bg;
	public TableView()  {
		/**
		 * set up window
		 */
		super("BlackJack Game");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(50,50,800,600));
		try {
			 setIconImage(ImageIO.read(new File("res\\icona.JPEG")));
			} catch (IOException e) { System.out.println("NOT FOUND");}
		setLocationRelativeTo(null);

		this.setVisible(true);
	
		
		/**
		 *  create and add panels to window
		 */
		
		userPanel = new UserPanel();
		dealerPanel = new DealerPanel();
		centerPanel = new CenterPanel();
		bg = new JLabel(new ImageIcon("res\\blackJackTable.jpg"));
		bg.setSize(800, 600);
		bg.setLayout(new BorderLayout());
		
		bg.add(centerPanel, BorderLayout.CENTER);
		bg.add(dealerPanel, BorderLayout.NORTH);
		bg.add(userPanel , BorderLayout.SOUTH );
		add(bg);
		//setContentPane(bg);
	}
	
	
	
	public UserPanel getUserPanel() {
		return userPanel;
		
	}
	public DealerPanel getDealerPanel() {
		return dealerPanel;
		
	}
	
	public CenterPanel getCenterPanel() {
		return centerPanel;
		
	}
	
//	public void setWinner(int result) {
//		System.out.println("Declaring 1s.." + result);
//		switch (result) {
//		case 0 :
//			winnerLabel.setText("PUSH !");
//			System.out.println("PUSHHH");
//			break;
//		case 1 :
//			winnerLabel.setText("WINNER !");
//			System.out.println("WONN");
//			break;
//		case 2 :
//			winnerLabel.setText("LOST !");
//			System.out.println("LOST");
//			break;
//		}
//		
//	}
//	
//	public void  setBusted() {
//		System.out.print("BUSTTT");
//		winnerLabel.setText("BUST !");
//	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updating...");
		userPanel.repaint();
		dealerPanel.repaint();
		centerPanel.repaint();
		
	}
	
	public void reset() {
		userPanel.resetButtons();
		userPanel.setSide(1);
		userPanel.resetAnimation();
		centerPanel.reset();
		dealerPanel.setShowCard(false);
		dealerPanel.resetAnimation();
	}


	public void declareWinner(int result) {
		// TODO Auto-generated method stub
		
	}

}
