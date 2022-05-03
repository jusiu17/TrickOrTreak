/**
 *      Class for the trick or treat game by saving game with row, col and number of candy.
 */

package cmpt276.as3.trickortreat.model;

import java.util.Random;

public class TrickOrTreat {

    private int gridRow;
    private int gridColumn;
    private int numOfCandy;
    private int numOfFound;
    private int numOfScan;
    private int[] candyPosition;
    private int totalCell;


    public TrickOrTreat(int candy, int row, int col){
        this.gridRow = row;
        this.gridColumn = col;
        this.totalCell = row*col;
        this.numOfCandy = candy;
        this.numOfScan = 0;
        this.numOfFound = 0;
        this.candyPosition = randomPosition();

    }

    public int getGridRow() {
        return gridRow;
    }
    public void setGridRow(int gridRow) {
        this.gridRow = gridRow;
    }

    public int getGridColumn() {
        return gridColumn;
    }
    public void setGridColumn(int gridColumn) {
        this.gridColumn = gridColumn;
    }

    public int getNumOfCandy() {
        return numOfCandy;
    }
    public void setNumOfCandy(int numOfCandy) {
        this.numOfCandy = numOfCandy;
    }

    public int getNumOfFound() {
        return numOfFound;
    }
    public void addNumOfFound() {
        numOfFound++;
    }

    public int getNumOfScan() {
        return numOfScan;
    }
    public void addNumOfScan() {
        numOfScan++;
    }

    public int[] getCandyPosition() {
        return candyPosition;
    }
    public void setCandyPosition(int[] candyPosition) {
        this.candyPosition = candyPosition;
    }

    public int getTotalCell() {
        return totalCell;
    }
    public void setTotalCell(int totalCell) {
        this.totalCell = totalCell;
    }

    @Override
    public String toString(){
        return "Used scan:" + numOfScan
                + " to found " + numOfCandy + "candy!!";
    }

    //  generate the position that candy sit on
    private int[] randomPosition() {
        int[] position = new int[numOfCandy];
        Random rand = new Random();
        int num;
        boolean check;
        for(int i = 0 ; i < numOfCandy ; i++ )
        {
            do {
                check = true;
                num = rand.nextInt(totalCell);
                for(int j = 0 ; j < i ; j++)
                {
                    if(position[j] == num)
                        check = false;
                }
            }while(!check);
            position[i] = num;
        }
        return position;
    }

}


