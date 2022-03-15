//IMport necessary imports
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//Main class
public class Main {

    private static Thread loading;
    private static int objectcount = 0;

    //Main method
    public static void main(String[] args) {

        //Define an Array of type Object to choose the simulationtype in an Optiondialog 
        Object[] choices1 = { "Boids", "Gravitation" };
        String simulationType = (String) choices1[(JOptionPane.showOptionDialog(null,
                "Please select a simulation!",
                "Simulation", 0, JOptionPane.QUESTION_MESSAGE, null, choices1,
                choices1[0]))];

        //Create a new temporary (invisible) parent frame
        JFrame temp = new JFrame();
        temp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Call a method, creating the window for the objectount input
        getobjectcount(temp); 
        //While objectcount has not been assigned a porper value, stay in this loop and do not continue
        while (objectcount == 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                loading.interrupt();
            }
        }

        //Create a new thread, responsible for the loading animation
        loading = new Thread(new Runnable() {
            @Override
            public void run() {
                loadingScreen();
            }
        });
        //New thread loading is started, Animationsurface is called, starting to initalize the simulation parameters
        loading.start();
        new AnimationSurface(simulationType, objectcount);

        //parent frame tmep is disposed after 4 seconds to avoid errors in window transition
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            loading.interrupt();
        }
        temp.dispose();

    }
    //This creates the loading screen
    public static void loadingScreen() {

        //A new Jframe with its properties is defined
        JFrame loadingframe = new JFrame("");
        loadingframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadingframe.setLocationRelativeTo(null);
        loadingframe.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 400,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 300, 800, 600);
        loadingframe.setResizable(false);
        loadingframe.setUndecorated(true);
        loadingframe.setOpacity(0.8f);

        //New Loadingscreen is created (named "component"), and added to the Frame
        LoadingScreen component = new LoadingScreen();
        loadingframe.add(component);
        loadingframe.getContentPane().validate();
        loadingframe.getContentPane().repaint();
        loadingframe.setVisible(true);

        //When the animation has been shown for at least 4.8 seconds, it starts the simulation and disposed itself
            //unless interrupt has been called
        try {
            Thread.sleep(4800);
        } catch (InterruptedException e) {
            e.printStackTrace();
            loading.interrupt();
        }

        AnimationSurface.instance.startSimulation();
        loadingframe.dispose();

    }

    //this defines the objectcount window as a JDialog with all its properties
    public static void getobjectcount(JFrame temp) {

        JDialog chooseobjectcount = new JDialog(temp, "Objects");
        chooseobjectcount.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 200,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 150, 400, 260);
        chooseobjectcount.setMinimumSize(chooseobjectcount.getSize());
        chooseobjectcount.setMaximumSize(chooseobjectcount.getSize());
        chooseobjectcount.setLayout(null);
        chooseobjectcount.setResizable(false);
        chooseobjectcount.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel description = new JLabel();
        description.setText("Please choose a number of objects!");
        description.setBounds(chooseobjectcount.getWidth() / 2 - 110, 15, 220, 20);
        description.setForeground(Color.black);
        description.setBackground(Color.gray);
        description.setVisible(true);
        chooseobjectcount.add(description);

        JLabel descriptionextrange = new JLabel();
        descriptionextrange.setText("You can also define a new range:");
        descriptionextrange.setBounds(60, chooseobjectcount.getHeight() / 2 - 40,
                240, 20);
        descriptionextrange.setForeground(Color.black);
        descriptionextrange.setBackground(Color.gray);
        descriptionextrange.setVisible(true);
        chooseobjectcount.add(descriptionextrange);

        //This is the label for the selected value, starting point is 100
        JLabel selectedvalue = new JLabel("100");
        selectedvalue.setBounds(chooseobjectcount.getWidth() / 2 - 50, chooseobjectcount.getHeight() / 2 - 10, 100, 30);
        selectedvalue.setVisible(true);
        selectedvalue.setHorizontalAlignment(SwingConstants.CENTER);
        selectedvalue.setVerticalAlignment(SwingConstants.CENTER);
        selectedvalue.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),
                BorderFactory.createLoweredBevelBorder()));
        chooseobjectcount.add(selectedvalue);

        //This slied lets you choose a value and automatically sets the set value
        JSlider movecount = new JSlider(SwingConstants.HORIZONTAL, 1, 1000, 100);
        movecount.setMajorTickSpacing(200);
        movecount.setMinorTickSpacing(10);
        movecount.setBounds(30, chooseobjectcount.getHeight() / 2 - 90, chooseobjectcount.getWidth() - 70, 50);
        movecount.setPaintTicks(true);
        movecount.setPaintLabels(true);
        chooseobjectcount.add(movecount);
        movecount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                selectedvalue.setText(String.valueOf(movecount.getValue()));
            }
        });

        movecount.setLabelTable(creatHashtable(movecount));

        //Input for a new range, in the beginning set to 1000
        JTextField rangeinput = new JTextField();
        rangeinput.setBounds(260, chooseobjectcount.getHeight() / 2 - 40, 70, 20);
        rangeinput.setVisible(true);
        rangeinput.setText("1000");
        rangeinput.setToolTipText(
                "Please input a number from 1 to 1000000 (Onehundredthousand). \n Press 'enter' to confirm!");
        chooseobjectcount.add(rangeinput);

        //Button sets the global objectcount to the value selected with the slider and then disposes the Dialog 
        JButton select = new JButton("Confirm");
        select.setBounds(chooseobjectcount.getWidth() / 2 - 50, chooseobjectcount.getHeight() - 100, 100, 50);
        select.setBackground(Color.white);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                objectcount = movecount.getValue();
                chooseobjectcount.dispose();
            }
        });
        select.setVisible(true);
        chooseobjectcount.add(select);

        //Adds a keylistenes to the frame
        //This keylistener checks, after a key has been pressed, that the numbers are in a valid range
        //If not, a message is displayed and the value is reset
        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent evt) {
                // no Event
            }

            @Override
            public void keyTyped(KeyEvent evt) {
                // no Event
            }

            @Override
            public void keyReleased(KeyEvent evt) {
                try {
                    String sze = rangeinput.getText();
                    if (Integer.valueOf(sze) < 1) {
                        rangeinput.setText("1000");
                        JOptionPane.showMessageDialog(null, "Values below 1 are forbidden!", "Invalid entry",
                                JOptionPane.ERROR_MESSAGE);
                        movecount.setMaximum(1000);
                        movecount.setLabelTable(creatHashtable(movecount));

                    }
                    if (Integer.valueOf(sze) > 100000) {
                        rangeinput.setText("1000");
                        JOptionPane.showMessageDialog(null, "Values over 100000 are forbidden!",
                                "Invalid entry", JOptionPane.ERROR_MESSAGE);
                        movecount.setMaximum(1000);
                        movecount.setLabelTable(creatHashtable(movecount));

                    }
                    sze = rangeinput.getText();
                    if (Integer.valueOf(sze) > 0 && Integer.valueOf(sze) <= 100000) {
                        movecount.setMaximum(Integer.valueOf(sze));
                        movecount.setLabelTable(creatHashtable(movecount));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        rangeinput.addKeyListener(keyListener);
        chooseobjectcount.setVisible(true);

    }


    //This method is used to automatically update the hastable displaying the new values, when the range has been redefined
    public static Hashtable creatHashtable(JSlider slider) {

        Hashtable labelTable = new Hashtable();
        labelTable.put(slider.getMaximum() / 2,
                new JLabel(String.valueOf(slider.getMaximum() / 2)));
        labelTable.put(1, new JLabel("1"));
        labelTable.put(slider.getMaximum(), new JLabel(String.valueOf(slider.getMaximum())));

        return labelTable;
    }

}
