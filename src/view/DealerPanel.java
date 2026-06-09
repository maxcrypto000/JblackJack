package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.ModelManager;

public class DealerPanel extends JPanel {

	boolean showCard;

	private int drawnCardCount = 0;
	private int animY = 0;
	private boolean isAnimating = false;
	private Timer animTimer;

	private boolean showResult = false;
	private boolean resultTimerStarted = false;
	private int resultAnimY = -100;

	public DealerPanel() {
		this.setPreferredSize(new Dimension(400, 200));
		this.setOpaque(false);
	}

	public void resetAnimation() {
		drawnCardCount = 0;
		isAnimating = false;
		showResult = false;
		resultTimerStarted = false;
		resultAnimY = -100;
		if (animTimer != null && animTimer.isRunning()) {
			animTimer.stop();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		ArrayList<Integer> cards = ModelManager.getInstance().getCardsOfDealer();
		System.out.print("cards of Dealer " + cards);

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
				public void actionPerformed(ActionEvent e) {
					animY += 5;
					if (animY >= 50) {
						animY = 50;
						isAnimating = false;
						drawnCardCount++;
						((Timer) e.getSource()).stop();
					}
					repaint();
				}
			});
			animTimer.start();
		}

		try {

			int xAxis = 330;
			// Draw up to drawnCardCount
			for (int i = 0; i < drawnCardCount; i++) {
				if (cards.get(i) != 0) {
					xAxis += 20;
					if (!showCard && i == 0) {
						g.drawImage(ImageIO.read(new File("res\\" + "back" + ".png")), xAxis, 50, this);
					} else {
						g.drawImage(ImageIO.read(new File("res\\" + cards.get(i) + ".png")), xAxis, 50, this);
					}
				} else {
					g.drawImage(ImageIO.read(new File("res\\" + "0" + ".png")), xAxis, 50, this);
				}
			}

			// Draw animating card
			if (isAnimating && drawnCardCount < validCardCount) {
				int nextX = xAxis + 20;
				if (cards.get(drawnCardCount) != 0) {
					if (!showCard && drawnCardCount == 0) {
						g.drawImage(ImageIO.read(new File("res\\" + "back" + ".png")), nextX, animY, this);
					} else {
						g.drawImage(ImageIO.read(new File("res\\" + cards.get(drawnCardCount) + ".png")), nextX, animY,
								this);
					}
				} else {
					g.drawImage(ImageIO.read(new File("res\\" + "0" + ".png")), nextX, animY, this);
				}
				xAxis = nextX;
			}

			if (showCard && !isAnimating && validCardCount == drawnCardCount) {
				if (!resultTimerStarted) {
					resultTimerStarted = true;
					Timer delayTimer = new Timer(1500, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							showResult = true;
							resultAnimY = -100;
							Timer fallTimer = new Timer(15, new ActionListener() {
								public void actionPerformed(ActionEvent e2) {
									resultAnimY += 15;
									if (resultAnimY >= 50) {
										resultAnimY = 50;
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
					if (ModelManager.getInstance().getSumOfDealer() == 21
							&& ModelManager.getInstance().getCardsOfDealer().size() == 2) {
						g.drawImage(ImageIO.read(new File("res\\" + "BlackJack" + ".png")), xAxis, resultAnimY, this);
					} else if (ModelManager.getInstance().getSumOfDealer() > 21) {
						g.drawImage(ImageIO.read(new File("res\\" + "Lose" + ".png")), xAxis, resultAnimY, this);
					}
				}
			}

			// card2 = ImageIO.read(new File("res\\" +
			// ModelManager.getInstance().getCardsOfPlayer(0, 1) + ".png"));
		} catch (IOException ex) {
			System.out.println("not found " + cards);
		}
	}

	public void setShowCard(boolean show) {
		showCard = show;
	}

}
