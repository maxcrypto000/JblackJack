package view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 * The initial view or menu of the Blackjack game.
 * Allows the user to enter their username and initial bankroll before starting.
 */
@SuppressWarnings("deprecation")
public class CounterView extends JFrame implements Observer{

	public static final String TITLE="BlackJack Menu";

	private JPanel controlPanel;
	private JButton submitButton;
	private JPanel loginPanel;
	private JSpinner capitaleSpinner;
	

	private JLabel bg, userLabel, capitaleLabel;
	private JTextField  usernameField;  
	static {
		 UIManager.put("Label.font", new Font("Cascadia Code", Font.PLAIN, 15));
		 UIManager.put("Label.foreground", new Color(130, 15, 7));
		 //UIManager.put("TextField.background", new Color(71, 113, 72));
		 UIManager.put("JSpinner.background", new Color(71, 113, 72));
		 UIManager.put("Button.font", new Font("Cascadia Code", Font.PLAIN, 15));
		 UIManager.put("Button.foreground", new Color(130, 15, 7));
		 UIManager.put("Button.background", Color.WHITE);
		 UIManager.put("Button.highlight", Color.WHITE);
		 UIManager.put("Button.select", Color.WHITE);
		 UIManager.put("Button.focus", Color.WHITE);
		 UIManager.put("Panel.background", new Color(71, 113, 72));
		 }
	/**
	 * Constructs the CounterView window and its UI components (labels, spinners, buttons).
	 */
	public CounterView()
	
	{	
		
		
		/**
		 * set up window
		 */
		super(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(new Rectangle(500,200,300,170));
		try {
			 setIconImage(ImageIO.read(new File("res\\icona.JPEG")));
			} catch (IOException e) { System.out.println("NOT FOUND");}
		setLocationRelativeTo(null);
		setResizable(false);
		
		/**
		 * create Background label and add it to window
		 */
		
		bg = new JLabel(new ImageIcon("res\\\\bgMenu.jpg"));
		bg.setSize(300, 170);
		bg.setLayout(new BorderLayout());
		add(bg);
		
		/**
		 *  create panels, make them transparent and add to bg Label
		 */
	
		controlPanel = new JPanel();
		loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		controlPanel.setOpaque(false);
		loginPanel.setOpaque(false);
		loginPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		//add(cp,BorderLayout.EAST);
		
		
		bg.add(loginPanel, BorderLayout.CENTER);
		bg.add(controlPanel,BorderLayout.SOUTH);
		
		
		/**
		 * create buttons 
		 */
		
		submitButton = new JButton("START");
		
		/**
		 * create JSpinner
		 */
		capitaleSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 5000, 100));
		
		capitaleSpinner.getEditor().setOpaque(false);
		((JSpinner.NumberEditor)capitaleSpinner.getEditor()).getTextField().setOpaque(false);
	
		/**
		 * create labels
		 */
		userLabel = new JLabel("Username: ");
		capitaleLabel = new JLabel("Initial Bankroll");
		
		/**
		 * create textField
		 */
		usernameField = new JTextField("enter username" ,20);
		/**
		 * add components to panels
		 */

		controlPanel.add(submitButton,BorderLayout.CENTER);
		loginPanel.add(userLabel);
		loginPanel.add(usernameField);
		loginPanel.add(capitaleLabel);
		loginPanel.add(capitaleSpinner);
		
	}
	
	/**
	 * Adds an ActionListener to the submit button.
	 *
	 * @param al the ActionListener to be added
	 */
	public void addSubmitButtonListener(ActionListener al)
	{
		submitButton.addActionListener(al);
	}
	
	
	/**
	 * Gets the username entered by the player.
	 *
	 * @return the username as a String
	 */
	public String getUsername() {
		return usernameField.getText().toString();
	}

	
	/**
	 * Gets the initial bankroll chosen by the player.
	 *
	 * @return the initial bankroll value
	 */
	public int getInitialBankroll() {
		
		return (int) capitaleSpinner.getValue();
	}
	/**
	 * Switches the active view from the menu to the main game table.
	 *
	 * @param tv the TableView to switch to
	 */
	public void changeView(TableView tv)  {
		this.setVisible(false);
		tv.setVisible(true);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		//non è bloccante
		
		
	}

}
