import java.awt.*;
import javax.swing.*;
import java.awt.Image;
import javax.swing.ImageIcon;

class LoadingScreen extends JComponent {
    private static final long serialVersionUID = 1L;
    private Image backgroundanimation;

    public LoadingScreen() {
        try {
            backgroundanimation = new ImageIcon("Inhalte/Loading.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (backgroundanimation == null)
            return;

        int imageWidth = backgroundanimation.getWidth(this);
        int imageHeight = backgroundanimation.getHeight(this);
        g.drawImage(backgroundanimation, 0, 0, this);

        g.setFont(new Font("Impact", Font.TRUETYPE_FONT, 28));
        g.setColor(new Color(254, 24, 100));
        g.drawString("Ein Projekt von Yannis und Henrik", 206, 80);

        for (int i = 0; i * imageWidth <= getWidth(); i++)
            for (int j = 0; j * imageHeight <= getHeight(); j++)
                if (i + j > 0)
                    g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
    }
}