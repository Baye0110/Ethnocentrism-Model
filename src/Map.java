
public class Map {
    private int numRow;
    private int numCol;
    private Agent[][] mapElements;

    public Map(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;
        mapElements = new Agent[numRow][numCol];
    }

    public void setElement(int row, int col, Agent agent){
        mapElements[row][col] = agent;
    }

    public boolean isEmpty(int row, int col) {
        if (mapElements[row][col] == null) {
            return true;
        }
        return false;
    }

    public Agent getElement(int row, int col) {
        return mapElements[row][col];
    }
}
