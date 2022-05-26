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
        super.setPreferredSize(new Dimension(colNumber * cellSize, rowNumber * cellSize));
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
        long time = System.currentTimeMillis();
        // columny
//        for (int i = 0; i < colNumber; i++) {
//            threadList.add(new ThreadCounting(gameField, i));
//            threadList.get(i).start();
//        }
        int rowStep = 1000;
        int threadCount = 0;
        for (int i = 0; i < colNumber - 1; i+=rowStep) {
            for (int l = 0 ; l < rowNumber-1; l++) {
                int[] rowSteps = new int[rowStep];
                int[] colSteps = new int[rowStep];
                int current = i;
                for (int j = 0; j < rowStep; j++) {
                    colSteps[j] = current++;
                }
                for (int j = 0; j < rowStep; j++) {
                    rowSteps[j] = l++;
                }
                l--;
                threadList.add(new ThreadCounting(gameField, colSteps, rowSteps));
                threadList.get(threadCount++).start();
            }
        }

        int[][] newField = new int[rowNumber][colNumber];
        for (ThreadCounting thread : threadList) {
            try {
                thread.join();
                joinTable(thread.getColNumber(), thread.getRowNumber(), thread.getResult(), newField);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Time taken = " + (System.currentTimeMillis() - time));

        gameField = newField;
    }

    private void joinTable(int[] colNum, int[] rowNum, int[][] results, int[][] field) {
        for (int j = 0; j < colNum.length; j++) {
            for (int i = 0; i < rowNum.length; i++) {
                field[rowNum[i]][colNum[j]] = results[i][j];
            }
        }
    }
}