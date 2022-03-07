import MathLib.Vector2;

public class GridSim extends Simulation{

    public GridSim.SimOJ[] simOJs;

    public Grid grid;
    public Vector2 gridCellSize;
    protected Vector2 invCellSize;

    public class SimOJ extends Simulation.SimOJ{

        public SimOJ(AnimationObject animOJ, int id, double initSpeed) {
            super(animOJ, id, initSpeed);
        }
        
        //--------------------- SPACIAL HASHING --------------------------------------------
        int gridID = 0;
        public void RecomputeGridID(){ gridID = (int)(getPos().x*invCellSize.x) + (int)(getPos().y*invCellSize.y)*grid.getWidth(); }//  pos / cellsize = gridPos;    posX + posY * width = index
        public int getGridX() { return gridID % grid.getWidth(); }

    }

    public GridSim(AnimationObject[] initAnim, double width, double height, int threadCount) {
        super(initAnim, width, height, threadCount);

        simOJs = new SimOJ[super.simOJs.length];
        for(int i = 0; i < simOJs.length; i++) simOJs[i] = (SimOJ)super.simOJs[i];// convert to the grid SimOjs

        grid = new Grid(25, 25, simOJs);
        gridCellSize = new Vector2(width / grid.getWidth(), height / grid.getHeight());
        invCellSize = new Vector2(1 / gridCellSize.x, 1 / gridCellSize.y);
        grid.UpdateGrid(simOJs);
    }

    @Override
    public void step(double dt) {
        super.step(dt);
        grid.UpdateGrid(simOJs);
    }
    
}
