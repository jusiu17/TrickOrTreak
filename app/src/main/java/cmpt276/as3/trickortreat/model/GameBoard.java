/**
 *      Class for the game board  by saving the trick or treat game.
 */

package cmpt276.as3.trickortreat.model;

public class GameBoard {

    private TrickOrTreat tot;
    private int row;
    private int column;
    private boolean[][] candyLocation;
    private boolean[][] scanned;
    private int[][] count;

    /*
            Singleton Model
     */
    private static GameBoard instance;
    public static GameBoard getInstance() {
        if (instance == null){
            TrickOrTreat tot = new TrickOrTreat(6,4,6);
            instance = new GameBoard(tot);
        }
        return instance;
    }
    public static void setInstance(GameBoard game){
        instance = game;
    }

    private GameBoard(TrickOrTreat tot){
        this.tot = tot;
        this.row = tot.getGridRow();
        this.column = tot.getGridColumn();
        this.candyLocation = randomCandyLocation();
        this.scanned = clearScan();
        this.count = new int[tot.getGridRow()][tot.getGridColumn()];
        reCalCount();
    }

    /*
            Normal Getter, Setter and function
     */

    private boolean[][] randomCandyLocation() {
        boolean[][] setCandyLocation = new boolean[row][column];
        int[] position = tot.getCandyPosition();

        //
        System.out.println("\nposition");
        for(int i : position)
            System.out.print(i + " ");
        System.out.println("\nend");
        //
        for(int i=0 ; i <row ; i++)
            for(int j=0 ; j <column ; j++)
                setCandyLocation[i][j] = false;

        for (int k=0 ; k<position.length ; k++)
            for(int i=0 ; i<row ; i++)
                for(int j=0 ; j<column; j++)
                    if ( position[k] == (i*column + j) )
                        setCandyLocation[i][j] = true;

        return setCandyLocation;
    }

    private boolean[][] clearScan(){
        boolean[][] empty = new boolean[row][column];
        for(int i=0 ; i <row ; i++)
            for(int j=0 ; j <column ; j++)
                empty[i][j] = false;
        return empty;
    }

    private void reCalCount(){
        int[][] found = new int[row][column];

        for(int i=0 ; i <row ; i++)
            for(int j=0 ; j <column ; j++)
                found[i][j] = 0;

        for(int i=0 ; i<row ; i++)
            for(int j=0 ; j<column ; j++)
                if (candyLocation[i][j] == true && scanned[i][j] == false){
                    for( int ii=0 ; ii <row ; ii++)
                        found[ii][j]++;
                    for( int jj=0 ; jj<column ; jj++)
                        found[i][jj]++;
                    found[i][j]--;
                }
        this.count = found;
    }

    public TrickOrTreat getTot() {
        return tot;
    }
    public void setTot(TrickOrTreat tot) {
        this.tot = tot;
        this.row = tot.getGridRow();
        this.column = tot.getGridColumn();
        this.candyLocation = randomCandyLocation();
        this.scanned = clearScan();
        this.count = new int[tot.getGridRow()][tot.getGridColumn()];
        reCalCount();
    }

    public boolean[][] getCandyLocation() {
        return candyLocation;
    }
    public void setCandyLocation(boolean[][] candyLocation) {
        this.candyLocation = candyLocation;
    }
    public boolean isCandy(int row, int col){
        return candyLocation[row][col];
    }

    public boolean[][] getScanned() {
        return scanned;
    }
    public void setScanned(boolean[][] scanned) {
        this.scanned = scanned;
    }
    public boolean isGridScanned(int row, int col){
        return scanned[row][col];
    }
    public void scanCell(int row, int col){
        scanned[row][col] = true;
        tot.addNumOfScan();
        if(candyLocation[row][col] == true)
            tot.addNumOfFound();
        reCalCount();
    }

    public int[][] getCount() {
        return count;
    }
    public void setCount(int[][] count) {
        this.count = count;
    }
    public int getGridCount(int row, int col){
        return count[row][col];
    }

    // check is all grid scanned
    public boolean isAllCountZero(){
        for( int i=0 ; i<row ; i++)
            for( int j=0 ; j<column ; j++)
                if(count[i][j]!=0)
                    return false;
        return true;
    }

    // checking is game reach goal
    public boolean checkWin(){
        if(tot.getNumOfFound() == tot.getNumOfCandy() && isAllCountZero())
            return true;
        return false;
    }

    //  regenerate the game
    public void restart(){
        int newCandy = tot.getNumOfCandy();
        int newRow = tot.getGridRow();
        int newCol = tot.getGridColumn();
        setTot(new TrickOrTreat(newCandy, newRow, newCol));
    }

}
