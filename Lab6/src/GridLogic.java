package Lab6;

import java.util.Arrays;
import java.util.Random;

public class GridLogic {
    private Cell[][] mainField;

    private Cell[][] nextField;

    private int width, height;

    private int[][] neighbors = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    GridLogic(int width, int height) {
        this.width = width;
        this.height = height;
        mainField = new Cell[height][width];
        nextField = new Cell[height][width];

        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++) {
                nextField[i][j] = new Cell();
                mainField[i][j] = new Cell();
            }
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    void clear() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                mainField[i][j].value = 0;
    }

    Cell getCell(int x, int y) {
        return mainField[x][y];
    }

    void simulate(int types, int start, int finish) {
        for (byte type = 1; type <= types; type++)
            for (int x = start; x < finish; x++)
                for (int y = 0; y < width; y++) {
                    nextField[x][y].lock.writeLock().lock();
                    nextField[x][y].value = simulateCell(mainField[x][y].value, nextField[x][y].value, countNeighbors(x, y, type), type);
                    nextField[x][y].lock.writeLock().unlock();
                }
    }

    void swapField() {
        mainField = nextField;
        nextField = new Cell[height][width];

        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
                nextField[i][j] = new Cell();
    }

    void generate(int civAmount) {
        Random rand = new Random();
        double density = 0.2;
        int cellAmount = (int) (width * height * density / civAmount);

        for (byte i = 1; i <= civAmount; i++)
            for (int j = 0; j < cellAmount; j++)
                mainField[rand.nextInt(width)][rand.nextInt(height)].value = i;
    }

    private byte countNeighbors(int cellX, int cellY, byte type) {
        return (byte) Arrays.stream(neighbors).filter((neighbor) -> {
            int neighborX = cellX + neighbor[0];
            int neighborY = cellY + neighbor[1];

            if (neighborX >= 0 && neighborX < height && neighborY >= 0 && neighborY < width)
                return mainField[neighborX][neighborY].value == type;
            return false;
        }).count();
    }

    private byte simulateCell(byte cell, byte inNewField, byte neighbors, byte type) {
        if (cell == type && neighbors < 2)
            return 0;
        if (cell == type && (neighbors == 2 || neighbors == 3))
            return type;
        if (cell == type && neighbors > 3)
            return 0;
        if (cell != type && neighbors == 3)
            return type;

        return inNewField;
    }
}