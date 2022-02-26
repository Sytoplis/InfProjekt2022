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

    private Thread[] threads;
    private int threadOJCount;
    private double last_dt;

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
            getVel().clamp(-maxSpeed, maxSpeed);

            getPos().x += getVel().x * dt;//new pos = old pos + vel * dt
            getPos().y += getVel().y * dt;//new pos = old pos + vel * dt

            getPos().clamp(boundMin, boundMax);//clamp position into screensize
        }

        //--------------------- SPACIAL HASHING --------------------------------------------
        int gridID = 0;
        public void RecomputeGridID(){ gridID = (int)(getPos().x*invCellSize.x) + (int)(getPos().y*invCellSize.y)*grid.getWidth(); }//  pos / cellsize = gridPos;    posX + posY * width = index
        public int getGridX() { return gridID % grid.getWidth(); }
        
    }

    public SimOJ[] simOJs;

    public SimOJ createSimOJ(AnimationObject animOJ, int id){ return new SimOJ(animOJ, id, 5); }

    //CONSTRUCTOR
    public Simulation(AnimationObject[] initAnim, double width, double height, int threadCount){
        size = new Vector2(width, height);

        boundMin = new Vector2(boundOffset, boundOffset);
        boundMax = new Vector2(size).sub(boundMin);
        
        simOJs = new SimOJ[initAnim.length];
        for(int i = 0; i < initAnim.length; i++){//loads all animation objects into the simulation
            simOJs[i] = createSimOJ(initAnim[i], i);
        }

        grid = new Grid(10, 10, simOJs);
        gridCellSize = new Vector2(width / grid.getWidth(), height / grid.getHeight());
        System.out.println("Cell Size: " + gridCellSize);
        invCellSize = new Vector2(1 / gridCellSize.x, 1 / gridCellSize.y);
        grid.UpdateGrid(simOJs);


        //THREADS:
        threadOJCount = simOJs.length / threadCount;
        threads = new Thread[threadCount];
        int i = 0;
        for(int t = 0; t < threadCount; t++){
            int end = i+threadCount;
            if(t == threadCount-1) end = simOJs.length;

            threads[t] = new Thread(new Runnable() {
                int start;
                int end;

                public Runnable setRange(int start, int end){ 
                    this.start = start; 
                    this.end = end;
                    return this; 
                }

                @Override public void run() { stepPartial(last_dt, start, end); }

            }.setRange(i, end));
            System.out.println(i + " " + end);
            i += threadCount;
        }
    }
    
    public void step(double dt){//dt in seconds
        
        for(int t = 0; t < threads.length; t++){
            threads[t].start();
        }
        try{
            for(int t = 0; t < threads.length; t++)
                threads[t].join(0);//make all threads end again
        }catch(Exception e){}

        grid.UpdateGrid(simOJs);
    }

    private void stepPartial(double dt, int start, int end){
        for(int i = start; i < end; i++){
            simOJs[i].step(dt);
        }
        for(int i = start; i < end; i++){
            simOJs[i].applyStep(dt);
        }
    }
}