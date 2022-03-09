import java.awt.event.*;

//Class for the Keyinputs
public class Inputs extends KeyAdapter {

    //Override for keypressed event
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        //when the key gets pressed, a method in Animationsurface is called with an instance
        if (key == KeyEvent.VK_ESCAPE)
            AnimationSurface.instance.endSimulation();
        if (key == KeyEvent.VK_F11)
            AnimationSurface.instance.toggleFullscreen();
        if (key == KeyEvent.VK_P) {
            AnimationSurface.instance.pauseSimulation();
        }

    }
}
