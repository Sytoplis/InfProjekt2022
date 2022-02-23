import java.awt.*;
import javax.swing.*;

import MathLib.Vector2;

public class AnimationSurface extends JPanel {

    private JFrame frame;
    public static AnimationSurface instance;
    private Inputs input = new Inputs();
    private boolean paused;
    private Simulation sim;
    private long deltaTime;
    private int boidSize;



    AnimationObject[] objects;

    public AnimationSurface(String simulationType, int objectcount) {
        super();
        instance = this;
        buildFrame(simulationType, objectcount);

    }

    public void buildFrame(String simulationType, int objectcount) {
        frame = new JFrame("Simulation: " + simulationType);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 1,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 1);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setIconImage(new ImageIcon("Inhalte/ProgramIcon.png").getImage());
        frame.addKeyListener(input);

        setSize(frame.getWidth(), frame.getHeight());
        createAnimationObjects(objectcount);// create objects
        frame.add(this);

        while (!frame.isVisible()) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        toggleFullscreen();
        frame.toFront();
        JOptionPane.showMessageDialog(this,
                "Mit 'ESC' kann die Simulation beendet werden \nMit 'P' kann die Simulation pausiert werden \nMit 'F11' kann der Vollbildmodus gewechselt werden",
                "Hinweise", JOptionPane.INFORMATION_MESSAGE);

            runSimulation();
    }

    public Vector2 getframeDimension() {
        return new Vector2(getWidth(), getHeight());
    }

    public void createAnimationObjects(int objectcount) {

        java.util.Random rand = new java.util.Random();
        objects = new AnimationObject[objectcount];

        for (int i = 0; i < objectcount; i++) {
            objects[i] = new AnimationObject();
            objects[i].setPosition(new Vector2(rand.nextInt((int) getframeDimension().x),
                    rand.nextInt((int) getframeDimension().y)));
            objects[i].setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            objects[i].setDirection(new Vector2(rand.nextInt(1), rand.nextInt(1)));

        }

        if(objectcount >= 100){
            	boidSize = 20;
        }if(objectcount < 100){
            boidSize = 25;
        }if(objectcount <= 50){
            boidSize = 30;
        }if(objectcount < 10){
            boidSize = 35;
        }
        deltaTime = (int)objectcount/10;

        sim = new BoidSim(objects, frame.getWidth(), frame.getHeight());
    }

    @Override
    public void paint(Graphics g) {
       
        super.paint(g);

        g.setColor(new Color(201, 234, 255));
        g.fillRect(0, 0, getWidth(), getHeight());

        sim.step(0.1*deltaTime/1000);
        for (int i = 0; i < objects.length; i++) {
            g.setColor(objects[i].getColor());
            g.fillOval((int) objects[i].getPosition().x, (int) objects[i].getPosition().y, boidSize, boidSize);

        }
    }

    public void runSimulation(){

        while (true) {
            if (!paused) {
                frame.repaint();
                try {
                    Thread.sleep(deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void startSimulation() {
        frame.setVisible(true);
    }

    public void endSimulation() {
        frame.dispose();
        System.exit(0);
    }

    public void toggleFullscreen() {

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = env.getDefaultScreenDevice();

        if (frame.getWidth() == (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
            dev.setFullScreenWindow(null);
        } else {
            dev.setFullScreenWindow(frame);
        }
    }

    public void pauseSimulation() {
        paused = !paused;
    }

}
