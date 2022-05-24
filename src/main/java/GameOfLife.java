import javax.swing.*;
import java.awt.*;

public class GameOfLife {


    private static final int DEFAULT_CELL_NUMBER = 5;
    private static int rowNumber = DEFAULT_CELL_NUMBER;
    private static int colNumber = DEFAULT_CELL_NUMBER;


    public static void main(String[] args) {
        checkArgs(args);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int cellSize = Math.min((int) ((screenSize.getHeight() - 100) / rowNumber),
                (int) ((screenSize.getWidth() - 100) / colNumber));

        Field field = new Field(rowNumber, colNumber, cellSize);
        JFrame frame = new JFrame();

        frame.setLocationRelativeTo(null);
        frame.setTitle("Game Of Life");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(field);
        frame.pack();
        int panelX = (int) ((screenSize.getWidth() - frame.getWidth() - frame.getInsets().left - frame.getInsets().right) / 2);
        int panelY = (int) ((screenSize.getHeight() - frame.getHeight() - frame.getInsets().top - frame.getInsets().bottom) / 2);
        frame.setLocation(panelX, panelY);
    }

    private static void checkArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i].toUpperCase()) {
                case "-R":
                    int rNumber = Integer.parseInt(args[++i]);
                    if (rNumber > 0 && rNumber <= 100) {
                        rowNumber = rNumber;
                    }
                    break;
                case "-C":
                    int cNumber = Integer.parseInt(args[++i]);
                    if (cNumber > 0 && cNumber <= 100) {
                        colNumber = cNumber;
                    }
                    break;
                default:
                    System.out.println("Wrong parameters. Add -C flag to specify number of columns. Add -R flag to specify number of rows. ");
            }
        }
    }
}
