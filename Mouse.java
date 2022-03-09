import java.awt.event.*;
import java.awt.*;

import MathLib.Vector2;

//Class for the Mouse
public class Mouse extends MouseAdapter{

    private Simulation sim;
    public static Mouse instance;

    //Class Simulation is handed over for direct access to simulation
    public Mouse(Simulation simulation){
        instance = this;
        sim = simulation;
    }

    //Override event for a click (mose pressed and released)
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        super.mouseClicked(e);
        sim.onMouseClick(getMousePosition());

    }
    //Override event for when the mouse is pressed
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        super.mousePressed(e);
        sim.onMousePressed(getMousePosition());
    }
    //Override event for when the mouse leaves the simulation surface
    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {  
        super.mouseExited(e);
    }
    //Override event for when the mouse enters the simulation surface
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        super.mouseEntered(e);
    }
    //Override event for when the mouse button gets released
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        super.mouseReleased(e);
        sim.onMouseReleased(getMousePosition());


    }

    //on call, hands over the position of the mouse on the animation surface
    public Vector2 getMousePosition(){
        PointerInfo info = MouseInfo.getPointerInfo();
        Point p = info.getLocation();
        int x = (int) p.getX();
        int y = (int) p.getY();
        
        return new Vector2(x,y);
    }

}
