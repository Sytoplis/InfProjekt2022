import MathLib.Vector2;
import MathLib.MathLib;

public class Simulation{

    public double width;
    public double height;

    public class SimOJ{

        //---------------------- ANIMATION INTERFACE ---------------------------------
        public AnimationObject animOJ;

        public Vector2 getPos() { return animOJ.getPosition(); }
        public void setPos(Vector2 value) { animOJ.setPosition(value); }

        public Vector2 getDir() { return animOJ.getDirection(); }
        public void setDir(Vector2 value) { animOJ.setDirection(value); }

        public SimOJ(AnimationObject animOJ){
            this.animOJ = animOJ;
            setPos(new Vector2(MathLib.rnd(width), MathLib.rnd(height)));//set boid to random position on screen
            setDir(new Vector2(MathLib.rnd(-1, 1), MathLib.rnd(-1, 1)).normalized());//give boid a random direction
        }


        //--------------------- MOVEMENT --------------------------------------------
        private Vector2 vel;
        public Vector2 getVel() { return vel; }
        public void setVel(Vector2 vel) { 
            this.vel = vel; 
            setDir(vel.normalized());//update the direction, so it is always facing in the movement direction
        }

        public void step(double dt){//step this boid one timestep forward
            setPos(getPos().add(vel.mul(dt)));//new pos = old pos + vel * dt
        }
    }

    public SimOJ[] simOJs;

    public SimOJ createSimOJ(AnimationObject animOJ){ return new SimOJ(animOJ); }

    public Simulation(AnimationObject[] initAnim){
        for(int i = 0; i < initAnim.length; i++){//loads all animation objects into the simulation
            simOJs[i] = createSimOJ(initAnim[i]);
        }
    }


    public void step(double dt){
        for(int i = 0; i < simOJs.length; i++){
            simOJs[i].step(dt);
        }
    }
}