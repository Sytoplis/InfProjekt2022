import MathLib.Vector2;
import MathLib.MathLib;


public class Simulation{

    public String test = "";
    public SimOJ[] simOJs;

    public Vector2 size;

    private double boundOffset = 0.01;
    public Vector2 boundMin;
    public Vector2 boundMax;

    public double maxSpeed = 15;

    private int threadCount;
    private int threadOJs;

    //keep ojs from leaving screen
    protected final double margin = 100;
    private final double turnFactor = 5;

    private boolean keepInBound = false;

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
            if(keepInBound)
            mirrorBounds();
        
            //getVel().clamp(-maxSpeed, maxSpeed);//square clamp
            getVel().clamp(maxSpeed);//circle clamp (using fastInvSqrt)
        }

        public void applyStep(double dt){//update position based on velocity
            getPos().x += getVel().x * dt;//new pos = old pos + vel * dt
            getPos().y += getVel().y * dt;//new pos = old pos + vel * dt

            if(keepInBound)
                getPos().clamp(boundMin, boundMax);//clamp position into screensize
            else
                ModuloPosition();
        }

        private void smoothWithinBounds(double dt) {
            Vector2 v = getVel();
            
             if(getPos().x < margin) v.x += turnFactor * dt;
             if(getPos().x > size.x - margin)v.x -= turnFactor * dt;
             if(getPos().y < margin) v.y += turnFactor * dt;
             if(getPos().y > size.y - margin)v.y -= turnFactor * dt;
        } 

        private void mirrorBounds(){//only works thogether with the screenclamp
            Vector2 v = getVel();

            if (getPos().x < 0)      v.x *= -1;
            if (getPos().x > size.x) v.x *= -1;
            if (getPos().y < 0)      v.y *= -1;
            if (getPos().y > size.y) v.y *= -1;
        }

        private void ModuloPosition(){
            getPos().add(size);
            getPos().x %= size.x;
            getPos().y %= size.y;
        }
        
    }

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

        this.threadCount = threadCount;
        threadOJs = simOJs.length / threadCount;
    }

    /*
    public void step(double dt){//the step method without threading
        for(int i = start; i < end; i++){
            simOJs[i].step(dt);
        }
        for(int i = start; i < end; i++){
            simOJs[i].applyStep(dt);
        }
    }
    */
    
    //----------------DEBUG-----------------
    protected Debug.Timer timer = new Debug.Timer(4);
    protected Debug.Timer.AvrgTimes avrgTimes = new Debug.Timer.AvrgTimes(4);


    //NOTE: sample computer results: no threads, 50000 boids -> 142 ms;     8 threads, 50000 boids -> 55 ms


    public void step(double dt){//dt in seconds

        timer.restartTimer();//DEBUG

        //CREATE THE THREADS
        Thread[] threads = new Thread[threadCount];
        int i = 0;
        for(int t = 0; t < threadCount; t++){
            int end = i+threadOJs;
            if(t == threadCount-1) end = simOJs.length;

            threads[t] = new Thread(new Runnable() {
                int start;
                int end;

                public Runnable setRange(int start, int end){ 
                    this.start = start; 
                    this.end = end;
                    return this; 
                }

                @Override public void run() {//THE EXECUTION OF THESE THREADS
                    for(int i = start; i < end; i++){
                        simOJs[i].step(dt);
                    }
                }

            }.setRange(i, end));
            threads[t].start();
            i += threadOJs;
        }

        //END THE THREADS
        try{
            for(int t = 0; t < threadCount; t++)
                threads[t].join(0);//make all threads end again
        }catch(Exception e){}

        timer.saveInterval();//DEBUG

        //APPLY STEP
        for(i = 0; i < simOJs.length; i++){//because applyStep takes almost no calculation power and performs in O(1) it is not calculated threaded
            simOJs[i].applyStep(dt);
        }
        
        timer.saveInterval();//DEBUG
        avrgTimes.AddResults(timer.getResults());//DEBUG
    }



    //----------------------- MOUSE -------------------------
    public void onMouseClick(Vector2 pos){
        System.out.println(avrgTimes.getResults());//DEBUG
    }
    public void onMousePressed(Vector2 pos){}
    public void onMouseReleased(Vector2 pos){}
}