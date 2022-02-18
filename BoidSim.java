import MathLib.MathLib;
import MathLib.Vector2;

public class BoidSim extends Simulation{

    public class Boid extends SimOJ{
        public Boid(AnimationObject animOJ){
            super(animOJ);
            setPos(new Vector2(MathLib.rnd(width), MathLib.rnd(height)));//set boid to random position on screen
            setDir(new Vector2(MathLib.rnd(-1, 1), MathLib.rnd(-1, 1)).normalized());//give boid a random direction
        }
    }

    public BoidSim(AnimationObject[] initAnim){
        super(initAnim);
    }
}
