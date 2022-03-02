public class Gravitation extends Simulation{

    public class GravityOJ extends SimOJ{

        public GravityOJ(AnimationObject animOJ, int id) {
            super(animOJ, id, 5);// initSpeed 5
        }

    }

    @Override
    public Simulation.SimOJ createSimOJ(AnimationObject animOJ, int id) {
        return new GravityOJ(animOJ, id);
    }// use GravityOJ as the standard simoj

    public Gravitation(AnimationObject[] initAnim, double width, double height, int threadCount) {
        super(initAnim, width, height, threadCount);
    }
    
}
