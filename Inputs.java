import java.awt.event.*;

public class Inputs extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE)
            AnimationSurface.instance.endSimulation();
        if (key == KeyEvent.VK_F11)
            AnimationSurface.instance.toggleFullscreen();
        if (key == KeyEvent.VK_P) {
            AnimationSurface.instance.pauseSimulation();
        }

    }
}
