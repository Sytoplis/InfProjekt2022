import java.awt.*;
import javax.swing.*;

import MathLib.Vector2;

public class AnimationSurface extends JPanel {

    public JFrame frame;

    AnimationObject[] object;

    public AnimationSurface(String simulationType, int objectcount) {
        super();
        buildFrame(simulationType, objectcount);
    }

    public void buildFrame(String simulationType, int objectcount) {
        frame = new JFrame("Simulation: " + simulationType);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        setSize(frame.getWidth(), frame.getHeight());
        createAnimationObjects(objectcount);//create objects
        frame.add(this);

        frame.setVisible(true);
    }

    public Vector2 getframeDimension() {
        return new Vector2(getWidth(), getHeight());
    }

    public void createAnimationObjects(int objectcount) {

        java.util.Random rand = new java.util.Random();
        object = new AnimationObject[objectcount];

        for (int i = 0; i < objectcount; i++) {
            object[i] = new AnimationObject();
            object[i].setPosition(new Vector2(rand.nextInt((int) getframeDimension().x),
                    rand.nextInt((int) getframeDimension().y)));
            object[i].setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            object[i].setDirection(new Vector2(rand.nextInt(1), rand.nextInt(1)));
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(int i = 0; i < object.length; i++){       
            g.fillOval((int) object[i].getPosition().x, (int) object[i].getPosition().y, 25, 25);
            g.setColor(object[i].getColor());

            object[i].setPosition(object[i].getPosition().add(new Vector2(1, 1).mul(0.1)));
        }
    }
}
