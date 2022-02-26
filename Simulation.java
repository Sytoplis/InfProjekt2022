import MathLib.Vector2;
import MathLib.MathLib;

public class Simulation{

    public Vector2 size;

    private double boundOffset = 0.01;
    public Vector2 boundMin;
    public Vector2 boundMax;

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

        public SimOJ(AnimationObject animOJ, int id, double initSpeed){
            this.animOJ = animOJ;
            this.id = id;

            setPos(new Vector2(MathLib.rnd(size.x), MathLib.rnd(size.y)));//set boid to random position on screen
            setVel(new Vector2(MathLib.rnd(-1, 1), MathLib.rnd(-1, 1)).normalize().mul(initSpeed));//give boid a random direction
        }


        //--------------------- MOVEMENT --------------------------------------------
        public Vector2 getVel() { return animOJ.getVelocity(); }
        public void setVel(Vector2 vel) { animOJ.setVelocity(vel); }

        public void step(double dt){//step this boid one timestep forward
        }

        public void applyStep(double dt){//update position based on velocity
            getVel().ClipDiagLength(maxSpeed);

            getVel().mul(dt);
            getPos().add(getVel());//new pos = old pos + vel * dt
            getVel().mul(1.0/dt);//change velocity back to normal value (better than creating a new scaled object)

            getPos().clamp(boundMin, boundMax);//clamp position into screensize
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

        boundMin = Vector2.one.mul(boundOffset);
        boundMax = size.sub(boundMin);
        
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