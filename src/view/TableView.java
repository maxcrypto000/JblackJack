package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.GameState;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The main game window (frame) that holds all the different panels
 * (UserPanel, DealerPanel, CenterPanel) and observes the model for updates.
 */
@SuppressWarnings("deprecation")
public class TableView extends JFrame implements Observer {
	
	private UserPanel userPanel;
	private DealerPanel dealerPanel;
	private CenterPanel centerPanel;
	private JLabel bg;
	private boolean previousEnded = false;
	private boolean previousBust = false;
	/**
	 * Constructs the TableView, setting up the main game UI, assembling its sub-panels,
	 * and initializing the window parameters.
	 */
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
		this.setResizable(false);
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
	
	
	
	/**
	 * Gets the UserPanel.
	 *
	 * @return the user panel
	 */
	public UserPanel getUserPanel() {
		return userPanel;
		
	}
	/**
	 * Gets the DealerPanel.
	 *
	 * @return the dealer panel
	 */
	public DealerPanel getDealerPanel() {
		return dealerPanel;
		
	}
	
	/**
	 * Gets the CenterPanel.
	 *
	 * @return the center panel
	 */
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
		if (arg instanceof GameState) {
			GameState state = (GameState) arg;
			
			if (state.bust && !previousBust) {
				controller.AudioManager.getInstance().play("res\\lose.wav");
			}
			
			if (state.isEnded && !previousEnded && !state.bust) {
				if (state.result == 1 || state.result == 4 || state.resultSplit1 == 1 || state.resultSplit1 == 4) {
					controller.AudioManager.getInstance().play("res\\win.wav");
				} else if (state.result == 2 || state.result == 3 || state.resultSplit1 == 2 || state.resultSplit1 == 3) {
					controller.AudioManager.getInstance().play("res\\lose.wav");
				}
			}
			
			previousBust = state.bust;
			previousEnded = state.isEnded;
			
			if (!state.playersEmpty) {
				userPanel.updateModelData(
					state.userName,
					state.capitale,
					state.wins,
					state.cardsOfPlayer,
					state.puntataOfPlayer,
					state.sum,
					state.isSplit,
					state.cardsOfPlayerSplit1,
					state.cardsOfPlayerSplit2,
					state.puntataOfPlayerSplit1,
					state.puntataOfPlayerSplit2,
					state.sumSplit1,
					state.sumSplit2,
					state.isEnded,
					state.result,
					state.resultSplit1,
					state.resultSplit2
				);
				
				dealerPanel.updateModelData(
					state.cardsOfDealer,
					state.sumOfDealer
				);
				
				centerPanel.updateModelData(
					state.isEnded,
					state.bust
				);
			} else {
				userPanel.updateModelData(
					state.userName,
					0, 0, new java.util.ArrayList<>(), 0, 0, false, 
					new java.util.ArrayList<>(), new java.util.ArrayList<>(), 
					0, 0, 0, 0, false, -1, -1, -1
				);
				
				dealerPanel.updateModelData(
					new java.util.ArrayList<>(),
					0
				);
				
				centerPanel.updateModelData(
					false,
					false
				);
			}
			
			userPanel.repaint();
			dealerPanel.repaint();
			centerPanel.repaint();
		}
	}
	
	/**
	 * Resets the UI components and animations for a new game round.
	 */
	public void reset() {
		previousEnded = false;
		previousBust = false;
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
