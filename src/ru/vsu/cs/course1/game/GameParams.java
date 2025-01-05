package ru.vsu.cs.course1.game;

/**
 * Класс для хранения параметров игры
 */
public class GameParams {

    private int fieldSize;
    private int colorCount;

    public GameParams(int fieldSize, int colorCount) {
        this.fieldSize = fieldSize;
        this.colorCount = colorCount;
    }

    public GameParams() {
        this(5, 16);
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    /**
     * @return the colorCount
     */
    public int getColorCount() {
        return colorCount;
    }

    /**
     * @param colorCount the colorCount to set
     */
    public void setColorCount(int colorCount) {
        this.colorCount = colorCount;
    }
}
