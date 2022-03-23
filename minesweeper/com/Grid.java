package minesweeper.com;

import javax.swing.*;
import java.util.ArrayList;

public class Grid extends JPanel {
    private int bound = Game.gridsize * Game.gridsize;
    private ArrayList<Integer> mines = new ArrayList<Integer>();
    public static ArrayList<Cell> cellGrid = new ArrayList<Cell>();
    private boolean picked = false;

    public Grid() {
        createCells();
        addCells();
        addCells();
    }
    public void createCells() {
        for (int i = 0; i <= Game.minecount ; i++) {

            while(!picked) {
                int minesPosition = (int) (Math.random() * bound);
                if (!mines.contains(minesPosition)) {
                    mines.add(minesPosition);
                    picked = true;
                }
            }
            picked = false;
        }
        for (int i = 0; i < bound; i++) {
            if (mines.contains(i)) {
                cellGrid.add(new Cell(1, i, false, false));
            } else if (i % Game.gridsize == 0) {
                if (mines.contains(i - Game.gridsize - 1)) {
                    mines.contains(i - Game.gridsize);
                    mines.contains(i + Game.gridsize);
                    mines.contains(i + Game.gridsize + 1);
                    mines.contains(i + Game.gridsize - 1);
                    mines.contains(i + 1);
                    mines.contains(i - 1);
                    cellGrid.add(new Cell(2, i, false, false));
                } else {
                    cellGrid.add(new Cell(0, i, false, false));
                }
            }
            else {
                if (mines.contains(i - Game.gridsize - 1)) {
                    mines.contains(i - Game.gridsize);
                            mines.contains(i + Game.gridsize);
                            mines.contains(i + Game.gridsize + 1);
                            mines.contains(i + Game.gridsize - 1);
                            mines.contains(i + 1);
                            mines.contains(i - 1);
                    cellGrid.add(new Cell(2, i, false, false));
                } else {
                    cellGrid.add(new Cell(0, i, false, false));
                }
            }
        }
    }

    public void addCells() {

    }

}
