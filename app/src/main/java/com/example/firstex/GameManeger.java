package com.example.firstex;


import java.util.Arrays;
import java.util.Random;

public class GameManeger {

    private int rowSize;
    private int colSize;
    private int life;
    boolean[][] hammerMatrix;
    boolean[] dwarfArray;
    boolean[] heartArray;
    private int currentVisibleDwarf = 1;

    public GameManeger(int life, int rowSize, int colSize) {
        this.life = life;
        this.rowSize = rowSize;
        this.colSize = colSize;
        init();
        updateFirstRow();
    }


    public int getColSize() {
        return colSize;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean[][] getHammerMatrix() {
        return hammerMatrix;
    }

    public int getCurrentVisibleDwarf() {
        return currentVisibleDwarf;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setCurrentVisibleDwarf(int currentVisibleDwarf) {
        this.currentVisibleDwarf = currentVisibleDwarf;
    }

    public void updateMatrix() {
        for (int i = rowSize - 1; i > 0; i--) {
            for (int j = 0; j < colSize; j++) {
                hammerMatrix[i][j] = hammerMatrix[i - 1][j];
            }
        }
        updateFirstRow();
    }

    public void moveDwarf(int number) {
        int currentPlace = getCurrentVisibleDwarf();
        if (currentPlace + number <= 2 && currentPlace + number >= 0) {
            for (int i = 0; i < dwarfArray.length; i++) {
                if (i == currentVisibleDwarf + number) {
                    dwarfArray[i] = true;
                } else {
                    dwarfArray[i] = false;
                }
            }
            setCurrentVisibleDwarf(currentPlace + number);
        }
    }

    public void updateFirstRow() {
        int selectedCol = randomNumber();
        for (int i = 0; i < colSize; i++) {
            if (selectedCol == i) {
                hammerMatrix[0][i] = true;
            } else {
                hammerMatrix[0][i] = false;
            }
        }
    }

    public boolean checkCollition() {
        for (int i = 0; i < getColSize(); i++) {
            if (hammerMatrix[getRowSize() - 1][i] && dwarfArray[i]) {
                setLife(Math.max(getLife() - 1, 0));
                return true;
                //vibretion
            }
        }
        return false;
    }

    private void init() {
        hammerMatrix = new boolean[getRowSize()][getColSize()];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColSize(); j++) {
                hammerMatrix[i][j] = false;
            }
        }

        dwarfArray = new boolean[getColSize()];
        for (int i = 0; i < dwarfArray.length; i++) {
            if (i != currentVisibleDwarf) {
                dwarfArray[i] = false;
            } else {
                dwarfArray[i] = true;
            }
        }

        heartArray = new boolean[life];
        Arrays.fill(heartArray, true);
    }

    private int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(3);
    }
}
