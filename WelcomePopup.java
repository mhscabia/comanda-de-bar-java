package FuckinAe;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomePopup {
    public static void WelcomePopup(){
        // Load the background image from a file
        final BufferedImage[] backgroundImage = {null}; // Create a final array to hold the image

        try {
            File file = new File("670x534.jpg"); // Replace with your image file path
            backgroundImage[0] = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a custom JPanel for the message with styling
        JPanel messagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage[0] != null) {
                    g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), this);
                }

                // Outline
                g.setColor(Color.BLACK); // Set the outline color
                g.setFont(new Font("Arial", Font.BOLD, 40));
                String message = "Bem vindo a Caverna do bin Laden";
                int stringWidth = g.getFontMetrics().stringWidth(message);
                int x = (getWidth() - stringWidth) / 2;
                int y = getHeight() / 2;
                // Draw the outline by repeatedly drawing the text in different positions
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        g.drawString(message, x + i, y + j);
                    }
                }

                // Main text
                g.setColor(Color.BLUE);
                g.drawString(message, x, y);
            }

        };
        messagePanel.setLayout(null);
        messagePanel.setPreferredSize(new Dimension(800, 600)); // Adjust panel size

        // Show the JOptionPane with the custom messagePanel
        JOptionPane.showMessageDialog(null, messagePanel, "Welcome", JOptionPane.PLAIN_MESSAGE);
    }
}
