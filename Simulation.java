import MathLib.Vector2;
import MathLib.MathLib;

public class Simulation{

    public Vector2 size;
    public double maxSpeed = 15;

    public Grid grid;
    public Vector2 gridCellSize;
    private Vector2 invCellSize;

    public class SimOJ{

        //---------------------- ANIMATION INTERFACE ---------------------------------
        public AnimationObject animOJ;
        public int id;

        public Vector2 getPos() { return animOJ.getPosition(); }
        public void setPos(Vector2 value) { animOJ.setPosition(value); }

        public Vector2 getDir() { return animOJ.getDirection(); }
        public void setDir(Vector2 value) { animOJ.setDirection(value); }

        public SimOJ(AnimationObject animOJ, int id, double initSpeed){
            this.animOJ = animOJ;
            this.id = id;

            setPos(new Vector2(MathLib.rnd(size.x), MathLib.rnd(size.y)));//set boid to random position on screen
            setVel(new Vector2(MathLib.rnd(-1, 1), MathLib.rnd(-1, 1)).normalized().mul(initSpeed));//give boid a random direction
        }


        //--------------------- MOVEMENT --------------------------------------------
        private Vector2 vel;
        public Vector2 getVel() { return vel; }
        public void setVel(Vector2 vel) { 
            this.vel = vel; 
            setDir(vel.normalized());//update the direction, so it is always facing in the movement direction
        }

        public void step(double dt){//step this boid one timestep forward
        }

        public void applyStep(double dt){//update position based on velocity
            vel.ClipDiagLength(maxSpeed);
            setPos(getPos().add(vel.mul(dt)));//new pos = old pos + vel * dt
            setPos(getPos().clamp(Vector2.zero, size));//clamp position into screensize
        }

        //--------------------- SPACIAL HASHING --------------------------------------------
        int gridID = 0;
        public void RecomputeGridID(){ gridID = (int)(getPos().x*invCellSize.x) + (int)(getPos().y*invCellSize.y)*grid.getWidth(); }//  pos / cellsize = gridPos;    posX + posY * width = index
        public int getGridX() { return gridID % grid.getWidth(); }
        
    }

    public SimOJ[] simOJs;

    public SimOJ createSimOJ(AnimationObject animOJ, int id){ return new SimOJ(animOJ, id, 0); }

    //CONSTRUCTOR
    public Simulation(AnimationObject[] initAnim, double width, double height){
        size = new Vector2(width, height);

        simOJs = new SimOJ[initAnim.length];
        for(int i = 0; i < initAnim.length; i++){//loads all animation objects into the simulation
            simOJs[i] = createSimOJ(initAnim[i], i);
        }

        grid = new Grid(10, 10, simOJs);
        gridCellSize = new Vector2(width / grid.getWidth(), height / grid.getHeight());
        System.out.println("Cell Size: " + gridCellSize);
        invCellSize = new Vector2(1 / gridCellSize.x, 1 / gridCellSize.y);
        grid.UpdateGrid(simOJs);
    }


    public void step(double dt){//dt in seconds
        for(int i = 0; i < simOJs.length; i++){
            simOJs[i].step(dt);
        }
        for(int i = 0; i < simOJs.length; i++){
            simOJs[i].applyStep(dt);
        }
        grid.UpdateGrid(simOJs);
    }
}