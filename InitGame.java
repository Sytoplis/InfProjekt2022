import javax.swing.*;
import MathLib.Vector2;
import java.awt.*;
import java.util.*;

public class InitGame {

    AnimationObject[] object;
    AnimationSurface surface;
    int objectcount;
    private Graphics g;

    public InitGame(String simulationType, int objectcount) {

        this.objectcount = objectcount;
        createAnimationSurface(simulationType);
        createAnimationObjects(objectcount);

        // paint();

    }

    public void createAnimationSurface(String simulationType) {

        surface = new AnimationSurface(simulationType);

    }

    public void createAnimationObjects(int objectcount) {

        Random rand = new Random();
        object = new AnimationObject[objectcount];

        for (int i = 0; i < objectcount; i++) {
            object[i] = new AnimationObject();
            object[i].setPosition(new Vector2(rand.nextInt((int) surface.getframeDimension().x),
                    rand.nextInt((int) surface.getframeDimension().y)));
            object[i].setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            object[i].setDirection(new Vector2(rand.nextInt(1), rand.nextInt(1)));
        }

        while (true) {

            for (int i = 0; i < objectcount; i++) {
                surface.drawObject(object[i]);

            }

        }
    }

    /*
     * @Override
     * public void paint() {
     * 
     * Graphics g = surface.getGraphics();
     * 
     * for (int i = 0; i < objectcount; i++) {
     * 
     * g.setColor(object[i].getColor());
     * g.fillOval((int) object[i].getPosition().x, (int) object[i].getPosition().y,
     * 50, 50);
     * 
     * }
     * 
     * }
     */

}
