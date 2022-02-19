import javax.swing.*;
import MathLib.Vector2;
import java.awt.*;

public class InitGame {

    JFrame frame;
    AnimationObject object;
    private Graphics g;

    public InitGame(JFrame frame) {

        this.frame = frame;
        object = new AnimationObject();
        object.setPosition(Vector2.zero);

        new AnimationSurface(frame);
        paint(g);

    }

    public void paint(Graphics g) {

        g.setColor(Color.red);
        g.drawOval((int) object.getPosition().x, (int) object.getPosition().y, 50, 200);
        // g.drawOval(0, 0, 50, 200);

    }

}
