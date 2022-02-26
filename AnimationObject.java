import java.awt.*;

import MathLib.Vector2;

public class AnimationObject {

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Color objectColor;

    public AnimationObject() {
        velocity = Vector2.zero();
        position = Vector2.zero();
        objectColor = Color.black;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 newvelocity) {
        velocity = newvelocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 newposition) {
        position = newposition;
    }

    public Color getColor() {
        return objectColor;
    }

    public void setColor(Color color) {
        objectColor = color;
    }

}
