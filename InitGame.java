import javax.swing.*;
import MathLib.Vector2;
import java.awt.*;
import java.util.*;

public class InitGame {

    AnimationObject[] object;
    AnimationSurface surface;

    public InitGame(String simulationType, int objectcount) {

        createAnimationSurface(simulationType);
        createAnimationObjects(objectcount);

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

            surface.drawObject(object[i]);
        }

        while (true) {

            for (int i = 0; i < objectcount; i++) {
                // if (rand.nextInt(1) == 1) {
                object[i].setPosition(new Vector2((int) object[i].getPosition().x + 1,
                        (int) object[i].getPosition().y + 1));

                // } else {

                // object[i].setPosition(new Vector2((int) object[i].getPosition().x -
                // rand.nextInt(1),
                // (int) object[i].getPosition().y - rand.nextInt(1)));
                // }

                surface.drawObject(object[i]);
            }

        }
    }

}
