public class InitGame {
    
    AnimationSurface surface;
    int objectcount;

    public InitGame(String simulationType, int objectcount) {

        this.objectcount = objectcount;
        surface = new AnimationSurface(simulationType, objectcount);

        while (true) {
            surface.frame.repaint();
        }
    }

}
