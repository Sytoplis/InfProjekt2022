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

public class Main {

    private static Thread loading;
    private static int objectcount = 0;

    public static void main(String[] args) {

        Object[] choices1 = { "Boids", "Gravitation", "Natural Motion" };
        String simulationType = (String) choices1[(JOptionPane.showOptionDialog(null,
                "Bitte Art der Simulation auswählen!",
                "Simulation", 0, JOptionPane.QUESTION_MESSAGE, null, choices1,
                choices1[0]))];

        JFrame temp = new JFrame();
        temp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getobjectcount(temp);
        while (objectcount == 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                loading.interrupt();
            }
        }

        loading = new Thread(new Runnable() {
            @Override
            public void run() {
                loadingScreen();
            }
        });
        loading.start();
        new AnimationSurface(simulationType, objectcount);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            loading.interrupt();
        }
        temp.dispose();

    }

    public static void loadingScreen() {

        JFrame loadingframe = new JFrame("");
        loadingframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadingframe.setLocationRelativeTo(null);
        loadingframe.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 400,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 300, 800, 600);
        loadingframe.setResizable(false);
        loadingframe.setUndecorated(true);
        loadingframe.setOpacity(0.8f);

        LoadingScreen component = new LoadingScreen();
        loadingframe.add(component);
        loadingframe.getContentPane().validate();
        loadingframe.getContentPane().repaint();
        loadingframe.setVisible(true);

        try {
            Thread.sleep(48);
        } catch (InterruptedException e) {
            e.printStackTrace();
            loading.interrupt();
        }

        AnimationSurface.instance.startSimulation();
        loadingframe.dispose();

    }

    public static void getobjectcount(JFrame temp) {

        JDialog chooseobjectcount = new JDialog(temp, "Objektzahl");
        chooseobjectcount.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 200,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 150, 400, 260);
        chooseobjectcount.setMinimumSize(chooseobjectcount.getSize());
        chooseobjectcount.setMaximumSize(chooseobjectcount.getSize());
        chooseobjectcount.setLayout(null);
        chooseobjectcount.setResizable(false);
        chooseobjectcount.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel description = new JLabel();
        description.setText("Bitte Anzahl der Objekte eingeben!");
        description.setBounds(chooseobjectcount.getWidth() / 2 - 100, 15, 200, 20);
        description.setForeground(Color.black);
        description.setBackground(Color.gray);
        description.setVisible(true);
        chooseobjectcount.add(description);

        JLabel descriptionextrange = new JLabel();
        descriptionextrange.setText("Alternativ einen neuen Bereich festlegen:");
        descriptionextrange.setBounds(40, chooseobjectcount.getHeight() / 2 - 40,
                240, 20);
        descriptionextrange.setForeground(Color.black);
        descriptionextrange.setBackground(Color.gray);
        descriptionextrange.setVisible(true);
        chooseobjectcount.add(descriptionextrange);

        JLabel selectedvalue = new JLabel("100");
        selectedvalue.setBounds(chooseobjectcount.getWidth() / 2 - 50, chooseobjectcount.getHeight() / 2 - 10, 100, 30);
        selectedvalue.setVisible(true);
        selectedvalue.setHorizontalAlignment(SwingConstants.CENTER);
        selectedvalue.setVerticalAlignment(SwingConstants.CENTER);
        selectedvalue.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),
                BorderFactory.createLoweredBevelBorder()));
        chooseobjectcount.add(selectedvalue);

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

        JTextField rangeinput = new JTextField();
        rangeinput.setBounds(280, chooseobjectcount.getHeight() / 2 - 40, 70, 20);
        rangeinput.setVisible(true);
        rangeinput.setText("1000");
        rangeinput.setToolTipText(
                "Hier eine Zahl von 1 bis 100000 (Einhundertausend) eingeben. \n Zum Bestätigen Enter drücken!");
        chooseobjectcount.add(rangeinput);

        JButton select = new JButton("Bestätigen");
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
                        JOptionPane.showMessageDialog(null, "Werte unter 1 sind verboten!", "Ungültige Eingabe",
                                JOptionPane.ERROR_MESSAGE);
                        movecount.setMaximum(1000);
                        movecount.setLabelTable(creatHashtable(movecount));

                    }
                    if (Integer.valueOf(sze) > 100000) {
                        rangeinput.setText("1000");
                        JOptionPane.showMessageDialog(null, "Werte über 100000 sind verboten!",
                                "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
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

    public static Hashtable creatHashtable(JSlider slider) {

        Hashtable labelTable = new Hashtable();
        labelTable.put(slider.getMaximum() / 2,
                new JLabel(String.valueOf(slider.getMaximum() / 2)));
        labelTable.put(1, new JLabel("1"));
        labelTable.put(slider.getMaximum(), new JLabel(String.valueOf(slider.getMaximum())));

        return labelTable;
    }

}
