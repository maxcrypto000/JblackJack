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

public class CenterPanel extends JPanel {

	private boolean isEnded;
	private FadingPanel displayPanel;
	private JLabel winnerLabel;
	private CustomButton playAgainButton;
	private ModelManager mm = ModelManager.getInstance();

	public CenterPanel() {
		this.setOpaque(false);
		// this.setBackground(new Color(7, 113, 0));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		displayPanel = new FadingPanel(new GridLayout(2, 1));
		displayPanel.setPreferredSize(new Dimension(260, 100));
		displayPanel.setOpaque(false);
		winnerLabel = new JLabel();
		winnerLabel.setBackground(Color.BLACK);
		winnerLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
		winnerLabel.setForeground(Color.WHITE);
		playAgainButton = new CustomButton("Play Again");

		displayPanel.add(winnerLabel);
		displayPanel.add(playAgainButton);
		add(displayPanel);
		playAgainButton.setVisible(false);
		winnerLabel.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mm.isEnded()) {
			playAgainButton.setVisible(true);
		}

		if (mm.getBust(0)) {
			winnerLabel.setText("    BUST !");
			playAgainButton.setVisible(true);
			winnerLabel.setIcon(new ImageIcon("res\\youLOSE.png"));
			winnerLabel.setVisible(true);
		}
		if (mm.getSum(0) == 21 && !isEnded) {
			if (mm.getCardsOfPlayer(0).size() == 2) {
				winnerLabel.setText("BLACKJACK");
				playAgainButton.setVisible(true);
			} else {
				winnerLabel.setText("21 !");
			}
			winnerLabel.setVisible(true);
		}
	}

	public void setWinner(int result) {

		isEnded = true;

		switch (result) {
			case 0:
				winnerLabel.setIcon(new ImageIcon("res\\youPUSH.png"));
				System.out.println("PUSHHH");
				break;
			case 1:
				if (mm.getSum(0) == 21 && mm.getCardsOfPlayer(0).size() == 2) {
					winnerLabel.setText("BLACKJACK");
				} else {

					winnerLabel.setText("");
					winnerLabel.setIcon(new ImageIcon("res\\youWIN.png"));

				}

				System.out.println("WONN");
				break;
			case 2:
				winnerLabel.setText("");
				winnerLabel.setIcon(new ImageIcon("res\\youLOSE.png"));

				break;
		}
		triggerFadeIn();
	}

	private void triggerFadeIn() {
		displayPanel.setAlpha(0f);

		Timer delayTimer = new Timer(1500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAgainButton.setVisible(true);
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

	public void addPlayAgainListener(ActionListener al) {
		playAgainButton.addActionListener(al);
	}

	public void reset() {
		playAgainButton.setVisible(false);
		winnerLabel.setVisible(false);
		isEnded = false;
	}

	class FadingPanel extends JPanel {
		float alpha = 1f;

		public FadingPanel(java.awt.LayoutManager layout) {
			super(layout);
		}

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			super.paint(g2);
			g2.dispose();
		}

		public void setAlpha(float a) {
			this.alpha = a;
			repaint();
		}
	}
}
