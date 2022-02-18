import java.awt.*;
import javax.swing.*;

public class AnimationObject {

    private Image skin;
    private int[] position;
    private int[] direction;
    public static AnimationObject Objinstance;

    public AnimationObject() {

        this.direction = new int[2];
        this.direction[0] = 0;
        this.direction[1] = 1;
        this.position = new int[2];
        this.position[0] = 0;
        this.position[1] = 1;
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

    public int[] getdirection() {

        return this.direction;
    }

    public void setdirection(int[] newdirection) {

        this.direction = newdirection;
    }

    public int[] getposition() {

        return this.position;
    }

    public void setposition(int[] newposition) {

        this.position = newposition;
    }

}
