package minesweeper.com;

import javax.swing.*;

public class Cell extends JButton {
    private int type;
    private int position;
    private boolean discovered;
    private boolean flag;

    public Cell(int type, int position, boolean discovered, boolean flag) {
        this.type = type;
        this.position = position;
        this.discovered = discovered;
        this.flag = flag;
    }

    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean mine) {
        this.discovered = mine;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag (boolean flag) {
        this.flag = flag;
    }
}
