import java.awt.*;
import javax.swing.*;

import MathLib.Vector2;

public class AnimationSurface extends JFrame {

    private JFrame animationFrame;

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

    /*
     * @Override
     * public void paint(Graphics g) {
     * 
     * JLabel labelx = new JLabel();
     * labelx.setBounds((int) obj.getPosition().x, (int) obj.getPosition().y, 100,
     * 50);
     * labelx.setVisible(true);
     * labelx.setForeground(obj.getColor());
     * labelx.setText("Überraschung");
     * animationFrame.add(labelx);
     * 
     * g = animationFrame.getGraphics();
     * 
     * g.setColor(obj.getColor());
     * g.fillOval((int) obj.getPosition().x, (int) obj.getPosition().y, 50, 50);
     * 
     * }
     */

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
