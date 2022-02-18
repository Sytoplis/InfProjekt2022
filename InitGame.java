import java.awt.Toolkit;
import javax.swing.*;
import java.awt.*;

public class InitGame {

    public InitGame(JFrame frame) {

        AnimationObject object = new AnimationObject();
        frame.add(object);
        object.setForeground(Color.green);
        object.setPosition(Vector2.);


        new AnimationSurface(frame);

    }

}
