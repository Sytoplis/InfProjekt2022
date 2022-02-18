import MathLib.Vector2;

public class BoidSim extends Simulation{

    public class Boid extends SimOJ{
        public Boid(AnimationObject animOJ){
            super(animOJ);
        }
        
        @Override public void step(double dt){
            keepWithinBounds(dt);
            super.step(dt);
        }

        private void keepWithinBounds(double dt){
            final double margin = 200;
            final double turnFactor = 1;

            Vector2 v = getVel();
            if(getPos().x < margin)         v.x += turnFactor;
            if(getPos().x > width - margin) v.x -= turnFactor;
            if(getPos().y < margin)         v.y += turnFactor;
            if(getPos().y > height - margin)v.y -= turnFactor;
            setVel(v);
        }
    }

    @Override public Simulation.SimOJ createSimOJ(AnimationObject animOJ) { return new Boid(animOJ); }//use boid as the standard simoj
    public BoidSim(AnimationObject[] initAnim){
        super(initAnim);
    }
}
