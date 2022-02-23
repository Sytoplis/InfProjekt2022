import MathLib.Vector2;

public class BoidSim extends Simulation{

    private double cohesionRad;
    private double cohesionStrength;
    private double seperationRad;//usually smaller than cohesion rad
    private double seperationStrength;//usually greater than cohesion strength

    public class Boid extends SimOJ{
        public Boid(AnimationObject animOJ){
            super(animOJ);
        }
        
        @Override public void step(double dt){
            keepWithinBounds(dt);
            ForceToAvrg(dt, cohesionRad, cohesionStrength);//cohesion force
            ForceToAvrg(dt, seperationRad, seperationStrength);//seperation force
            //TODO: alignment
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

        //make boids steer to avrg of surrounding boids     (runs for all boids in O(n²))
        private void ForceToAvrg(double dt, double rad, double strength){
            Vector2 pos = getPos();

            Vector2 avrgPos = Vector2.zero;
            int count = 0;
            for(int i = 0; i < simOJs.length; i++){
                if(simOJs[i].getPos().sqrDist(pos) <= rad*rad){//if boid is in rad
                    avrgPos.add(simOJs[i].getPos());
                    count++;
                }
            }
            avrgPos.mul(1.0/count);//divide sum by count to get avrg
            avrgPos.sub(pos);//make avrage position relative to current boid position
            avrgPos.mul(dt*strength);//scale avrgPos to become a force

            setVel(getVel().add(avrgPos));
        }
    }

    @Override public Simulation.SimOJ createSimOJ(AnimationObject animOJ) { return new Boid(animOJ); }//use boid as the standard simoj
    public BoidSim(AnimationObject[] initAnim, double width, double height){
        super(initAnim, width, height);
    }
}
