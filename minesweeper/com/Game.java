package minesweeper.com;

public class Game {
    public static final int width = 720;
    public static final int height = 720;
    public static final int gridsize = 10;
    public static final int minecount =10;

    public Game() {
        new Window(this.width, this.height, this.gridsize, "MineSweeper", this);
    }

    public static void main(String[] args) {
        new Game();
    }
}
