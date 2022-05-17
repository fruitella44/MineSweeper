package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private com.javarush.games.minesweeper.GameObject[][] gameField = new com.javarush.games.minesweeper.GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new com.javarush.games.minesweeper.GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);

            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }

    private List<com.javarush.games.minesweeper.GameObject> getNeighbors(com.javarush.games.minesweeper.GameObject gameObject) {
        List<com.javarush.games.minesweeper.GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.x + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[x][y]);
            }
        }
        return result;
    }

    private void countMineNeighbors() {
        for (int y= 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {

                com.javarush.games.minesweeper.GameObject gameObject = gameField[y][x];
                if (!gameObject.isMine) {
                    for (com.javarush.games.minesweeper.GameObject neighbor : getNeighbors(gameObject)) {
                        if (neighbor.isMine) {
                            gameObject.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private void openTile(int x, int y) {
        com.javarush.games.minesweeper.GameObject gameObject = gameField[y][x];
        if (gameObject.isMine) {
            setCellValue(gameObject.x, gameObject.y, MINE);
        } else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }
        gameObject.isOpen = true;
        setCellColor(x, y, Color.GREEN);
    }

    @Override
    public void onMouseLeftClick(int x , int y) {
        openTile(x, y);
    }
}