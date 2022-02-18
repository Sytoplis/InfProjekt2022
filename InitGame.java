import java.awt.Toolkit;
import javax.swing.*;
import java.awt.*;

public class InitGame {

    JFrame frame;

    public InitGame(JFrame frame) {

        this.frame = frame;
        AnimationObject object = new AnimationObject();
        frame.add(object);
        object.setForeground(Color.green);
        object.setBounds(400, 300, 50, 50);

        frame.repaint();

        new AnimationSurface(frame);

    }

    public void paintComponent(Graphics g) {

        g.drawOval(50, 50, 50, 200);
        frame.repaint();
    }

}
