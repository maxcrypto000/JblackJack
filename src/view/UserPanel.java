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

import model.ModelManager;

public class UserPanel extends JPanel {

	private CustomButton dealButton, doubleButton, staiButton, splitButton;
	private JButton chip100Button, chip50Button;
	private JPanel controlPanel, chipPanel, infoPanel, wrapper, wrapper1, wrapper2;
	private JLabel nameLabel, capitaleLabel, userLabel, iconaCapitaleLabel;
	private int side = 1;

	private int drawnCardCount = 0;
	private int drawnSplit1Count = 0;
	private int drawnSplit2Count = 0;
	private int animY = 0;
	private boolean isAnimating = false;
	private Timer animTimer;
	
	private int drawnPuntata = 0;
	private int drawnSplit1Puntata = 0;
	private int drawnSplit2Puntata = 0;
	private boolean isChipAnimating = false;
	private int chipAnimX = 0, chipAnimY = 0;
	private Timer chipAnimTimer;
	
	private boolean showResult = false;
	private boolean resultTimerStarted = false;
	private int resultAnimY = -100;

	public UserPanel() {

		this.setPreferredSize(new Dimension(400, 250));
		this.setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setOpaque(false);

		/**
		 * create Panels
		 */
		controlPanel = new JPanel();

		// controlPanel.setBackground(Color.BLACK);
		controlPanel.setLayout(new GridLayout(2, 2, 4, 4));
		controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
		controlPanel.setPreferredSize(new Dimension(190, 90));
		controlPanel.setOpaque(false);
		controlPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		chipPanel = new JPanel();
		// chipPanel.setPreferredSize(new Dimension(100, 100));
		chipPanel.setLayout(new GridLayout(2, 2, 2, 4));
		chipPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 30));
		chipPanel.setOpaque(false);

		wrapper = new JPanel();
		wrapper.setOpaque(false);
		wrapper1 = new JPanel(new GridLayout(2, 1, 0, 0));
		wrapper2 = new JPanel();
		wrapper2.setOpaque(false);
		wrapper1.setPreferredSize(new Dimension(190, 150));
		wrapper1.setOpaque(false);

		infoPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		infoPanel.setPreferredSize(new Dimension(50, 50));
		infoPanel.setOpaque(false);
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		/**
		 * add panels
		 */
		add(wrapper, BorderLayout.WEST);
		add(wrapper2, BorderLayout.EAST);
		wrapper.add(wrapper1);
		wrapper2.add(chipPanel);
		wrapper1.add(infoPanel);
		wrapper1.add(controlPanel);

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

		/**
		 * add buttons
		 */

		controlPanel.add(dealButton);
		controlPanel.add(staiButton);
		controlPanel.add(doubleButton);
		controlPanel.add(splitButton);

		infoPanel.add(nameLabel);

		infoPanel.add(capitaleLabel);
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

	public void resetAnimation() {
		drawnCardCount = 0;
		drawnSplit1Count = 0;
		drawnSplit2Count = 0;
		drawnPuntata = 0;
		drawnSplit1Puntata = 0;
		drawnSplit2Puntata = 0;
		isAnimating = false;
		isChipAnimating = false;
		showResult = false;
		resultTimerStarted = false;
		resultAnimY = -100;
		if (animTimer != null && animTimer.isRunning()) animTimer.stop();
		if (chipAnimTimer != null && chipAnimTimer.isRunning()) chipAnimTimer.stop();
	}

	public void enableStai(boolean enable) {
		/**
		 * disable buttons
		 */
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

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Font font = new Font("Verdana", Font.BOLD, 12);
		g.setFont(font);
		g.setColor(Color.BLACK);
		ArrayList<Integer> cards = ModelManager.getInstance().getCardsOfPlayer(0);
		if (ModelManager.getInstance().isSplit(0)) {
			splitView(g);
		} else {
			int validCardCount = 0;
			for (Integer c : cards) {
				if (c != 0) validCardCount++;
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
			}

			int currentPuntata = ModelManager.getInstance().getPuntataOfPlayer(0);
			if (currentPuntata > drawnPuntata && !isChipAnimating) {
				isChipAnimating = true;
				chipAnimX = 150; // Approximated start from chip button area
				chipAnimY = 200;
				if (chipAnimTimer != null && chipAnimTimer.isRunning()) chipAnimTimer.stop();
				chipAnimTimer = new Timer(15, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						chipAnimX += (349 - chipAnimX) / 4;
						chipAnimY += (95 - chipAnimY) / 4;
						if (Math.abs(349 - chipAnimX) < 5 && Math.abs(95 - chipAnimY) < 5) {
							chipAnimX = 349;
							chipAnimY = 95;
							isChipAnimating = false;
							drawnPuntata = currentPuntata;
							((Timer)e.getSource()).stop();
						}
						repaint();
					}
				});
				chipAnimTimer.start();
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
					g.drawImage(ImageIO.read(new File("res\\" + cards.get(drawnCardCount) + ".png")), nextX, animY, this);
					xAxis = nextX; // For bust/blackjack icons
				}

				if (ModelManager.getInstance().getSum(0) > 21 && validCardCount == drawnCardCount && !isAnimating) {
					g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxis, 0, this);
				} else if (ModelManager.getInstance().getSum(0) == 21 && validCardCount == 2 && validCardCount == drawnCardCount && !isAnimating) {
					g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxis, 0, this);
				}

			} catch (IOException ex) {
				System.out.println("not found " + cards.toString());
			}

			// Draw established chip
			if (drawnPuntata != 0) {
				try {
					g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), 349, 95, this);
				} catch (IOException e) { e.printStackTrace(); }
				g.drawString("" + drawnPuntata, 363, 121);
			}
			// Draw animating chip
			if (isChipAnimating) {
				try {
					g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), chipAnimX, chipAnimY, this);
				} catch (IOException e) { e.printStackTrace(); }
			}

			if (validCardCount > 1 && cards.get(1) != 0 && validCardCount == drawnCardCount && !isAnimating) {
				g.drawString("Value: " + ModelManager.getInstance().getSum(0), 367, 180);
			}

			if (ModelManager.getInstance().isEnded() && !isAnimating) {
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
										((Timer)e2.getSource()).stop();
									}
									repaint();
								}
							});
							fallTimer.start();
							((Timer)e.getSource()).stop();
						}
					});
					delayTimer.setRepeats(false);
					delayTimer.start();
				}

				if (showResult) {
					int result = ModelManager.getInstance().getResult(0);
					showResult(result, g, xAxis, resultAnimY);
				}
			}
		}

		nameLabel.setText("User: " + ModelManager.getInstance().getUserName());
		capitaleLabel.setText("Capitale: " + ModelManager.getInstance().getCapitale(0));

	}

	private void splitView(Graphics g) {
		int xAxisL = 290;
		int xAxisR = 390;
		try {

			ArrayList<Integer> cards1 = ModelManager.getInstance().getCardsOfPlayer(0, 1);
			ArrayList<Integer> cards2 = ModelManager.getInstance().getCardsOfPlayer(0, 2);

			if (cards1.size() > drawnSplit1Count && !isAnimating) {
				isAnimating = true;
				animY = -200;
				if (animTimer != null && animTimer.isRunning())
					animTimer.stop();
				animTimer = new Timer(15, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						animY += 20;
						if (animY >= 0) {
							animY = 0;
							isAnimating = false;
							drawnSplit1Count = cards1.size();
							((Timer) e.getSource()).stop();
						}
						repaint();
					}
				});
				animTimer.start();
			} else if (!isAnimating) {
				drawnSplit1Count = cards1.size();
			}

			for (int i = 0; i < cards1.size(); i++) {
				xAxisL += 10;
				int currentY = (isAnimating && i == cards1.size() - 1) ? animY : 0;
				g.drawImage(ImageIO.read(new File("res\\" + cards1.get(i) + ".png")), xAxisL, currentY, this);

				g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), 305, 95, this);
				g.drawString("" + ModelManager.getInstance().getPuntataOfPlayer(0, 1), 319, 121);
				g.drawString("Value: " + ModelManager.getInstance().getSum(0, 1), 305, 180);

			}
			if (ModelManager.getInstance().getSum(0, 1) > 21) {
				g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxisL, 0, this);
			} else if (ModelManager.getInstance().getSum(0, 1) == 21 && cards1.size() == 2) {
				g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxisL, 0, this);
			}

			if (cards2.size() > drawnSplit2Count && !isAnimating) {
				isAnimating = true;
				animY = -200;
				if (animTimer != null && animTimer.isRunning())
					animTimer.stop();
				animTimer = new Timer(15, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						animY += 20;
						if (animY >= 0) {
							animY = 0;
							isAnimating = false;
							drawnSplit2Count = cards2.size();
							((Timer) e.getSource()).stop();
						}
						repaint();
					}
				});
				animTimer.start();
			} else if (!isAnimating) {
				drawnSplit2Count = cards2.size();
			}

			for (int i = 0; i < cards2.size(); i++) {
				xAxisR += 10;
				int currentY = (isAnimating && i == cards2.size() - 1) ? animY : 0;
				g.drawImage(ImageIO.read(new File("res\\" + cards2.get(i) + ".png")), xAxisR, currentY, this);

				g.drawImage(ImageIO.read(new File("res\\" + "chip" + ".png")), 405, 95, this);
				g.drawString("" + ModelManager.getInstance().getPuntataOfPlayer(0, 2), 419, 121);
				g.drawString("Value: " + ModelManager.getInstance().getSum(0, 2), 405, 180);

			}
			if (ModelManager.getInstance().getSum(0, 2) > 21) {
				g.drawImage(ImageIO.read(new File("res\\" + "Bust" + ".png")), xAxisR, 0, this);
			} else if (ModelManager.getInstance().getSum(0, 2) == 21 && cards2.size() == 2) {
				g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxisL, 0, this);
			}

			// card2 = ImageIO.read(new File("res\\" +
			// ModelManager.getInstance().getCardsOfPlayer(0, 1) + ".png"));
		} catch (IOException ex) {
			System.out.println("not found ");
		}
		if (ModelManager.getInstance().isEnded()) {
			for (int i = 1; i < 3; i++) {
				int result = ModelManager.getInstance().getResult(0, i);
				int xAxis = (i == 1) ? xAxisL : xAxisR;
				showResult(result, g, xAxis);
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
	// case 0 :
	// try {
	// g.drawImage(ImageIO.read(new File("res\\" + "Push" + ".png")), xAxis, 0,
	// this);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// case 1 :
	//
	// try {
	// g.drawImage(ImageIO.read(new File("res\\" + "Win" + ".png")), xAxis, 0,
	// this);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// case 2:
	// System.out.print("\n EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE\n");
	// try {
	// g.drawImage(ImageIO.read(new File("res\\" + "Lose" + ".png")), xAxis, 0,
	// this);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

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

}
