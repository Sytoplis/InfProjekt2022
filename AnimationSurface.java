import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.plaf.SliderUI;

import MathLib.Vector2;

public class AnimationSurface extends JPanel {

    private JFrame frame;
    public static AnimationSurface instance;
    private Inputs input = new Inputs();
    private boolean paused;
    private Simulation sim;
    private long deltaTime;
    private int boidSize;
    private JSlider animationspeed;
    private JSlider cohesion;
    private JSlider seperation;
    private JSlider alignment;

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
        setLayout(null);
        createAnimationObjects(objectcount);// create objects
        frame.add(this);

        animationspeed = new JSlider(JSlider.HORIZONTAL, 5, 80, 20);
        animationspeed.setMajorTickSpacing(5);
        animationspeed.setMinorTickSpacing(1);
        animationspeed.setBounds(10, frame.getHeight() - 60, 250, 60);
        Hashtable animspeedTable = new Hashtable();
        animspeedTable.put(20, new JLabel("1x"));
        animspeedTable.put(5, new JLabel("0.25x"));
        animspeedTable.put(animationspeed.getMaximum(), new JLabel("4x"));
        animationspeed.setLabelTable(animspeedTable);
        animationspeed.setPaintLabels(true);
        animationspeed.setPaintTicks(true);
        animationspeed.setFocusable(false);
        add(animationspeed);

        cohesion = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
        cohesion.setMajorTickSpacing(10);
        cohesion.setMinorTickSpacing(5);
        cohesion.setBounds(270, frame.getHeight() - 60, 250, 60);
        Hashtable cohesionTable = new Hashtable();
        cohesionTable.put(5, new JLabel("n"));
        cohesionTable.put(0, new JLabel("0"));
        cohesionTable.put(100, new JLabel("0.1"));
        cohesion.setLabelTable(cohesionTable);
        cohesion.setPaintLabels(true);
        cohesion.setPaintTicks(true);
        cohesion.setFocusable(false);
        add(cohesion);

        seperation = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
        seperation.setMajorTickSpacing(10);
        seperation.setMinorTickSpacing(5);
        seperation.setBounds(530, frame.getHeight() - 60, 250, 60);
        Hashtable seperationTable = new Hashtable();
        seperationTable.put(5, new JLabel("n"));
        seperationTable.put(0, new JLabel("0"));
        seperationTable.put(100, new JLabel("1"));
        seperation.setLabelTable(seperationTable);
        seperation.setPaintLabels(true);
        seperation.setPaintTicks(true);
        seperation.setFocusable(false);
        add(seperation);

        alignment = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
        alignment.setMajorTickSpacing(10);
        alignment.setMinorTickSpacing(5);
        alignment.setBounds(790, frame.getHeight() - 60, 250, 60);
        Hashtable alignmentTable = new Hashtable();
        alignmentTable.put(1, new JLabel("n"));
        alignmentTable.put(0, new JLabel("0"));
        alignmentTable.put(100, new JLabel("1"));
        alignment.setLabelTable(alignmentTable);
        alignment.setPaintLabels(true);
        alignment.setPaintTicks(true);
        alignment.setFocusable(false);
        add(alignment);

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
                "Mit 'ESC' kann die Simulation beendet werden \nMit 'P' kann die Simulation pausiert werden \nMit 'F11' kann der Vollbildmodus gewechselt werden \nMit dem Slider kann die Geschwindigkeit angepasst werden",
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
            objects[i].setVelocity(new Vector2(0, 0));

        }

        if (objectcount <= 100000) {
            boidSize = 3;
        }
        if (objectcount <= 10000) {
            boidSize = 5;
        }
        if (objectcount <= 1000) {
            boidSize = 10;
        }
        if (objectcount <= 100) {
            boidSize = 20;
        }
        if (objectcount <= 100) {
            boidSize = 25;
        }
        if (objectcount <= 50) {
            boidSize = 30;
        }
        if (objectcount < 10) {
            boidSize = 35;
        }
        deltaTime = 10;

        sim = new Simulation(objects, frame.getWidth(), frame.getHeight());
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        g.setColor(new Color(201, 234, 255));
        // g.fillRect(0, 0, getWidth(), getHeight());

        // sim.step(0.1*deltaTime/1000);
        double tempstep = animationspeed.getValue();
        tempstep = tempstep / 5;
        sim.step(tempstep);

        for (int i = 0; i < objects.length; i++) {
            g.setColor(objects[i].getColor());
            g.fillOval((int) objects[i].getPosition().x - boidSize / 2, (int) objects[i].getPosition().y - boidSize / 2,
                    boidSize, boidSize);

        }
    }

    public void runSimulation() {

        while (true) {
            if (!paused) {
                deltaTime = 1000 / animationspeed.getValue();
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
