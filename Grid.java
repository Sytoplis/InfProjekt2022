//import java.util.Iterator;

public class Grid {
    private int width;  public int getWidth()  {return width; }
    private int height; public int getHeight() {return height;}

    private int cellCount;  public int getCellCount() {return cellCount;}

    private int[] cellLength;
    private int[] cellStart;    public int getCellStart(int c) {return cellStart[c];}
    private int[] cellEnd;      public int getCellEnd  (int c) {return cellEnd[c];  }

    private int[] gridIndices;//length -> oj count

    public Grid(int width, int height, Simulation.SimOJ[] ojs){
        this.width = width;
        this.height = height;
        cellCount = width * height;

        cellStart = new int[cellCount];
        cellEnd = new int[cellCount];

        gridIndices = new int[ojs.length];

        //UpdateGrid(ojs);
    }

    public void UpdateGrid(Simulation.SimOJ[] ojs){//UPDATES THE ENTIRE GRID    (complexity: O(n) )

        cellLength = new int[cellCount];//reset cellLength to 0
        for(int i = 0; i < ojs.length; i++){//get the count of each cell
            ojs[i].RecomputeGridID();
            cellLength[ojs[i].gridID]++;//update cellLength
        }
        
        int ojIndex = 0;
        for(int c = 0; c < cellCount; c++){//update the cell bounds
            cellStart[c] = ojIndex;//set cell start
            cellEnd[c] = ojIndex;//make cell end pointer point to the cell start -> used to fill up the ids -> should get to correct value afterwards

            ojIndex += cellLength[c];//shift ojIndex to the next cell
        }

        for(int i = 0; i < ojs.length; i++){//update the cell id-content
            gridIndices[cellEnd[ojs[i].gridID]] = i;//write the oj-ID on the next free spot in the correct cell
            cellEnd[ojs[i].gridID]++;//shift the last free spot of the cell one back
        }
    }

    //DO NOT USE ITERATOR -> hard code it every time is as easy and faster

    //HARD CODE (c is cell index):      for(int i = grid.getCellStart(c); i < grid.getCellEnd(c); i++){ ... }

    /*
    public Iterable<Integer> getCellContent(int cell){//create an iterator to iterate over all ojs in that cell
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int i = 0;
                    @Override public boolean hasNext() { return i < cellLength[cell]; }
                    @Override public Integer next() { return gridIndices[cellStart[cell] + i]; }              
                };
            } 
        };
    }*/
    
}//NOTE: programmed ~11:30 - 13:00; 14.50 - x
