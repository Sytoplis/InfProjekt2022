public class BoidSim extends Simulation{

    public class Boid extends SimOJ{
        public Boid(AnimationObject animOJ){
            super(animOJ);
        }
    }

    @Override public Simulation.SimOJ createSimOJ(AnimationObject animOJ) { return new Boid(animOJ); }//use boid as the standard simoj
    public BoidSim(AnimationObject[] initAnim){
        super(initAnim);
    }
}
