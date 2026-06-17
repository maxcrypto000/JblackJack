package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * The panel that displays the player's cards, chips, buttons, and animations.
 * Handles the logic for rendering the player's hands, including split hands.
 */
public class UserPanel extends JPanel {

	private CustomButton dealButton, doubleButton, staiButton, splitButton;
	private JButton chip100Button, chip50Button;
	private JPanel controlPanel, chipPanel, infoPanel, wrapper, wrapper1, wrapper2;
	private JLabel nameLabel, capitaleLabel, winCounterLabel, userLabel, iconaCapitaleLabel;
	private int side = 1;

	private int drawnCardCount = 0;
	private int drawnSplit1Count = 0;
	private int drawnSplit2Count = 0;
	private int animY = 0;
	private boolean isAnimating = false;
	private int animatingSplit = 0; // 0=none, 1=split1, 2=split2
	private Timer animTimer;

	private JLabel instructionLabel;

	private int drawnPuntata = 0;
	private int drawnSplit1Puntata = 0;
	private int drawnSplit2Puntata = 0;
	private boolean isChipAnimating = false;
	private int chipAnimX = 0, chipAnimY = 0;
	private Timer chipAnimTimer;

	private boolean showResult = false;
	private boolean resultTimerStarted = false;
	private int resultAnimY = -100;

	private String userName = "";
	private int capitale = 0;
	private int wins = 0;
	private ArrayList<Integer> cards = new ArrayList<>();
	private int puntata = 0;
	private int sum = 0;
	private boolean isSplit = false;
	private ArrayList<Integer> cardsSplit1 = new ArrayList<>();
	private ArrayList<Integer> cardsSplit2 = new ArrayList<>();
	private int puntataSplit1 = 0;
	private int puntataSplit2 = 0;
	private int sumSplit1 = 0;
	private int sumSplit2 = 0;
	private boolean isEnded = false;
	private int result = -1;
	private int resultSplit1 = -1;
	private int resultSplit2 = -1;

	public void updateModelData(
		String userName, int capitale, int wins, 
		ArrayList<Integer> cards, int puntata, int sum, boolean isSplit, 
		ArrayList<Integer> cardsSplit1, ArrayList<Integer> cardsSplit2, 
		int puntataSplit1, int puntataSplit2, int sumSplit1, int sumSplit2, 
		boolean isEnded, int result, int resultSplit1, int resultSplit2
	) {
		System.out.println("UserPanel.updateModelData received capitale: " + capitale + " and puntata: " + puntata);
		this.userName = userName;
		this.capitale = capitale;
		this.wins = wins;
		this.cards = cards;
		this.puntata = puntata;
		this.sum = sum;
		this.isSplit = isSplit;
		this.cardsSplit1 = cardsSplit1;
		this.cardsSplit2 = cardsSplit2;
		this.puntataSplit1 = puntataSplit1;
		this.puntataSplit2 = puntataSplit2;
		this.sumSplit1 = sumSplit1;
		this.sumSplit2 = sumSplit2;
		this.isEnded = isEnded;
		this.result = result;
		this.resultSplit1 = resultSplit1;
		this.resultSplit2 = resultSplit2;
	}


	/**
	 * Constructs the UserPanel, initializing its layout, buttons, and labels.
	 */
	public UserPanel() {

		this.setPreferredSize(new Dimension(400, 250));
		this.setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
		this.setOpaque(false);

		/**
		 * create Panels
		 */
		controlPanel = new JPanel();

		// controlPanel setup
		controlPanel.setLayout(new GridLayout(2, 2, 5, 5));
		controlPanel.setPreferredSize(new Dimension(190, 75));
		controlPanel.setOpaque(false);

		// chipPanel setup
		chipPanel = new JPanel();
		chipPanel.setLayout(new GridLayout(2, 2, 2, 4));
		chipPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 30));
		chipPanel.setOpaque(false);

		wrapper = new JPanel();
		wrapper.setOpaque(false);
		wrapper1 = new JPanel(new BorderLayout(0, 15)); // Use BorderLayout for better sizing
		wrapper2 = new JPanel();
		wrapper2.setOpaque(false);
		wrapper1.setPreferredSize(new Dimension(200, 180));
		wrapper1.setOpaque(false);

		infoPanel = new JPanel(new GridLayout(3, 1, 2, 2)) {
			@Override
			protected void paintComponent(Graphics g) {
				java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
				g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
						java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(0, 0, 0, 140)); // Semi-transparent black background
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
				g2.setColor(new Color(255, 215, 0)); // Gold border
				g2.setStroke(new java.awt.BasicStroke(1.5f));
				g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
				g2.dispose();
				super.paintComponent(g);
			}
		};
		infoPanel.setPreferredSize(new Dimension(190, 85));
		infoPanel.setOpaque(false);
		infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10)); // Inner padding
		/**
		 * add panels
		 */
		add(wrapper, BorderLayout.WEST);
		add(wrapper2, BorderLayout.EAST);
		wrapper.add(wrapper1);
		wrapper2.add(chipPanel);
		wrapper1.add(infoPanel, BorderLayout.NORTH);
		wrapper1.add(controlPanel, BorderLayout.SOUTH);

		/**
		 * create buttons
		 */
		splitButton = new CustomButton("Split");
		splitButton.setSize(10, 5);
		staiButton = new CustomButton("Stai");
		doubleButton = new CustomButton("Double");
		dealButton = new CustomButton("Deal");
		chip100Button = new JButton();
		chip100Button.setIcon(new ImageIcon("res\\chip100.png"));
		chip100Button.setOpaque(false);
		chip100Button.setContentAreaFilled(false);
		chip100Button.setBorderPainted(false);
		chip100Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		chip50Button = new JButton();
		chip50Button.setIcon(new ImageIcon("res\\chip50.png"));
		chip50Button.setOpaque(false);
		chip50Button.setContentAreaFilled(false);
		chip50Button.setBorderPainted(false);
		chip50Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		/**
		 * create labels
		 */
		capitaleLabel = new JLabel();

		nameLabel = new JLabel();

		nameLabel.setIcon(new ImageIcon("res\\usericon.png"));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

		capitaleLabel.setIcon(new ImageIcon("res\\chipIcon.png"));
		capitaleLabel.setForeground(Color.WHITE);
		capitaleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

		winCounterLabel = new JLabel("Vinte: 0");
		winCounterLabel.setForeground(new Color(255, 215, 0));
		winCounterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

		instructionLabel = new JLabel("Fai la tua puntata cliccando sulle chips!", SwingConstants.CENTER);
		instructionLabel.setForeground(new Color(255, 215, 0)); // Colore dorato per risaltare
		instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		instructionLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		add(instructionLabel, BorderLayout.SOUTH);

		/**
		 * add buttons
		 */

		controlPanel.add(dealButton);
		controlPanel.add(staiButton);
		controlPanel.add(doubleButton);
		controlPanel.add(splitButton);

		infoPanel.add(nameLabel);
		infoPanel.add(capitaleLabel);
		infoPanel.add(winCounterLabel);
		chipPanel.add(chip100Button);
		chipPanel.add(chip50Button);

		/**
		 * set buttons to disabled
		 */
		staiButton.setEnabled(false);
		dealButton.setEnabled(false);
		splitButton.setEnabled(false);
		doubleButton.setEnabled(false);
	}

	/**
	 * Switches the text of the Deal button to "Carta" (Hit) and updates
	 * the availability of other buttons during the game.
	 */
	public void switchDealForCarta() {
		/**
		 * Change name of buttons
		 */
		dealButton.setText("Carta");
		/**
		 * disable Chips
		 */
		chip50Button.setEnabled(false);
		chip100Button.setEnabled(false);
		/**
		 * disable deal button
		 */
		staiButton.setEnabled(true);
		doubleButton.setEnabled(true);

	}

	/**
	 * Resets the buttons to their initial states (e.g., enabling chips, resetting
	 * text).
	 */
	public void resetButtons() {
		/**
		 * enable Chips
		 */
		chip50Button.setEnabled(true);
		chip100Button.setEnabled(true);
		/**
		 * Change name of buttons
		 */
		dealButton.setText("Deal");

	}

	/**
	 * Resets all animations and visual states for the player panel.
	 */
	public void resetAnimation() {
		drawnCardCount = 0;
		drawnSplit1Count = 0;
		drawnSplit2Count = 0;
		drawnPuntata = 0;
		drawnSplit1Puntata = 0;
		drawnSplit2Puntata = 0;
		isAnimating = false;
		animatingSplit = 0;
		isChipAnimating = false;
		showResult = false;
		resultTimerStarted = false;
		resultAnimY = -100;
		if (animTimer != null && animTimer.isRunning())
			animTimer.stop();
		if (chipAnimTimer != null && chipAnimTimer.isRunning())
			chipAnimTimer.stop();
	}

	public void enableStai(boolean enable) {
		staiButton.setEnabled(enable);
	}

	public void enableDeal(boolean enable) {

		dealButton.setEnabled(enable);
	}

	public void enableDouble(boolean enable) {

		doubleButton.setEnabled(enable);
	}

	public void enableSplit(boolean enable) {

		splitButton.setEnabled(enable);
	}

	/**
	 * Paints the player's components, including cards, chips, and animations.
	 *
	 * @param g the Graphics context in which to paint
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Font font = new Font("Verdana", Font.BOLD, 12);
		g.setFont(font);
		g.setColor(Color.BLACK);
		if (isSplit) {
			splitView(g);
		} else {
			int validCardCount = 0;
			for (Integer c : cards) {
				if (c != 0)
					validCardCount++;
			}

			if (validCardCount > drawnCardCount && !isAnimating) {
				isAnimating = true;
				animY = -200;
				if (animTimer != null && animTimer.isRunning())
					animTimer.stop();
				animTimer = new Timer(15, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						animY += 15;
						if (animY >= 0) {
							animY = 0;
							isAnimating = false;
							drawnCardCount++;
							((Timer) e.getSource()).stop();
						}
						repaint();
					}
				});
				animTimer.start();
				controller.AudioManager.getInstance().play("res\\placingCard.wav");
			}

			int currentPuntata = puntata;
			if (currentPuntata > drawnPuntata && !isChipAnimating) {
				isChipAnimating = true;
				chipAnimX = 360; // Start from the chip buttons on the right
				chipAnimY = 200; // Bottom right area
				if (chipAnimTimer != null && chipAnimTimer.isRunning())
					chipAnimTimer.stop();
				chipAnimTimer = new Timer(15, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						chipAnimX += (349 - chipAnimX) / 4;
						chipAnimY += (95 - chipAnimY) / 4;
						if (Math.abs(349 - chipAnimX) < 5 && Math.abs(95 - chipAnimY) < 5) {
							chipAnimX = 349;
							chipAnimY = 95;
							isChipAnimating = false;
							drawnPuntata = currentPuntata;
							((Timer) e.getSource()).stop();
						}
						repaint();
					}
				});
				chipAnimTimer.start();
				controller.AudioManager.getInstance().play("res\\placingChip.wav");
			}

			int xAxis = 244;
			try {
				// Draw only up to drawnCardCount
				for (int i = 0; i < drawnCardCount; i++) {
					xAxis += 70;
					g.drawImage(ImageIO.read(new File("res\\" + cards.get(i) + ".png")), xAxis, 0, this);
				}
				// Draw the animating card
				if (isAnimating && drawnCardCount < validCardCount) {
					int nextX = xAxis + 70;
					g.drawImage(ImageIO.read(new File("res\\" + cards.get(drawnCardCount) + ".png")), nextX, animY,
							this);
					xAxis = nextX; // For bust/blackjack icons
				}

				if (sum > 21 && validCardCount == drawnCardCount && !isAnimating) {
					g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxis, 0, this);
				} else if (sum == 21 && validCardCount == 2
						&& validCardCount == drawnCardCount && !isAnimating) {
					g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxis, 0, this);
				}

			} catch (IOException ex) {
				System.out.println("not found " + cards.toString());
			}

			// Draw established chip
			if (drawnPuntata != 0) {
				try {
					g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), 349, 95, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawString("" + drawnPuntata, 363, 121);
			}
			// Draw animating chip
			if (isChipAnimating) {
				try {
					g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), chipAnimX, chipAnimY, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (validCardCount > 1 && cards.get(1) != 0 && validCardCount == drawnCardCount && !isAnimating) {
				String valueStr = "Value: " + sum;
				java.awt.FontMetrics fm = g.getFontMetrics();
				int textWidth = fm.stringWidth(valueStr);
				int rectWidth = textWidth + 20;
				g.setColor(new Color(0, 0, 0, 180));
				g.fillRoundRect(350, 160, rectWidth, 25, 15, 15);
				g.setColor(new Color(255, 215, 0)); // Gold color
				g.drawString(valueStr, 360, 178);
			}

			if (isEnded && !isAnimating) {
				if (!resultTimerStarted) {
					resultTimerStarted = true;
					Timer delayTimer = new Timer(1500, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							showResult = true;
							resultAnimY = -100;
							Timer fallTimer = new Timer(15, new ActionListener() {
								public void actionPerformed(ActionEvent e2) {
									resultAnimY += 15;
									if (resultAnimY >= 0) {
										resultAnimY = 0;
										((Timer) e2.getSource()).stop();
									}
									repaint();
								}
							});
							fallTimer.start();
							((Timer) e.getSource()).stop();
						}
					});
					delayTimer.setRepeats(false);
					delayTimer.start();
				}

				if (showResult) {
					showResult(result, g, xAxis, resultAnimY);
				}
			}
		}

		nameLabel.setText("User: " + userName);
		capitaleLabel.setText("Capitale: " + capitale);
		winCounterLabel.setText("Partite vinte: " + wins);

	}

	private void splitView(Graphics g) {
		int xAxisL = 290;
		int xAxisR = 390;
		try {

			ArrayList<Integer> cards1 = cardsSplit1;
			ArrayList<Integer> cards2 = cardsSplit2;

			if (cards1.size() > drawnSplit1Count && !isAnimating) {
				isAnimating = true;
				animatingSplit = 1;
				animY = -200;
				if (animTimer != null && animTimer.isRunning())
					animTimer.stop();
				animTimer = new Timer(15, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						animY += 20;
						if (animY >= 0) {
							animY = 0;
							isAnimating = false;
							animatingSplit = 0;
							drawnSplit1Count = cards1.size();
							((Timer) e.getSource()).stop();
						}
						repaint();
					}
				});
				animTimer.start();
				controller.AudioManager.getInstance().play("res\\placingCard.wav");
			} else if (!isAnimating) {
				drawnSplit1Count = cards1.size();
			}

			for (int i = 0; i < cards1.size(); i++) {
				xAxisL += 10;
				int currentY = (isAnimating && animatingSplit == 1 && i == cards1.size() - 1) ? animY : 0;
				g.drawImage(ImageIO.read(new File("res\\" + cards1.get(i) + ".png")), xAxisL, currentY, this);
			}

			if (cards1.size() > 0) {
				g.setColor(Color.BLACK);
				g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), 305, 95, this);
				g.drawString("" + puntataSplit1, 319, 121);

				if (cards1.size() == drawnSplit1Count && !(isAnimating && animatingSplit == 1)) {
					String valueStr = "Value: " + sumSplit1;
					java.awt.FontMetrics fm = g.getFontMetrics();
					int textWidth = fm.stringWidth(valueStr);
					int rectWidth = textWidth + 20;
					g.setColor(new Color(0, 0, 0, 180));
					g.fillRoundRect(295, 160, rectWidth, 25, 15, 15);
					g.setColor(new Color(255, 215, 0)); // Gold color
					g.drawString(valueStr, 305, 178);
					g.setColor(Color.BLACK);
				}
			}
			if (sumSplit1 > 21) {
				g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxisL, 0, this);
			} else if (sumSplit1 == 21 && cards1.size() == 2) {
				g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxisL, 0, this);
			}

			if (cards2.size() > drawnSplit2Count && !isAnimating) {
				isAnimating = true;
				animatingSplit = 2;
				animY = -200;
				if (animTimer != null && animTimer.isRunning())
					animTimer.stop();
				animTimer = new Timer(15, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						animY += 20;
						if (animY >= 0) {
							animY = 0;
							isAnimating = false;
							animatingSplit = 0;
							drawnSplit2Count = cards2.size();
							((Timer) e.getSource()).stop();
						}
						repaint();
					}
				});
				animTimer.start();
				controller.AudioManager.getInstance().play("res\\placingCard.wav");
			} else if (!isAnimating) {
				drawnSplit2Count = cards2.size();
			}

			for (int i = 0; i < cards2.size(); i++) {
				xAxisR += 10;
				int currentY = (isAnimating && animatingSplit == 2 && i == cards2.size() - 1) ? animY : 0;
				g.drawImage(ImageIO.read(new File("res\\" + cards2.get(i) + ".png")), xAxisR, currentY, this);
			}

			if (cards2.size() > 0) {
				g.setColor(Color.BLACK);
				g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), 405, 95, this);
				g.drawString("" + puntataSplit2, 419, 121);

				if (cards2.size() == drawnSplit2Count && !(isAnimating && animatingSplit == 2)) {
					String valueStr = "Value: " + sumSplit2;
					java.awt.FontMetrics fm = g.getFontMetrics();
					int textWidth = fm.stringWidth(valueStr);
					int rectWidth = textWidth + 20;
					g.setColor(new Color(0, 0, 0, 180));
					g.fillRoundRect(395, 160, rectWidth, 25, 15, 15);
					g.setColor(new Color(255, 215, 0)); // Gold color
					g.drawString(valueStr, 405, 178);
					g.setColor(Color.BLACK);
				}
			}
			if (sumSplit2 > 21) {
				g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxisR, 0, this);
			} else if (sumSplit2 == 21 && cards2.size() == 2) {
				g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxisR, 0, this);
			}

			// card2 = ImageIO.read(new File("res\\" +
			// cardsSplit1 + ".png"));
		} catch (IOException ex) {
			System.out.println("not found ");
		}
		if (isEnded) {
			for (int i = 1; i < 3; i++) {
				int currentResult = (i == 1) ? resultSplit1 : resultSplit2;
				int xAxis = (i == 1) ? xAxisL : xAxisR;
				showResult(currentResult, g, xAxis, 0);
			}
		}
		int xFinger = 0;

		if (side == 1) {

			xFinger = 280;
		} else {

			xFinger = 380;
		}

		try {
			g.drawImage(ImageIO.read(new File("res\\" + "finger" + ".png")), xFinger, 40, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showResult(int result, Graphics g, int xAxis, int yAxis) {
		System.out.println(" !!!! \n -----------------\n" + "RESULT IS " + result + " !!!! \n -----------------\n");
		try {
			switch (result) {
				case 0 -> g.drawImage(ImageIO.read(new File("res\\" + "Push" + ".png")), xAxis, yAxis, this);
				case 1 -> g.drawImage(ImageIO.read(new File("res\\" + "Win" + ".png")), xAxis, yAxis, this);
				case 2 -> g.drawImage(ImageIO.read(new File("res\\" + "Lose" + ".png")), xAxis, yAxis, this);
				case 3 -> g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxis, yAxis, this);
				case 4 -> g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxis, yAxis, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDealButtonListener(ActionListener al) {
		dealButton.addActionListener(al);
	}

	public void add50ButtonListener(ActionListener al) {
		chip50Button.addActionListener(al);
	}

	public void add100ButtonListener(ActionListener al) {
		chip100Button.addActionListener(al);
	}

	public void addStaiListener(ActionListener al) {
		staiButton.addActionListener(al);
	}

	public void addDoubleListener(ActionListener al) {
		doubleButton.addActionListener(al);
	}

	public void addSplitListener(ActionListener al) {
		splitButton.addActionListener(al);
	}

	/**
	 * @return the side
	 */
	public int getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(int side) {
		this.side = side;
	}

	public void setInstruction(String text) {
		instructionLabel.setText(text);
	}

}
