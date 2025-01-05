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
    public final int fieldSze = 16;
    public final int colorCount = 5;

    private int[][] field = null;

    public Game() {
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
        if (!isButtonCell(row, col)) {
            return;
        }

        int rowCount = getRowCount(), colCount = getColCount();
        if (row < 0 || row >= rowCount || col < 0 || col >= colCount) {
            return;
        }

        field[row][col] = rnd.nextInt(

                getColorCount()) + 1;
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
}
