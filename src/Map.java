// Building 2D map to save Agents
public class Map {
    // The number of row
    private int numRow;
    // The number of Column
    private int numCol;
    // 2D Array for Agent's Map
    private Agent[][] mapElements;

    // Constructor
    public Map(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;
        mapElements = new Agent[numRow][numCol];
    }

    // Set Agent with x and y coordinate
    public void setElement(int row, int col, Agent agent){
        mapElements[row][col] = agent;
    }

    // Check whether the coordinate occupid by a Agent
    public boolean isEmpty(int row, int col) {
        if (mapElements[row][col] == null) {
            return true;
        }
        return false;
    }

    // Get the Agent with X and Y coordinate
    public Agent getElement(int row, int col) {
        return mapElements[row][col];
    }

    // Clear all Agent in Map/ 2D Array
    public void clearAllElements() {
        mapElements = new Agent[numRow][numCol];
    }
}
