import MathLib.Vector2;

public class BoidSim extends GridSim {

    private final double visualRad = 150;// used for cohesion and alignment
    private double cohesionStrength = 0.005;
    private final double seperationRad = 20;// usually smaller than cohesion rad
    private double seperationStrength = 0.05;// usually greater than cohesion strength
    private double alignmentStrength = 0.01;

    // MOUSE:
    private double mouseAvoidance = 0.5;

    public class Boid extends SimOJ {
        public Boid(AnimationObject animOJ, int id) {
            super(animOJ, id, 5);// initSpeed 5

            //setPos(new Vector2(MathLib.rnd(margin, size.x - margin), MathLib.rnd(margin, size.y - margin))); // update position to not spawn in the border
        }

        @Override
        public void step(double dt) {
            // ForceToAvrgPos(dt, visualRad, cohesionStrength);//cohesion force
            // ForceToAvrgPos(dt, seperationRad, -seperationStrength);//seperation force
            // (negative -> repulsion)
            // AvrgVel(dt, visualRad, alignmentStrength);//alignment force

            double tempcohesion = AnimationSurface.instance.Slider1.getValue();
            double tempseperation = AnimationSurface.instance.Slider2.getValue();
            double tempalignment = AnimationSurface.instance.alignment.getValue();
            double tempavoidance = AnimationSurface.instance.alignment.getValue();
            cohesionStrength = tempcohesion / 1000;
            seperationStrength = tempseperation / 1000;
            alignmentStrength = tempalignment / 1000;
            mouseAvoidance = tempavoidance / 1000;

            CompForces(dt);
            AvoidMouse(dt);

            super.step(dt);
        }

        // ---------------------------------THE SLOW, BUT EASY TO UNDERSTAND ALGORITHM
        // (runs for all boids in O(n²) [always])
        /*
         * //make boids steer to avrg of surrounding boids
         * private void ForceToAvrgPos(double dt, double rad, double strength){
         * Vector2 pos = getPos();
         * 
         * Vector2 avrgPos = Vector2.zero;
         * int count = 0;
         * for(int i = 0; i < simOJs.length; i++){
         * if(i == id) continue;//skip self
         * if(simOJs[i].getPos().sqrDist(pos) < rad*rad){//if boid is in rad
         * avrgPos = avrgPos.add(simOJs[i].getPos());
         * count++;
         * }
         * }
         * if(count == 0) return;//no oj -> no force
         * 
         * avrgPos = avrgPos.mul(1.0/count);//divide sum by count to get avrg
         * avrgPos = avrgPos.sub(pos);//make avrage position relative to current boid
         * position
         * avrgPos = avrgPos.mul(dt*strength);//scale avrgPos to become a force
         * 
         * setVel(getVel().add(avrgPos));
         * }
         * 
         * 
         * private void AvrgVel(double dt, double rad, double strength){
         * Vector2 pos = getPos();
         * 
         * Vector2 avrgVel = Vector2.zero;
         * int count = 0;
         * for(int i = 0; i < simOJs.length; i++){
         * if(i == id) continue;//skip self
         * if(simOJs[i].getPos().sqrDist(pos) < rad*rad){//if boid is in rad
         * avrgVel = avrgVel.add(simOJs[i].getVel());
         * count++;
         * }
         * }
         * if(count == 0) return;//no oj -> no force
         * 
         * avrgVel = avrgVel.mul(1.0/count);//divide sum by count to get avrg
         * avrgVel = avrgVel.mul(dt*strength);//scale avrgPos to become a force
         * 
         * setVel(getVel().add(avrgVel));
         * }
         */

        // ---------------------------------THE FAST AND COMPLICATED ALGORITHM (runs for all boids worst case: O(n²), avrg case: O(n))
        private void CompForces(double dt) {
            double sqrDist = 0;// allocate memory for sqr distance for faster usage
            Vector2 pos = getPos();

            Vector2 avrgCohPos = Vector2.zero();
            Vector2 avrgSepPos = Vector2.zero();
            Vector2 avrgVel = Vector2.zero();
            int visCount = 0;
            int sepCount = 0;
            // for(int i = 0; i < simOJs.length; i++){//the O(n²) approach
            for (int c = gridID - 1 - grid.getWidth(); c < gridID + 1 + grid.getWidth(); c += grid.getWidth())// y-axis
                for (int x = -1; x < 1; x++) {
                    c++;// x-axis
                    if (c < 0 || c >= grid.getCellCount())
                        continue;// skip out of bounds

                    for (int i = grid.getCellStart(c); i < grid.getCellEnd(c); i++) {// iterate through all boids in
                                                                                     // those cells

                        if (i == id)
                            continue;// skip self

                        sqrDist = simOJs[i].getPos().sqrDist(pos);
                        if (sqrDist < visualRad * visualRad) {// if boid is visible
                            avrgCohPos.add(simOJs[i].getPos());
                            avrgVel.add(simOJs[i].getVel());
                            visCount++;

                            if (sqrDist < seperationRad * seperationRad) {
                                avrgSepPos.add(simOJs[i].getPos());
                                sepCount++;
                            }
                        }
                    }
                }

            if (visCount != 0) {
                avrgCohPos.mul(1.0 / visCount);// divide sum by count to get avrg
                avrgCohPos.sub(pos);// make avrage position relative to current boid position
                avrgCohPos.mul(dt * cohesionStrength);// scale avrgPos to become a force

                avrgVel.mul(1.0 / visCount);// divide sum by count to get avrg
                avrgVel.mul(dt * alignmentStrength);// scale avrgPos to become a force
            }

            if (sepCount != 0) {
                avrgSepPos.mul(1.0 / sepCount);// divide sum by count to get avrg
                avrgSepPos.sub(pos);// make avrage position relative to current boid position
                avrgSepPos.mul(dt * -seperationStrength);// scale avrgPos to become a force
            }

            getVel().add(avrgCohPos).add(avrgSepPos).add(avrgVel);
        }

        private void AvoidMouse(double dt) {
            if (mouseObstacle.x == 0 && mouseObstacle.y == 0)
                return;

            if (getPos().sqrDist(mouseObstacle) <= visualRad * visualRad) {
                Vector2 mouseForce = new Vector2(mouseObstacle);
                mouseForce.sub(getPos());
                mouseForce.mul(dt * -mouseAvoidance);
                getVel().add(mouseForce);
            }
        }
    }

    @Override
    public GridSim.SimOJ createSimOJ(AnimationObject animOJ, int id) {
        return new Boid(animOJ, id);
    }// use boid as the standard simoj

    public BoidSim(AnimationObject[] initAnim, double width, double height, int threadCount) {
        super(initAnim, width, height, threadCount);
    }

    @Override
    public void step(double dt) {
        if (mouseObstacle.x != 0 || mouseObstacle.y != 0)
            mouseObstacle = Mouse.instance.getMousePosition();
        super.step(dt);
    }

    // ----------------------- MOUSE --------------------------------
    Vector2 mouseObstacle = Vector2.zero();

    @Override
    public void onMousePressed(Vector2 pos) {
        super.onMousePressed(pos);
        mouseObstacle = pos;
    }

    @Override
    public void onMouseReleased(Vector2 pos) {
        super.onMouseReleased(pos);
        mouseObstacle = Vector2.zero();
    }
}
