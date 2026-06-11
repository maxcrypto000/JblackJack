package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

/**
 * The initial view or menu of the Blackjack game.
 * Allows the user to enter their username and initial bankroll before starting.
 */
@SuppressWarnings("deprecation")
public class CounterView extends JFrame implements Observer{

	public static final String TITLE="BlackJack Menu";

	private JPanel mainPanel;
	private JPanel controlPanel;
	private JPanel loginPanel;
	private JButton submitButton;
	private JSpinner capitaleSpinner;
	
	private JLabel titleLabel, userLabel, capitaleLabel;
	private JTextField usernameField;  

	static {
		// Clean and professional UI settings
		UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD, 14));
		UIManager.put("Label.foreground", new Color(50, 50, 50));
		UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.background", new Color(34, 139, 34)); // Forest Green
		UIManager.put("Button.focus", new Color(34, 139, 34));
		UIManager.put("Panel.background", new Color(245, 245, 245)); // Light gray
	}

	/**
	 * Constructs the CounterView window and its UI components (labels, spinners, buttons).
	 */
	public CounterView()
	{	
		super(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300); 
		
		try {
			setIconImage(ImageIO.read(new File("res\\icona.JPEG")));
		} catch (IOException e) { System.out.println("NOT FOUND");}
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		mainPanel.setBackground(new Color(245, 245, 245));
		
		// Title
		titleLabel = new JLabel("Welcome to BlackJack", JLabel.CENTER);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titleLabel.setForeground(new Color(34, 139, 34));
		mainPanel.add(titleLabel, BorderLayout.NORTH);
		
		// Login Panel using GridBagLayout for alignment
		loginPanel = new JPanel(new GridBagLayout());
		loginPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		userLabel = new JLabel("Username:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		loginPanel.add(userLabel, gbc);
		
		usernameField = new JTextField("Player1");
		usernameField.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.7;
		loginPanel.add(usernameField, gbc);
		
		capitaleLabel = new JLabel("Initial Bankroll:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.3;
		loginPanel.add(capitaleLabel, gbc);
		
		capitaleSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 5000, 100));
		capitaleSpinner.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.7;
		loginPanel.add(capitaleSpinner, gbc);
		
		mainPanel.add(loginPanel, BorderLayout.CENTER);
		
		// Control Panel
		controlPanel = new JPanel();
		controlPanel.setOpaque(false);
		submitButton = new JButton("START GAME");
		submitButton.setPreferredSize(new Dimension(200, 40));
		submitButton.setFocusPainted(false);
		submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		submitButton.setBackground(new Color(34, 139, 34));
		submitButton.setForeground(Color.WHITE);
		
		controlPanel.add(submitButton);
		mainPanel.add(controlPanel, BorderLayout.SOUTH);
		
		setContentPane(mainPanel);
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
		return usernameField.getText().trim();
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
		// non è bloccante
	}
}
