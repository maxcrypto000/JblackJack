package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A custom component that paints a background image.
 */
public class BackGroundPanel extends JComponent{
	private Image image;
    /**
     * Constructs a BackGroundPanel with the specified image.
     *
     * @param image the image to be drawn as the background
     */
    public BackGroundPanel(Image image) {
        this.image = image;
    }
    
    /**
     * Paints the component by drawing the background image across the entire area.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
