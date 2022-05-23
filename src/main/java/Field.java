import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Field extends JPanel {


    private int[][] gameField;
    private final int rowNumber;
    private final int colNumber;
    private final int cellSize;
    private long time;
    private static final int DELAY = 500;


    public Field(int rowNumber, int colNumber, int cellSize) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.cellSize = cellSize;
        gameField = new int[rowNumber][colNumber];
        Random random = new Random();
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                gameField[i][j] = random.nextInt(2);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (System.currentTimeMillis() - time > DELAY) {
            time = System.currentTimeMillis();
            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < colNumber; j++) {
                    if (gameField[i][j] == 1) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(Color.black);
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
            nextIteration();
        }
        repaint();
    }

    private void nextIteration() {
        ArrayList<ThreadCounting> threadList = new ArrayList<>();
        for (int i = 0; i < colNumber; i++) {
            threadList.add(new ThreadCounting(gameField, i));
            threadList.get(i).start();
        }
        int[][] newField = new int[rowNumber][colNumber];
        for (ThreadCounting thread : threadList) {
            try {
                thread.join();
                joinTable(thread.getColNumber(), thread.getResult(), newField);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gameField = newField;
    }

    private void joinTable(int colNum, int[] results, int[][] field) {
        for (int i = 0; i < rowNumber; i++) {
            field[i][colNum] = results[i];
        }
    }
}