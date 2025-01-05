package ru.vsu.cs.course1.game;

import java.util.Random;

/**
 * Класс, реализующий логику игры
 */
public class Game {

    /**
     * объект Random для генерации случайных чисел
     * (можно было бы объявить как static)
     */
    private final Random rnd = new Random();
    public int fieldSze = 16;
    public int colorCount = 5;

    private int[][] field = null;

    private boolean isMoving = false;

    private int movingCellR, movingCellC;
    private final CellDirection downDirection = new CellDirection(1, 0);
    private final CellDirection upDirection = new CellDirection(-1, 0);
    private final CellDirection leftDirection = new CellDirection(0, -1);
    private final CellDirection rightDirection = new CellDirection(0, 1);
    private CellDirection movingDirection;

    public Game(int fieldSze, int colorCount) {
        this.fieldSze = fieldSze;
        this.colorCount = colorCount;
    }

    public void newGame() {
        field = new int[fieldSze][fieldSze];
        initField();
    }

    private void initField() {
        initUpButtons();
        initRightButtons();
        initBottomButtons();
        initLeftButtons();
        initCenter();
    }

    private void initCenter() {
        for (int r = fieldSze / 4; r < fieldSze * 3 / 4; r++) {
            for (int c = fieldSze / 4; c < fieldSze * 3 / 4; c++) {
                field[r][c] = getRandom();
            }
        }
    }

    private void initLeftButtons() {
        for (int i = 1; i < fieldSze - 1; i++) {
            field[i][0] = getRandom();
        }
    }

    private void initBottomButtons() {
        for (int i = 1; i < fieldSze - 1; i++) {
            field[fieldSze - 1][i] = getRandom();
        }
    }

    private void initRightButtons() {
        for (int i = 1; i < fieldSze - 1; i++) {
            field[i][fieldSze - 1] = getRandom();
        }
    }

    private void initUpButtons() {
        for (int i = 1; i < fieldSze - 1; i++) {
            field[0][i] = getRandom();
        }
    }

    private int getRandom() {
        return rnd.nextInt(getColorCount()) + 1;
    }

    private boolean isButtonCell(int row, int col) {
        return ((row == 0 || row == fieldSze - 1) && 0 < col && col < fieldSze - 1) ||
                ((col == 0 || col == fieldSze - 1) && 0 < row && row < fieldSze - 1);
    }

    public void leftMouseClick(int row, int col) {
        if (isMoving) {
            return;
        }
        if (!isButtonCell(row, col)) {
            return;
        }

        if (row == 0 && columnHaveCell(col)) { // is top button
            movingDirection = downDirection;

        } else if (row == fieldSze - 1 && columnHaveCell(col)) {
            movingDirection = upDirection;
        } else if (col == 0 && rowHaveCell(row)) {
            movingDirection = rightDirection;
        } else if (col == fieldSze - 1 && rowHaveCell(row)) {
            movingDirection = leftDirection;
        } else {
            return;
        }

        movingCellR = row;
        movingCellC = col;
        if (isNextCellEmpty()) {
            isMoving = true;
        }


    }

    private boolean rowHaveCell(int row) {
        for (int c = 1; c < fieldSze - 1; c++) {
            if (field[row][c] > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean columnHaveCell(int col) {
        for (int r = 1; r < fieldSze - 1; r++) {
            if (field[r][col] > 0) {
                return true;
            }
        }
        return false;
    }

    public int getRowCount() {
        return field == null ? 0 : field.length;
    }

    public int getColCount() {
        return field == null ? 0 : field[0].length;
    }

    public int getColorCount() {
        return colorCount;
    }

    public int getCell(int row, int col) {
        return (row < 0 || row >= getRowCount() || col < 0 || col >= getColCount()) ? 0 : field[row][col];
    }

    public boolean isMoving() {
        return isMoving;
    }

    private boolean isNextCellEmpty() {
        return field[movingCellR + movingDirection.dr][movingCellC + movingDirection.dc] == 0;
    }

    public void nextStep() {
        if (!isNextCellEmpty()) {
            isMoving = false;
            return;
        }
        int newR = movingCellR + movingDirection.dr;
        int newC = movingCellC + movingDirection.dc;
        field[newR][newC] = field[movingCellR][movingCellC];
        if (isButtonCell(movingCellR, movingCellC)) {
            field[movingCellR][movingCellC] = getRandom();
        } else {
            field[movingCellR][movingCellC] = 0;
        }
        movingCellC = newC;
        movingCellR = newR;

        isMoving = isNextCellEmpty();
        if (!isMoving) {
            deleteStartingFrom(movingCellR, movingCellC);
        }
    }

    private void deleteStartingFrom(int row, int col) {
        int[][] copy = new int[fieldSze][fieldSze];

        // сору without buttons
        for (int r = 1; r < fieldSze - 1; r++) {
            for (int c = 1; c < fieldSze - 1; c++) {
                copy[r][c] = field[r][c];
            }
        }

        deleteRec(copy, row, col);

        if (countNeigbrows(copy) > 1) {
            for (int r = 1; r < fieldSze - 1; r++) {
                for (int c = 1; c < fieldSze - 1; c++) {
                    if (copy[r][c] == -1) {
                        field[r][c] = 0;
                    }
                }
            }
        }

    }

    private int countNeigbrows(int[][] m) {
        int cnt = 0;
        for (int[] row : m) {
            for (int cell : row) {
                if (cell == -1) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private void deleteRec(int[][] copy, int row, int col) {
        int curValue = copy[row][col];
        copy[row][col] = -1;
        if (row > 0 && copy[row - 1][col] == curValue) {
            deleteRec(copy, row - 1, col);
        }
        if (row < fieldSze - 1 && copy[row + 1][col] == curValue) {
            deleteRec(copy, row + 1, col);
        }
        if (col > 0 && copy[row][col - 1] == curValue) {
            deleteRec(copy, row, col - 1);
        }
        if (col < fieldSze - 1 && copy[row][col + 1] == curValue) {
            deleteRec(copy, row, col + 1);
        }
    }


}
