# MineSweeper
Правила игры:

Игровое поле разделено на смежные ячейки (квадраты), некоторые из которых «заминированы».

Цель игры — разминировать поле, то есть открыть все ячейки без мин.

Если открыта ячейка с миной, игра считается проигранной. Мины расставляются в случайном порядке.

Если под открытой ячейкой мины нет, в ней появляется число, показывающее, сколько соседствующих с открытой ячейкой "заминировано". Используя эти числа, можно рассчитать расположение мин.

Если под соседними ячейками тоже нет мин, открывается некоторая "незаминированная" область до ячеек, в которых есть цифры.

"Заминированные" ячейки можно помечать флагом, чтобы случайно не открыть их. Количество флагов равно количеству мин на игровом поле.

Если все "незаминированные" ячейки открыты, игра считается выигранной.



# Игра MineSweeper реализована на движке javarush.engine.cell

1 - Класс MineSweeperGame наследуется от класса Game, который встроен в движок engine.cell.

2 - В классе MineSweeperGame заложена логика самой игры.

3 - В классе GameObject инициализированы объекты для данной игры.

4 - Класс MineSweeperGame наследуется от класса GameObject.


* Игра выложена на портал javarush, ссылка на игру: https://javarush.com.ua/projects/apps/307321
* Условия игры: Разминировать игровое поле не нарвавшись на мину
* За каждую открытую ячейку начисляются очки. Если при открытии ячейки вы попали на мину, сработает метод gameOver(); и появится сообщение "Gameover"
* При успешном разминировании всех мин, сработает метод win(); и игра завершится с общим подсчетом очков
* Для перезапуска игры не нужно обновлять страницу, добавлен метод restart(); который обнуляет все действия, очки и поле для минера. Чтобы перезапустить игру, нужно счелкнуть левым кликом мыши за пределы поля. Условие выполняется только в том случае, если был вызван какой-либо из методов: gameOver(); или win();
