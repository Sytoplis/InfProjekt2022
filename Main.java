import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import javax.swing.JButton;

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
            Thread.sleep(4800);
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
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 150, 400, 300);
        chooseobjectcount.setMinimumSize(chooseobjectcount.getSize());
        chooseobjectcount.setMaximumSize(chooseobjectcount.getSize());
        chooseobjectcount.setLayout(null);
        chooseobjectcount.setResizable(false);
        chooseobjectcount.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel description = new JLabel();
        description.setText("Bitte Anzahl der Objekte eingeben!");
        description.setBounds(chooseobjectcount.getWidth() / 2 - 100, 20, 200, 20);
        description.setForeground(Color.black);
        description.setBackground(Color.gray);
        description.setVisible(true);
        chooseobjectcount.add(description);

        JLabel descriptionextrange = new JLabel();
        descriptionextrange.setText("Alternativ einen neuen Bereich festelegen:");
        descriptionextrange.setBounds(chooseobjectcount.getWidth() / 2 - 120, chooseobjectcount.getHeight() / 2 - 50,
                240, 20);
        descriptionextrange.setForeground(Color.black);
        descriptionextrange.setBackground(Color.gray);
        descriptionextrange.setVisible(true);
        chooseobjectcount.add(descriptionextrange);

        JSlider movecount = new JSlider(JSlider.HORIZONTAL, 0, 1000, 100);
        movecount.setMajorTickSpacing(200);
        movecount.setMinorTickSpacing(10);
        movecount.setBounds(30, chooseobjectcount.getHeight() / 2 - 100, chooseobjectcount.getWidth() - 70, 50);
        movecount.setPaintTicks(true);
        movecount.setPaintLabels(true);
        chooseobjectcount.add(movecount);

        JTextField rangeinput = new JTextField();
        rangeinput.setBounds(chooseobjectcount.getWidth() / 2 - 100, chooseobjectcount.getHeight() / 2, 200, 20);
        rangeinput.setVisible(true);
        rangeinput.setToolTipText(
                "Hier eine Zahl von 1 bis 100000 (Einhundertausend) eingeben. \n Zum Bestätigen Enter drücken!");
        rangeinput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String sze = rangeinput.getText();
                    movecount.setMaximum(Integer.valueOf(sze));
                    movecount.setMajorTickSpacing(Integer.valueOf(sze) / (Integer.valueOf(sze) / 3));
                    movecount.setMinorTickSpacing(Integer.valueOf(sze) / 100);
                    movecount.setPaintTicks(false);
                    movecount.setPaintLabels(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chooseobjectcount.add(rangeinput);

        JButton select = new JButton("Bestätigen");
        select.setBounds(chooseobjectcount.getWidth() / 2 - 50, chooseobjectcount.getHeight() - 100, 100, 50);
        select.setBackground(Color.gray);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                objectcount = movecount.getValue();
                chooseobjectcount.dispose();
            }
        });
        select.setVisible(true);
        chooseobjectcount.add(select);

        chooseobjectcount.setVisible(true);

    }

}
