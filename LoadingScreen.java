import java.awt.*;
import javax.swing.*;
import java.awt.Image;
import javax.swing.ImageIcon;

//create a class for Loadingscreen implementing a JComponent
class LoadingScreen extends JComponent {
    private static final long serialVersionUID = 1L;
    private Image backgroundanimation;

    //Contructor tries to load the Animation of the Loadingscreen
    public LoadingScreen() {
        try {

            backgroundanimation = new ImageIcon("Inhalte/Loading.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Background image is drawn in an Override with a paintcomponent using Graphcis g
    @Override
    public void paintComponent(Graphics g) {
        //If the image has been loaded successfully, it is drawn on the JComponent
        if (backgroundanimation == null)
            return;
        
        int imageWidth = backgroundanimation.getWidth(this);
        int imageHeight = backgroundanimation.getHeight(this);
        g.drawImage(backgroundanimation, 0, 0, this);

        //text is also drawn on the JComponent
        g.setFont(new Font("Impact", Font.TRUETYPE_FONT, 28));
        g.setColor(new Color(254, 24, 100));
        g.drawString("A project by Yannis and Henrik", 222, 80);

        //Loop to confirm the image has been drawn correctly 
        for (int i = 0; i * imageWidth <= getWidth(); i++)
            for (int j = 0; j * imageHeight <= getHeight(); j++)
                if (i + j > 0)
                    g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
    }
}