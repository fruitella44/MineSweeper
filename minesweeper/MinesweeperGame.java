package minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    //поле для сапера
    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                    isGameStopped = false;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);

            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
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
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void countMineNeighbors() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {

                GameObject gameObject = gameField[y][x];
                if (!gameObject.isMine) {
                    for (GameObject neighbor : getNeighbors(gameObject)) {
                        if (neighbor.isMine) {
                            gameObject.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];
        ;

        //Если открыт объект и установлен флаг и игра остановлена, ничего не делаем
        if (gameObject.isOpen || gameObject.isFlag || isGameStopped) {
            return;
        }
        gameObject.isOpen = true;
        setCellColor(x, y, Color.GREEN);
        //Если мина, то устанавливаем цвет яйчеки крсный цвет и добавляем рисунок мины
        if (gameObject.isMine) {
            setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
            gameOver();
        } else if (gameObject.countMineNeighbors == 0) {
            setCellValue(gameObject.x, gameObject.y, "");
            List<GameObject> neighbors = getNeighbors(gameObject);
            for (GameObject blank : neighbors) {
                if (!blank.isOpen) {
                    openTile(blank.x, blank.y);
                }
            }
        } else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }

    }

    //Открытие яйчеки левой кнопкой мыши
    @Override
    public void onMouseLeftClick(int x , int y) {
        openTile(x, y);
    }

    //маркировка флагом
    private void markTile(int x, int y) {
        GameObject gameObject = gameField[y][x];

        if (isGameStopped) {
            return;
        }
        else if (gameObject.isOpen) {
            return;
        } else if (countFlags == 0 && !gameObject.isFlag) {
            return;
        } else if (gameObject.isFlag) {
            gameObject.isFlag = false;
            countFlags++;
            setCellValue(gameObject.x, gameObject.y, "");
            setCellColor(gameObject.x, gameObject.y, Color.ORANGE);
        } else {
            gameObject.isFlag = true;
            countFlags--;
            setCellValue(gameObject.x, gameObject.y, FLAG);
            setCellColor(gameObject.x, gameObject.y, Color.YELLOW);
        }
    }

    //флаг на правом клике мыши
    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }

    //игра окончена, сообщение Gameover
    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "Gameover", Color.BLACK, 30);
    }

}