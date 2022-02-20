public class InitGame {

    AnimationSurface surface;

    public InitGame(String simulationType, int objectcount) {

        surface = new AnimationSurface(simulationType, objectcount);

        while (true) {
            surface.frame.repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
