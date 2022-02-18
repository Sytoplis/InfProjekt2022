import java.awt.*;
import javax.swing.*;

import MathLib.Vector2;

public class AnimationObject {

    private Image skin;
    private Vector2 position = new Vector2();
    private Vector2 direction = new Vector2();
    public static AnimationObject Objinstance;

    public AnimationObject() {

        this.direction = Vector2.zero;
        this.position = Vector2.zero;
        skin = new ImageIcon().getImage(); // default skin is going up

        Objinstance = this;
    }

    public void draw(Graphics g) {

        g.drawOval(50, 50, 50, 50);

    }

    public void changeskin() {

        skin.flush();
        skin = new ImageIcon().getImage();

    }

    public Vector2 getdirection() {

        return this.direction;
    }

    public void setdirection(Vector2 newdirection) {

        this.direction = newdirection;
    }

    public Vector2 getposition() {

        return this.position;
    }

    public void setposition(Vector2 newposition) {

        this.position = newposition;
    }

}
