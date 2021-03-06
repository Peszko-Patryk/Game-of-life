public class ThreadCounting extends Thread {


    private final int[][] gameField;
    private final int[][] result;
    private final int[] colNumber;
    private final int[] rowNumber;


    public ThreadCounting(int[][] gameField, int[] colNumber, int[] rowNumber) {
        this.gameField = gameField;
        this.colNumber = colNumber;
        this.rowNumber = rowNumber;
        result = new int[colNumber.length][colNumber.length];
    }

    @Override
    public void run() {
        for (int i = 0; i < colNumber.length;i++) {
            for (int j = 0; j < rowNumber.length; j++) {
                int sum = getNeighboursSum(rowNumber[j], colNumber[i]);
                if (gameField[rowNumber[j]][colNumber[i]] == 1) {
                    if (sum == 2 || sum == 3) {
                        result[j][i] = 1;
                    } else {
                        result[j][i] = 0;
                    }
                } else if (sum == 3) {
                    result[j][i] = 1;
                } else {
                    result[j][i] = 0;
                }
            }
        }

    }

    private int getNeighboursSum(int i, int j) {
        int prevRow = i == 0 ? gameField.length - 1 : i - 1;
        int nextRow = i == gameField.length - 1 ? 0 : i + 1;
        int prevCol = j == 0 ? gameField[0].length - 1 : j - 1;
        int nextCol = j == gameField[0].length - 1 ? 0 : j + 1;
        return gameField[prevRow][prevCol] + gameField[prevRow][j] + gameField[prevRow][nextCol] + gameField[i][prevCol]
                + gameField[i][nextCol] + gameField[nextRow][prevCol] + gameField[nextRow][j]
                + gameField[nextRow][nextCol];
    }

    public int[][] getResult() {
        return result;
    }

    public int[] getColNumber() {
        return colNumber;
    }

    public int[] getRowNumber() {
        return rowNumber;
    }
}
