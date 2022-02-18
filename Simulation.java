import MathLib.Vector2;

public class Simulation{

    public class SimOJ{
        public AnimationObject animOJ;

        public Vector2 getPos() { return animOJ.getPosition(); }
        public void setPos(Vector2 value) { animOJ.setPosition(value); }

        public Vector2 getDir() { return animOJ.getDirection(); }
        public void setDir(Vector2 value) { animOJ.setDirection(value); }

        public SimOJ(AnimationObject animOJ){
            this.animOJ = animOJ;
        }
    }

    public SimOJ[] simOJs;

    public Simulation(AnimationObject[] initAnim){
        for(int i = 0; i < initAnim.length; i++){//loads all animation objects into the simulation
            simOJs[i] = new SimOJ(initAnim[i]);
        }
    }

    public void step(double dt){}
}