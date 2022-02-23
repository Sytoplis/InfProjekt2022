import MathLib.Vector2;

public class BoidSim extends Simulation{

    private double cohesionRad;

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
            final double turnFactor = 1 * dt;

            Vector2 v = getVel();
            if(getPos().x < margin)         v.x += turnFactor;
            if(getPos().x > width - margin) v.x -= turnFactor;
            if(getPos().y < margin)         v.y += turnFactor;
            if(getPos().y > height - margin)v.y -= turnFactor;
            setVel(v);
        }

        private void Cohesion(){//make boids steer to avrg of surrounding boids
            
        }
    }

    @Override public Simulation.SimOJ createSimOJ(AnimationObject animOJ) { return new Boid(animOJ); }//use boid as the standard simoj
    public BoidSim(AnimationObject[] initAnim, double width, double height){
        super(initAnim, width, height);
    }
}
