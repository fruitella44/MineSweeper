package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

public class MinesweeperGame extends Game {

   private static final int SIDE = 30;
   private GameObject[][] gameField = new GameObject[SIDE][SIDE];

   private void createGame() {
       for (int i = 0; i < gameField.length; i++) {
           for (int j = 0; j < gameField[i].length; j++) {
               gameField[j][i] = new GameObject(i, j);
               setCellColor(j, i, Color.DARKGREY);
           }
       }
   }
   
   public void initialize() {
       setScreenSize(SIDE, SIDE);
       createGame();
   }

}
