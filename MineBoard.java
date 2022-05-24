package com.minesweeper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class MineBoard {
    ArrayList<Location> minesList = new ArrayList<>();

    private int[][] board; //the board BTS
    private int[][] seenBoard; //seen = 2 notSeen = 0 flagged = 1
    private int mineCounter; //counts #of mines
    private int flagCounter; //counts #of flags on the current board
    protected boolean defeat;
    protected boolean discoveredBoard;

    private Texture greenSquare;
    private Texture yellowSquare;
    private Texture yellowOneSquare;
    private Texture yellowTwoSquare;
    private Texture yellowThreeSquare;
    private Texture yellowFourSquare;
    private Texture yellowFiveSquare;
    private Texture yellowSixSquare;
    private Texture yellowSevenSquare;
    private Texture yellowEightSquare;

    private Texture yellowMineSquare;
    private Texture greenFlagSquare;

    private Texture sadFaceIcon;
    private Texture happyFaceIcon;
    private Texture winFaceIcon;
    private Texture redFlag;



    public static final int GREEN = 0, YELLOW = 10, ONE = 1, TWO = 2, THREE = 3, FOUR = 4,
                            FIVE = 5, SIX = 6, SEVEN = 7, EIGHT = 8, MINE = -1;


    public MineBoard(int row, int col) {
        board = new int[row][col]; // size of the board
        seenBoard = new int[row][col];
        mineCounter = (int)(row*col/10);
        flagCounter = mineCounter;

        greenSquare =       new Texture("greenSquare.png");
        yellowSquare =      new Texture("yellowSquare.png");
        yellowOneSquare =   new Texture("yellowOneSquare.png");
        yellowTwoSquare =   new Texture("yellowTwoSquare.png");
        yellowThreeSquare = new Texture("yellowThreeSquare.png");
        yellowFourSquare =  new Texture("yellowFourSquare.png");
        yellowFiveSquare =  new Texture("yellowFiveSquare.png");
        yellowSixSquare =   new Texture("yellowSixSquare.png");
        yellowSevenSquare = new Texture("yellowSevenSquare.png");
        yellowEightSquare = new Texture("yellowEightSquare.png");

        yellowMineSquare =  new Texture("yellowMineSquare.png");
        greenFlagSquare =   new Texture("greenFlagSquare.png");

        sadFaceIcon =       new Texture("sadFaceIcon.png");
        happyFaceIcon =     new Texture("happyFaceIcon.png");
        winFaceIcon =       new Texture("winFaceIcon.png");
        redFlag =           new Texture("redFlag.png");
    }

//methods based on the MINES..
//NEXT 3 Methods

    public void createMines(Location startLoc) {
        System.out.println(mineCounter);
        for(int i = 0; i < mineCounter; i++){
            Location newMine = new Location(randomNumGen(),randomNumGen());
            //System.out.println("Started " + i + " at position " + newMine.getRow() + "," + newMine.getCol());
            minesList.add(newMine);

            boolean allMinesCleared = false;
            while (!allMinesCleared) {
                allMinesCleared = true;
                for (int j = 0; j < minesList.size(); j++) {
                    Location currentMine = minesList.get(j);
                    Location aboveMine = new Location(currentMine.getRow() - 1, currentMine.getCol());
                    Location belowMine = new Location(currentMine.getRow() + 1, currentMine.getCol());
                    Location leftMine = new Location(currentMine.getRow(), currentMine.getCol() - 1);
                    Location rightMine = new Location(currentMine.getRow(), currentMine.getCol() + 1);

                    Location aboveLeftMine = new Location(currentMine.getRow() - 1, currentMine.getCol() - 1);
                    Location aboveRightMine = new Location(currentMine.getRow() - 1, currentMine.getCol() + 1);
                    Location belowLeftMine = new Location(currentMine.getRow() + 1, currentMine.getCol() - 1);
                    Location belowRightMine = new Location(currentMine.getRow() + 1, currentMine.getCol() + 1);
                    if (((currentMine.getCol() == startLoc.getCol() && currentMine.getRow() == startLoc.getRow()) ||
                            (aboveMine.getCol() == startLoc.getCol() && aboveMine.getRow() == startLoc.getRow()) ||
                            (belowMine.getCol() == startLoc.getCol() && belowMine.getRow() == startLoc.getRow()) ||
                            (leftMine.getCol() == startLoc.getCol() && leftMine.getRow() == startLoc.getRow()) ||
                            (rightMine.getCol() == startLoc.getCol() && rightMine.getRow() == startLoc.getRow()) ||
                            (aboveLeftMine.getCol() == startLoc.getCol() && aboveLeftMine.getRow() == startLoc.getRow()) ||
                            (aboveRightMine.getCol() == startLoc.getCol() && aboveRightMine.getRow() == startLoc.getRow()) ||
                            (belowLeftMine.getCol() == startLoc.getCol() && belowLeftMine.getRow() == startLoc.getRow()) ||
                            (belowRightMine.getCol() == startLoc.getCol() && belowRightMine.getRow() == startLoc.getRow())) ||
                            (board[currentMine.getRow()][currentMine.getCol()] == -1)) {
                        allMinesCleared = false;
                        currentMine.setCol(randomNumGen());
                        currentMine.setRow(randomNumGen());
                        //System.out.println("Moved " + j + " to " + currentMine.getRow() + "," + currentMine.getCol());
                        j+=minesList.size();
                    }
                }
            }
            board[minesList.get(i).getRow()][minesList.get(i).getCol()] = -1;
            System.out.println("Completed " + i + " at position " + newMine.getRow() + "," + newMine.getCol());
        }
    }

    public void findMines() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                Location currentLoc = new Location(i,j);
                if(board[currentLoc.getRow()][currentLoc.getCol()] == -1) {

                }
                else {
                    int surrondingMines = 0;

                    Location above = new Location(currentLoc.getRow() - 1, currentLoc.getCol());
                    Location below = new Location(currentLoc.getRow() + 1, currentLoc.getCol());
                    Location left = new Location(currentLoc.getRow(), currentLoc.getCol() - 1);
                    Location right = new Location(currentLoc.getRow(), currentLoc.getCol() + 1);

                    Location aboveLeft = new Location(currentLoc.getRow() - 1, currentLoc.getCol() - 1);
                    Location aboveRight = new Location(currentLoc.getRow() - 1, currentLoc.getCol() + 1);
                    Location belowLeft = new Location(currentLoc.getRow() + 1, currentLoc.getCol() - 1);
                    Location belowRight = new Location(currentLoc.getRow() + 1, currentLoc.getCol() + 1);

                    if (isLocValid(above)) {
                        if (board[above.getRow()][above.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(below)) {
                        if (board[below.getRow()][below.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(left)) {
                        if (board[left.getRow()][left.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(right)) {
                        if (board[right.getRow()][right.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(aboveLeft)) {
                        if (board[aboveLeft.getRow()][aboveLeft.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(aboveRight)) {
                        if (board[aboveRight.getRow()][aboveRight.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(belowLeft)) {
                        if (board[belowLeft.getRow()][belowLeft.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }
                    if (isLocValid(belowRight)) {
                        if (board[belowRight.getRow()][belowRight.getCol()] == -1) {
                            surrondingMines += 1;
                        }
                    }

                    board[currentLoc.getRow()][currentLoc.getCol()] = surrondingMines;
                }
            }
        }

    }

    public void clearSpace(Location chosenLoc) {

        //base case
        if (!isLocValid(chosenLoc)) {
            return;
        }
        if(seenBoard[chosenLoc.getRow()][chosenLoc.getCol()] >= 1) {
            return;
        }
        seenBoard[chosenLoc.getRow()][chosenLoc.getCol()] = 2;
        if(board[chosenLoc.getRow()][chosenLoc.getCol()] != 0)
            return;

    board[chosenLoc.getRow()][chosenLoc.getCol()] += 10;

    Location above = new Location(chosenLoc.getRow() - 1, chosenLoc.getCol());
    clearSpace(above);
    Location below = new Location(chosenLoc.getRow() + 1, chosenLoc.getCol());
    clearSpace(below);
    Location left = new Location(chosenLoc.getRow(), chosenLoc.getCol() - 1);
    clearSpace(left);
    Location right = new Location(chosenLoc.getRow(), chosenLoc.getCol() + 1);
    clearSpace(right);

    Location aboveLeft = new Location(chosenLoc.getRow() - 1, chosenLoc.getCol() - 1);
    clearSpace(aboveLeft);
    Location aboveRight = new Location(chosenLoc.getRow() - 1, chosenLoc.getCol() + 1);
    clearSpace(aboveRight);
    Location belowLeft = new Location(chosenLoc.getRow() + 1, chosenLoc.getCol() - 1);
    clearSpace(belowLeft);
    Location belowRight = new Location(chosenLoc.getRow() + 1, chosenLoc.getCol() + 1);
    clearSpace(belowRight);
}

//methods working on the visual features
    public Texture getTextureByID(int id) {
        switch (id) {
            case -1:
                return yellowMineSquare;
            case 0:
                return greenSquare;
            case 1:
                return yellowOneSquare;
            case 2:
                return yellowTwoSquare;
            case 3:
                return yellowThreeSquare;
            case 4:
                return yellowFourSquare;
            case 5:
                return yellowFiveSquare;
            case 6:
                return yellowSixSquare;
            case 7:
                return yellowSevenSquare;
            case 8:
                return yellowEightSquare;
            case 10:
                return yellowSquare;
            default:
                return null;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                if(!discoveredBoard) {
                    if (seenBoard[row][col] == 0) {
                        spriteBatch.draw(greenSquare, col * 50, ((board.length - 1) * 50) - row * 50);
                    } else if (seenBoard[row][col] == 1) {
                        spriteBatch.draw(greenFlagSquare, col * 50, ((board.length - 1) * 50) - row * 50);
                    } else {
                        spriteBatch.draw(getTextureByID(board[row][col]), col * 50, ((board.length - 1) * 50) - row * 50);
                    }
                }
                else {
                    if (board[row][col] == -1) {
                        spriteBatch.draw(yellowMineSquare, col * 50, ((board.length - 1) * 50) - row * 50);
                    } else if (seenBoard[row][col] == 0) {
                        spriteBatch.draw(greenSquare, col * 50, ((board.length - 1) * 50) - row * 50);
                    } else if (seenBoard[row][col] == 1) {
                        spriteBatch.draw(greenFlagSquare, col * 50, ((board.length - 1) * 50) - row * 50);
                    } else {
                        spriteBatch.draw(getTextureByID(board[row][col]), col * 50, ((board.length - 1) * 50) - row * 50);
                    }
                }
            }
        }
    }

    public Texture getGreenSquare() { return greenSquare; }

    public Texture getYellowSquare() { return yellowSquare; }

    public Texture getYellowOneSquare() { return yellowOneSquare; }

    public Texture getYellowTwoSquare() { return yellowTwoSquare; }

    public Texture getYellowThreeSquare() { return yellowThreeSquare; }

    public Texture getYellowFourSquare() { return yellowFourSquare; }

    public Texture getYellowFiveSquare() { return yellowFiveSquare; }

    public Texture getYellowSixSquare() { return yellowSixSquare; }

    public Texture getYellowSevenSquare() { return yellowSevenSquare; }

    public Texture getYellowEightSquare() { return yellowEightSquare; }

    public Texture getYellowMineSquare() { return yellowMineSquare; }

    public Texture getSadFaceIcon() { return sadFaceIcon; }

    public Texture getHappyFaceIcon() { return happyFaceIcon; }

    public Texture getWinFaceIcon() { return winFaceIcon; }

    public Texture getRedFlag() { return redFlag; }

    public void setMineCounter(int mineCounter) { this.mineCounter = mineCounter; }

    public void setFlagCounter(int flagCounter) { this.flagCounter = flagCounter; }

    //methods focusing on the internal features;

    private int randomNumGen() {
        return (int)(Math.random() * board.length);
    }

    private boolean isLocValid(Location loc) {
        return loc.getRow() >= 0 && loc.getRow() < board.length && loc.getCol() >= 0 && loc.getCol() < board[0].length;
    }

    public Location mouseToBoardCoordinates(int x, int y) {
        int col = x/50;
        int row = y/50;

        return new Location(row,col);
    }

    public boolean isGameOver(Location chosenLoc) {
        if (board[chosenLoc.getRow()][chosenLoc.getCol()] == -1 && !checkIfFlagged(chosenLoc)) {
            discoveredBoard = true;
            return true;
        }
        else {
            return false;
        }
    }
/*
    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                System.out.print(seenBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

 */

    public void flipStatus(Location loc) {
        if (seenBoard[loc.getRow()][loc.getCol()] == 0)  { //not seen
            seenBoard[loc.getRow()][loc.getCol()] = 1; //clicked on to create the flag
            flagCounter--;
        }
        else if (seenBoard[loc.getRow()][loc.getCol()] == 1) {//already flagged
            seenBoard[loc.getRow()][loc.getCol()] = 0; //removing flag
            flagCounter++;
        }
    }

    public int getSeenBoardCounter() {
        int counter = 0;
        for(int i = 0; i < seenBoard.length; i++) {
            for(int j = 0; j < seenBoard.length; j++) {
                if(seenBoard[i][j] == 2) {
                    counter += seenBoard[i][j];
                }
            }
        }
        return counter;
    }

    public boolean checkAvailableFlags() {
        return flagCounter != 0;
    }

    public int getMineCounter() {
        return mineCounter;
    }

    public int getFlagCounter() {
        return flagCounter;
    }

    public boolean checkIfFlagged(Location loc) {
        return seenBoard[loc.getRow()][loc.getCol()] == 1;
    }

}
/*
In 10x10 grid, there will be 10 mines
This means there will be 90 2's needed for sure in the game since flags are not mandatory;
 */