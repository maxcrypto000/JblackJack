package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.ModelManager;

/**
 * Represents the central panel of the Blackjack game interface.
 * It displays the game outcome (win, lose, push, blackjack) and a "Play Again"
 * button.
 */
public class CenterPanel extends JPanel {

	private boolean isEnded;
	private FadingPanel displayPanel;
	private JLabel winnerLabel;
	private CustomButton playAgainButton;
	private ModelManager mm = ModelManager.getInstance();

	/**
	 * Constructs a new CenterPanel with a fading display panel, a winner label,
	 * and a "Play Again" button. Initializes components and sets their visibility.
	 */
	public CenterPanel() {
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		displayPanel = new FadingPanel(new GridLayout(2, 1));
		displayPanel.setPreferredSize(new Dimension(260, 100));
		displayPanel.setOpaque(false);
		winnerLabel = new JLabel();
		winnerLabel.setBackground(Color.BLACK);
		winnerLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
		winnerLabel.setForeground(Color.WHITE);
		playAgainButton = new CustomButton("Play Again");
		playAgainButton.setPreferredSize(new Dimension(150, 40));

		JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonWrapper.setOpaque(false);
		buttonWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		buttonWrapper.add(playAgainButton);

		displayPanel.add(winnerLabel);
		displayPanel.add(buttonWrapper);
		add(displayPanel);
		buttonWrapper.setVisible(false);
		playAgainButton.setVisible(false);
		winnerLabel.setVisible(false);
	}

	/**
	 * Paints the graphical components of the panel. Updates the visibility of the
	 * "Play Again" button and the winner label based on the current game state
	 * (e.g., bust, blackjack, or game ended).
	 *
	 * @param g the Graphics context in which to paint
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mm.isEnded()) {
			playAgainButton.setVisible(true);
			playAgainButton.getParent().setVisible(true);
		}

		if (mm.getBust(0)) {
			winnerLabel.setText("    BUST !");
			playAgainButton.setVisible(true);
			playAgainButton.getParent().setVisible(true);
			winnerLabel.setIcon(new ImageIcon("res\\youLOSE.png"));
			winnerLabel.setVisible(true);
		}

	}

	/**
	 * Sets the game outcome and displays the appropriate graphical feedback
	 * (e.g., win, lose, push, or blackjack images/text). Triggers a fade-in
	 * animation
	 * for the result display.
	 *
	 * @param result an integer representing the game result:
	 *               0 for push, 1 for win (or blackjack), 2 for lose
	 */
	public void setWinner(int result) {

		isEnded = true;

		switch (result) {
			case 0:
				winnerLabel.setIcon(new ImageIcon("res\\youPUSH.png"));
				System.out.println("PUSHHH");
				break;
			case 1:
				winnerLabel.setText("");
				winnerLabel.setIcon(new ImageIcon("res\\youWIN.png"));
				System.out.println("WONN");
				break;
			case 2:
				winnerLabel.setText("");
				winnerLabel.setIcon(new ImageIcon("res\\youLOSE.png"));

				break;
			case 4:
				winnerLabel.setText("BLACKJACK");
				winnerLabel.setIcon(new ImageIcon("res\\YouBLACKJACK.png"));
				break;
		}
		triggerFadeIn();
	}

	/**
	 * Triggers a fade-in animation for the display panel, making the winner label
	 * and the "Play Again" button smoothly become visible after a delay.
	 */
	private void triggerFadeIn() {
		displayPanel.setAlpha(0f);

		Timer delayTimer = new Timer(1500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAgainButton.setVisible(true);
				playAgainButton.getParent().setVisible(true);
				winnerLabel.setVisible(true);

				Timer timer = new Timer(50, new ActionListener() {
					float alpha = 0f;

					@Override
					public void actionPerformed(ActionEvent e2) {
						alpha += 0.1f;
						if (alpha >= 1f) {
							alpha = 1f;
							((Timer) e2.getSource()).stop();
						}
						displayPanel.setAlpha(alpha);
					}
				});
				timer.start();
				((Timer) e.getSource()).stop();
			}
		});
		delayTimer.setRepeats(false);
		delayTimer.start();
	}

	/**
	 * Adds an ActionListener to the "Play Again" button.
	 *
	 * @param al the ActionListener to be added
	 */
	public void addPlayAgainListener(ActionListener al) {
		playAgainButton.addActionListener(al);
	}

	/**
	 * Resets the center panel to its initial state by hiding the "Play Again"
	 * button and the winner label, and resetting the game ended flag.
	 */
	public void reset() {
		playAgainButton.setVisible(false);
		if (playAgainButton.getParent() != null) {
			playAgainButton.getParent().setVisible(false);
		}
		winnerLabel.setVisible(false);
		isEnded = false;
	}

	/**
	 * A custom JPanel that supports fading effects by altering its alpha composite.
	 */
	class FadingPanel extends JPanel {
		float alpha = 1f;

		/**
		 * Constructs a new FadingPanel with the specified layout manager.
		 *
		 * @param layout the LayoutManager to use
		 */
		public FadingPanel(java.awt.LayoutManager layout) {
			super(layout);
		}

		/**
		 * Paints the panel using the current alpha value to create a fading effect.
		 *
		 * @param g the Graphics context in which to paint
		 */
		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			super.paint(g2);
			g2.dispose();
		}

		/**
		 * Sets the alpha transparency level of the panel and repaints it.
		 *
		 * @param a the alpha value (0.0f for fully transparent, 1.0f for fully opaque)
		 */
		public void setAlpha(float a) {
			this.alpha = a;
			repaint();
		}
	}
}
