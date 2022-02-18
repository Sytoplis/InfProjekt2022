import java.awt.*;
import javax.swing.*;

public class AnimationObjects {

    private Image skin;
    private int[] position;
    private int[] rotation;
    public static AnimationObjects Objinstance;

    public AnimationObjects() {

        this.rotation = new int[2];
        this.rotation[0] = 0;
        this.rotation[1] = 1;
        this.position = new int[2];
        this.position[0] = 0;
        this.position[1] = 1;
        skin = new ImageIcon().getImage(); // default skin is going up

        Objinstance = this;
    }

    public void changeskin() {

        skin.flush();
        skin = new ImageIcon().getImage();

    }

    public int[] getrotation() {

        return this.rotation;
    }

    public void setrotation(int[] newrotation) {

        this.rotation = newrotation;
    }

    public int[] getposition() {

        return this.position;
    }

    public void setposition(int[] newposition) {

        this.position = newposition;
    }

}
