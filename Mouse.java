import java.awt.event.*;
import java.awt.*;
import org.w3c.dom.events.MouseEvent;

import MathLib.Vector2;

public class Mouse extends MouseAdapter{

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        super.mousePressed(e);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        
        super.mouseExited(e);
    }

 @Override
 public void mouseEntered(java.awt.event.MouseEvent e) {
     super.mouseEntered(e);
 }

@Override
public void mouseReleased(java.awt.event.MouseEvent e) {
    super.mouseReleased(e);
}


    public Vector2 getMousePosition(){
        Vector2 mousepos = new Vector2(Vector2.zero());

        PointerInfo a = MouseInfo.getPointerInfo();
Point b = a.getLocation();
int x = (int) b.getX();
int y = (int) b.getY();
        
mousepos = new Vector2(x,y);
        return mousepos;
    }

}
