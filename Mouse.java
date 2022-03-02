import java.awt.event.*;
import java.awt.*;

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
        PointerInfo info = MouseInfo.getPointerInfo();
        Point p = info.getLocation();
        int x = (int) p.getX();
        int y = (int) p.getY();
        
        return new Vector2(x,y);
    }

}
