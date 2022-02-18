import java.awt.Toolkit;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulationen");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);

        Object[] choices1 = { "Broids", "Gravitation", "Natural Motion" };
        String simulationtype = (String) choices1[(JOptionPane.showOptionDialog(null,
                "Bitte Art der Simulation auswählen!",
                "Simulation", 0, JOptionPane.QUESTION_MESSAGE, null, choices1, choices1[0]))];

        Object[] choices2 = { 1, 2, 5, 10, 20, 50, 100 };
        int objectcount = (int) choices2[(JOptionPane.showOptionDialog(null, "Bitte Anzahl der Objekte auswählen!",
                "Objektzahl", 0, JOptionPane.QUESTION_MESSAGE, null, choices2, choices2[0]))];

        // loadingScreen();
        new InitGame(frame);

    }

    public static void loadingScreen() {

        Thread loading = new Thread();
        loading.start();

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
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            loading.interrupt();
        }

        loadingframe.dispose();

    }
}
