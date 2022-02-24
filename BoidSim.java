import MathLib.Vector2;
import MathLib.MathLib;

public class BoidSim extends Simulation{

    private final double margin = 100;
    private final double turnFactor = 5;

    private final double cohesionRad = 150;
    private final double cohesionStrength = 0.005;
    private final double seperationRad = 20;//usually smaller than cohesion rad
    private final double seperationStrength = 0.05;//usually greater than cohesion strength
    private final double alignmentRad = cohesionRad;
    private final double alignmentStrength = 0.01;

    public class Boid extends SimOJ{
        public Boid(AnimationObject animOJ, int id){
            super(animOJ, id, 5);//initSpeed 5
            setPos(new Vector2(MathLib.rnd(width-margin), MathLib.rnd(height-margin)));//update position to not spawn in the border
        }
        
        @Override public void step(double dt){
            keepWithinBounds(dt);
            ForceToAvrgPos(dt, cohesionRad, cohesionStrength);//cohesion force
            ForceToAvrgPos(dt, seperationRad, -seperationStrength);//seperation force (negative -> repulsion)
            AvrgVel(dt, alignmentRad, alignmentStrength);//alignment force
            super.step(dt);
        }

        private void keepWithinBounds(double dt){
            Vector2 v = getVel();
            if(getPos().x < margin)         v.x += turnFactor * dt;
            if(getPos().x > width - margin) v.x -= turnFactor * dt;
            if(getPos().y < margin)         v.y += turnFactor * dt;
            if(getPos().y > height - margin)v.y -= turnFactor * dt;
            setVel(v);
        }

        //make boids steer to avrg of surrounding boids     (runs for all boids in O(n²))
        private void ForceToAvrgPos(double dt, double rad, double strength){
            Vector2 pos = getPos();

            Vector2 avrgPos = Vector2.zero;
            int count = 0;
            for(int i = 0; i < simOJs.length; i++){
                if(i == id) continue;//skip self
                if(simOJs[i].getPos().sqrDist(pos) < rad*rad){//if boid is in rad
                    avrgPos = avrgPos.add(simOJs[i].getPos());
                    count++;
                }
            }
            if(count == 0) return;//no oj -> no force

            avrgPos = avrgPos.mul(1.0/count);//divide sum by count to get avrg
            avrgPos = avrgPos.sub(pos);//make avrage position relative to current boid position
            avrgPos = avrgPos.mul(dt*strength);//scale avrgPos to become a force

            setVel(getVel().add(avrgPos));
        }
        

        private void AvrgVel(double dt, double rad, double strength){
            Vector2 pos = getPos();

            Vector2 avrgVel = Vector2.zero;
            int count = 0;
            for(int i = 0; i < simOJs.length; i++){
                if(i == id) continue;//skip self
                if(simOJs[i].getPos().sqrDist(pos) < rad*rad){//if boid is in rad
                    avrgVel = avrgVel.add(simOJs[i].getVel());
                    count++;
                }
            }
            if(count == 0) return;//no oj -> no force

            avrgVel = avrgVel.mul(1.0/count);//divide sum by count to get avrg
            avrgVel = avrgVel.mul(dt*strength);//scale avrgPos to become a force

            setVel(getVel().add(avrgVel));
        }
    }

    @Override public Simulation.SimOJ createSimOJ(AnimationObject animOJ, int id) { return new Boid(animOJ, id); }//use boid as the standard simoj
    public BoidSim(AnimationObject[] initAnim, double width, double height){
        super(initAnim, width, height);
    }
}
