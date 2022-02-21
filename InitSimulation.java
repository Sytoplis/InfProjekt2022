public class InitSimulation {

    AnimationSurface surface;

    public InitSimulation(String simulationType, int objectcount) {

        surface = new AnimationSurface(simulationType, objectcount);

        while (true) {
            if (!surface.paused) {
                surface.frame.repaint();
                try {
                    Thread.sleep(200);
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

}
