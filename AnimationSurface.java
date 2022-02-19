import java.awt.*;
import javax.swing.*;

import MathLib.Vector2;

public class AnimationSurface extends JFrame {

    private JFrame animationFrame;
    private Graphics g;

    public AnimationSurface(String simulationType) {

        buildFrame(simulationType);

    }

    public void buildFrame(String simulationType) {

        animationFrame = new JFrame("Simulation: " + simulationType);
        animationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        animationFrame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        animationFrame.setLocationRelativeTo(null);
        animationFrame.setResizable(false);
        animationFrame.setLayout(null);
        animationFrame.setVisible(true);

    }

    public void drawObject(AnimationObject obj) {

        Graphics g = animationFrame.getGraphics();
        g.setColor(obj.getColor());
        g.fillOval((int) obj.getPosition().x, (int) obj.getPosition().y, 25, 25);

    }

    public Vector2 getframeDimension() {

        return new Vector2(animationFrame.getWidth(), animationFrame.getHeight());
    }

    public void removeContents() {
        animationFrame.getContentPane().removeAll();
    }

}
