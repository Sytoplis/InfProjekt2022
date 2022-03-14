import java.awt.*;
import java.util.Hashtable;
import javax.swing.*;
import MathLib.Vector2;

//Class for the animation surface, extending JPanel
public class AnimationSurface extends JPanel {

    // global variables
    private JFrame frame;
    public static AnimationSurface instance;
    private Inputs input = new Inputs();
    private boolean paused;
    private Simulation sim;
    private Mouse mouse;
    private long deltaTime;
    private int boidSize;
    private JSlider animationspeed;
    public JSlider Slider1;
    public JSlider Slider2;
    public JSlider alignment;
    public JSlider mouseforce;
    AnimationObject[] objects;

    // contructor, creates an instance and calls the buildframe method
    public AnimationSurface(String simulationType, int objectcount) {

        super();
        instance = this;
        buildFrame(simulationType, objectcount);

    }

    // this builds the frame, depending on the simulationtype and objectcount
    public void buildFrame(String simulationType, int objectcount) {
        // Frame gets initiated and properties are set
        frame = new JFrame("Simulation: " + simulationType);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 1,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 1);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setIconImage(new ImageIcon("Inhalte/ProgramIcon.png").getImage());
        frame.addKeyListener(input);
        frame.addMouseListener(mouse);

        // JPanel properties are determined, gets added to frame
        setSize(frame.getWidth(), frame.getHeight());
        setLayout(null);
        createAnimationObjects(objectcount);// create objects
        setBackground(new Color(201, 234, 255));
        frame.add(this);

        // Simulationtype determines which frame is build (which Sliders and commands
        // are added)
        if (simulationType.equals("Boids")) {
            buildBoidFrame();
        } else if (simulationType.equals("Gravitation")) {
            buildGravitationFrame();
        }

        // as long as the frame is invisible, the Simulation is paused
        // Gets skipped, when Main class calls startAnimation
        while (!frame.isVisible()) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        // Frame is now visible, set fullscreen and show the Controls
        toggleFullscreen();
        frame.toFront();
        JOptionPane.showMessageDialog(this,
                "Press 'ESC' to end the simulation \nPress 'P' to pause the simulation \nPress 'F11' to switch fullscreen mode \nUse the sliders to control simulation parameters",
                "Controls", JOptionPane.INFORMATION_MESSAGE);
        // Now the timer is started, Simulation starts
        runSimulation();
    }

    // builds the Frame for the boid simulation
    public void buildBoidFrame() {
        // Red mark for standard value
        JLabel mark = new JLabel("I");
        mark.setForeground(Color.red);

        // calls method to create Slider for animation speed
        setAnimationSpeed(mark);

        // Label is added as title for the slider
        JLabel cohesionLabel = new JLabel();
        cohesionLabel.setText("Cohesion");
        cohesionLabel.setBounds(394, frame.getHeight() - 75, 90, 60);
        add(cohesionLabel);

        // now the slider is added, for cohesion
        Slider1 = new JSlider(JSlider.HORIZONTAL, 0, 50, 5); // Slider range is defined
        Slider1.setMajorTickSpacing(5); // Major and minor ticks set
        Slider1.setMinorTickSpacing(1);
        Slider1.setBounds(484, frame.getHeight() - 75, 250, 60); // Slider position and size set
        Hashtable cohesionTable = new Hashtable(); // Hashtable for the marks of the Slider
        cohesionTable.put(5, mark); // Standard value
        cohesionTable.put(0, new JLabel("0x")); // min value
        cohesionTable.put(50, new JLabel("10x")); // Max value
        Slider1.setLabelTable(cohesionTable); // hashtable added to slider
        Slider1.setPaintLabels(true); // Paint the labels, ticks
        Slider1.setPaintTicks(true);
        Slider1.setFocusable(false); // Slider may NOT be focusable, so keyshortcuts work properly!!!
        Slider1.setBackground(new Color(181, 214, 235)); // make Sliders more obvious
        add(Slider1); // Add Slider to Panel

        JLabel seperationLabel = new JLabel();
        seperationLabel.setText("Separation");
        seperationLabel.setBounds(778, frame.getHeight() - 75, 90, 60);
        add(seperationLabel);

        // Same for SLider for seperation
        Slider2 = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        Slider2.setMajorTickSpacing(50);
        Slider2.setMinorTickSpacing(10);
        Slider2.setBounds(868, frame.getHeight() - 75, 250, 60);
        Hashtable seperationTable = new Hashtable();
        seperationTable.put(50, mark);
        seperationTable.put(0, new JLabel("0x"));
        seperationTable.put(500, new JLabel("10x"));
        Slider2.setLabelTable(seperationTable);
        Slider2.setPaintLabels(true);
        Slider2.setPaintTicks(true);
        Slider2.setFocusable(false);
        Slider2.setBackground(new Color(181, 214, 235));
        add(Slider2);

        JLabel alignmentLabel = new JLabel();
        alignmentLabel.setText("Alignment");
        alignmentLabel.setBounds(1162, frame.getHeight() - 75, 90, 60);
        add(alignmentLabel);

        // Same for Slider for alignment
        alignment = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        alignment.setMajorTickSpacing(10);
        alignment.setMinorTickSpacing(2);
        alignment.setBounds(1252, frame.getHeight() - 75, 250, 60);
        Hashtable alignmentTable = new Hashtable();
        alignmentTable.put(10, mark);
        alignmentTable.put(0, new JLabel("0x"));
        alignmentTable.put(100, new JLabel("10x"));
        alignment.setLabelTable(alignmentTable);
        alignment.setPaintLabels(true);
        alignment.setPaintTicks(true);
        alignment.setFocusable(false);
        alignment.setBackground(new Color(181, 214, 235));
        add(alignment);

        JLabel mouseLabel = new JLabel();
        mouseLabel.setText("Mouseforce");
        mouseLabel.setBounds(1546, frame.getHeight() - 75, 90, 60);
        add(mouseLabel);

        // Same for Slider for mouseforce
        mouseforce = new JSlider(JSlider.HORIZONTAL, 0, 5000, 500);
        mouseforce.setMajorTickSpacing(500);
        mouseforce.setMinorTickSpacing(100);
        mouseforce.setBounds(1636, frame.getHeight() - 75, 250, 60);
        Hashtable mouseforceTable = new Hashtable();
        mouseforceTable.put(500, mark);
        mouseforceTable.put(0, new JLabel("0x"));
        mouseforceTable.put(5000, new JLabel("10x"));
        mouseforce.setLabelTable(mouseforceTable);
        mouseforce.setPaintLabels(true);
        mouseforce.setPaintTicks(true);
        mouseforce.setFocusable(false);
        mouseforce.setBackground(new Color(181, 214, 235));
        add(mouseforce);

    }

    // Builds the gravitationframe, by adidng a SLider for speed, the mass and
    // radius
    // refer to GravSim
    // Principle same as for BoidFrame

    public void buildGravitationFrame() {
        JLabel mark = new JLabel("I");
        mark.setForeground(Color.red);

        setAnimationSpeed(mark);

        JLabel massLabel = new JLabel();
        massLabel.setText("Mass");
        massLabel.setBounds(394, frame.getHeight() - 75, 90, 60);
        add(massLabel);

        Slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        Slider1.setMajorTickSpacing(10);
        Slider1.setMinorTickSpacing(2);
        Slider1.setBounds(484, frame.getHeight() - 75, 250, 60);
        Hashtable massTable = new Hashtable();
        massTable.put(10, mark);
        massTable.put(0, new JLabel("0x"));
        massTable.put(100, new JLabel("10x"));
        Slider1.setLabelTable(massTable);
        Slider1.setPaintLabels(true);
        Slider1.setPaintTicks(true);
        Slider1.setFocusable(false);
        Slider1.setBackground(new Color(181, 214, 235));
        add(Slider1);

        JLabel radiusLabel = new JLabel();
        radiusLabel.setText("Radius");
        radiusLabel.setBounds(778, frame.getHeight() - 75, 90, 60);
        add(radiusLabel);

        Slider2 = new JSlider(JSlider.HORIZONTAL, 0, 8000, 800);
        Slider2.setMajorTickSpacing(800);
        Slider2.setMinorTickSpacing(160);
        Slider2.setBounds(868, frame.getHeight() - 75, 250, 60);
        Hashtable radiusTable = new Hashtable();
        radiusTable.put(800, mark);
        radiusTable.put(0, new JLabel("0x"));
        radiusTable.put(8000, new JLabel("10x"));
        Slider2.setLabelTable(radiusTable);
        Slider2.setPaintLabels(true);
        Slider2.setPaintTicks(true);
        Slider2.setFocusable(false);
        Slider2.setBackground(new Color(181, 214, 235));
        add(Slider2);

    }

    // seperate method for the Slider of the animationspeed
    public void setAnimationSpeed(JLabel mark) {
        JLabel animationLabel = new JLabel();
        animationLabel.setHorizontalAlignment(JLabel.CENTER);
        animationLabel.setText("Speed");
        animationLabel.setBounds(10, frame.getHeight() - 75, 90, 60);
        add(animationLabel);

        animationspeed = new JSlider(JSlider.HORIZONTAL, 0, 80, 20);
        animationspeed.setMajorTickSpacing(20);
        animationspeed.setMinorTickSpacing(5);
        animationspeed.setBounds(100, frame.getHeight() - 75, 250, 60);
        Hashtable animspeedTable = new Hashtable();
        animspeedTable.put(20, mark);
        animspeedTable.put(0, new JLabel("0x"));
        animspeedTable.put(animationspeed.getMaximum(), new JLabel("4x"));
        animationspeed.setLabelTable(animspeedTable);
        animationspeed.setPaintLabels(true);
        animationspeed.setPaintTicks(true);
        animationspeed.setFocusable(false);
        animationspeed.setBackground(new Color(181, 214, 235));
        add(animationspeed);

    }

    // responsible for creating the animated objects
    public void createAnimationObjects(int objectcount) {

        java.util.Random rand = new java.util.Random();
        objects = new AnimationObject[objectcount];

        // USing a random, startingposition and color are determined.
        // Velocitsy gets set in BoidSim
        for (int i = 0; i < objectcount; i++) {
            objects[i] = new AnimationObject();
            objects[i].setPosition(new Vector2(rand.nextInt((int) getWidth()),
                    rand.nextInt((int) getHeight())));
            objects[i].setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            objects[i].setVelocity(new Vector2(0, 0));
        }

        // determines the size of the boids in correlation to the amount of them
        if (objectcount <= 100000)
            boidSize = 3;
        if (objectcount <= 10000)
            boidSize = 5;
        if (objectcount <= 1000)
            boidSize = 10;
        if (objectcount <= 100)
            boidSize = 20;
        if (objectcount <= 100)
            boidSize = 25;
        if (objectcount <= 50)
            boidSize = 30;
        if (objectcount < 10)
            boidSize = 35;
        deltaTime = 10;

        // Determines the typew of simulation based on the Windowtitle, initiates the
        // simulation
        // mouselistener and keylistener are added
        if (frame.getTitle().contains("Boids"))
            sim = new BoidSim(objects, frame.getWidth(), frame.getHeight(), 8);
        if (frame.getTitle().contains("Gravitation"))
            sim = new GravSim(objects, frame.getWidth(), frame.getHeight(), 8);
        mouse = new Mouse(sim);
        frame.addMouseListener(mouse);

    }

    // Responsible for painting the objects in an Override
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        // movement and calculations in correlation to the animationspeed
        double tempstep = animationspeed.getValue();
        tempstep = tempstep / 100;
        sim.step(tempstep);

        // each object is drawn individually by accessing position and color
        for (int i = 0; i < objects.length; i++) {
            g.setColor(objects[i].getColor());
            g.fillOval((int) objects[i].getPosition().x - boidSize / 2, (int) objects[i].getPosition().y - boidSize / 2,
                    boidSize, boidSize);
        }
    }

    // Responsible for running the simulation
    public void runSimulation() {

        // infinte loop
        while (true) {
            if (!paused) {
                // Uses the value of the JSlider to determine the delta time
                if (animationspeed.getValue() > 0)
                    deltaTime = 60 / animationspeed.getValue();
                else // Since dividing by zero would give an error
                    deltaTime = 800;
                // Repaints the frame and its objects (updates paint)
                frame.repaint();
                try {
                    Thread.sleep(deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // when paused, the thread sleep in 20ms intervals, checking again wether paused
                // is true or not
            } else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // Starts the simulation by making the frame visible
    public void startSimulation() {
        frame.setVisible(true);
    }

    // ends the simulation by disposing the screen end exiting the machine
    public void endSimulation() {
        frame.dispose();
        System.exit(0);
    }

    // Uses Graphics Driver to toggle fullscreen when called
    public void toggleFullscreen() {

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = env.getDefaultScreenDevice();

        if (frame.getWidth() == (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
            dev.setFullScreenWindow(null);
        } else {
            dev.setFullScreenWindow(frame);
        }
    }

    // Pauses the simulation when called
    public void pauseSimulation() {
        paused = !paused;
    }

}
