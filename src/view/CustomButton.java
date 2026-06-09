package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class CustomButton extends JButton {
    private boolean isHovered = false;
    private Color normalColor = new Color(40, 40, 40, 200);
    private Color hoverColor = new Color(70, 70, 70, 230);
    private Color disabledColor = new Color(100, 100, 100, 150);
    private Color textColor = new Color(255, 215, 0); // Gold
    private Color disabledTextColor = new Color(180, 180, 180);

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(textColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    isHovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        if (!isEnabled()) {
            g2.setColor(disabledColor);
        } else if (isHovered) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Border
        if (isEnabled()) {
            g2.setColor(textColor);
        } else {
            g2.setColor(disabledTextColor);
        }
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        g2.dispose();

        // Let standard painting draw the text, but ensure text color is correct
        if (!isEnabled()) {
            setForeground(disabledTextColor);
        } else {
            setForeground(textColor);
        }
        super.paintComponent(g);
    }
}
