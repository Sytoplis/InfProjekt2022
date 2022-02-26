import java.awt.*;

import MathLib.Vector2;

public class AnimationObject {

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Color objectColor;

    public AnimationObject() {

        this.velocity = Vector2.zero;
        this.position = Vector2.zero;
        objectColor = Color.black;
    }

    public Vector2 getVelocity() {

        return this.velocity;
    }

    public void setVelocity(Vector2 newvelocity) {

        this.velocity = newvelocity;
    }

    public Vector2 getPosition() {

        return this.position;
    }

    public void setPosition(Vector2 newposition) {

        this.position = newposition;

    }

    public Color getColor() {

        return this.objectColor;
    }

    public void setColor(Color color) {

        this.objectColor = color;

    }

}
