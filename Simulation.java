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
        }

        public void applyStep(double dt){//update position based on velocity
            if(keepInBound)
                keepWithinBounds(dt);
            

            getVel().clamp(-maxSpeed, maxSpeed);//fast square clamp
            //getVel().clamp(maxSpeed);//slow circle clamp

            getPos().x += getVel().x * dt;//new pos = old pos + vel * dt
            getPos().y += getVel().y * dt;//new pos = old pos + vel * dt

            if(keepInBound)
                getPos().clamp(boundMin, boundMax);//clamp position into screensize
            else
                ModuloPosition();
        }

        private void keepWithinBounds(double dt) {
            Vector2 v = getVel();
            
             if(getPos().x < margin) v.x += turnFactor * dt;
             if(getPos().x > size.x - margin)v.x -= turnFactor * dt;
             if(getPos().y < margin) v.y += turnFactor * dt;
             if(getPos().y > size.y - margin)v.y -= turnFactor * dt;
             
            /*
            if (getPos().x < margin)
                v.x *= -1;
            if (getPos().x > size.x - margin)
                v.x *= -1;
            if (getPos().y < margin)
                v.y *= -1;
            if (getPos().y > size.y - margin)
                v.y *= -1;*/
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
        grid.UpdateGrud(simOjs);
    }
    */
    
    public void step(double dt){//dt in seconds
        
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

        
        //CREATE THE SECOND THREADS (apply step)
        i = 0;
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
                        simOJs[i].applyStep(dt);
                    }
                }

            }.setRange(i, end));
            threads[t].start();
            i += threadOJs;
        }

        //END THE THREADS (again)
        try{
            for(int t = 0; t < threadCount; t++)
                threads[t].join(0);//make all threads end again
        }catch(Exception e){}
    }

    //TODO: save positions in seperate array to not need to use multiple threads



    //----------------------- MOUSE -------------------------
    public void onMouseClick(Vector2 pos){}
    public void onMousePressed(Vector2 pos){}
    public void onMouseReleased(Vector2 pos){}
}