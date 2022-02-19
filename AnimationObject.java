import java.awt.*;

import MathLib.Vector2;

public class AnimationObject {

    private Vector2 position = new Vector2();
    private Vector2 direction = new Vector2();
    private Color objectColor;

    public AnimationObject() {

        this.direction = Vector2.zero;
        this.position = Vector2.zero;
        objectColor = Color.black;
    }

    public Vector2 getDirection() {

        return this.direction;
    }

    public void setDirection(Vector2 newdirection) {

        this.direction = newdirection;
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
