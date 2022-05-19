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
    private int countClosedTiles = SIDE * SIDE;

    //поле для сапера
    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    //подсчет мин на игровом поле
    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);

            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
        isGameStopped = false;
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

    //подсчет соседних мин
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

    //открытие ячейки, метод openTile уменьшает кол-во мин на поле при условии, если мы маркаем флагом мину
    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];
        ;
        //Если открыт объект, установлен флаг, игра остановлена, далее продолжить игру нельзя
        if (gameObject.isOpen || isGameStopped || gameObject.isFlag) {
            return;
        }
        countClosedTiles--;
        gameObject.isOpen = true;
        setCellColor(x, y, Color.GREEN);

        //Если мина, то устанавливаем цвет яйчеки красный цвет и добавляем рисунок мины
        if (gameObject.isMine) {
            setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
            gameOver();
            return;
        } else if (gameObject.countMineNeighbors == 0) {
            setCellValue(gameObject.x, gameObject.y, "");
            List<GameObject> neighbors = getNeighbors(gameObject);
            for (GameObject blank : neighbors) {
                if (!blank.isOpen) {
                    openTile(blank.x, blank.y);
                }
            }
        }  else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }

        if (countClosedTiles == countMinesOnField) {
            win();
        }

    }

    //Открытие яйчеки левой кнопкой мыши
    @Override
    public void onMouseLeftClick(int x , int y) {
        openTile(x, y);
    }

    //маркировка флагом, рисунок флага, цвет флага желтый
    private void markTile(int x, int y) {
        GameObject gameObject = gameField[y][x];

        if (gameObject.isOpen) {
            return;
        } else if (isGameStopped) {
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

    //установка флага на правом клике мыши
    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }

    //игра окончена, сообщение Gameover
    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "Gameover", Color.BLACK, 30);
    }

    //игра выйграна при условии, если на поле не остается мин + сообщение You win
    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "You win", Color.BLACK, 30);
    }

}