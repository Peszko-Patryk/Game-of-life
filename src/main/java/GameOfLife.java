import javax.swing.*;

public class GameOfLife {


    private static int rowNumber;
    private static int colNumber;


    public static void main(String[] args) {
        if (!checkArgs(args)) {
            return;
        }
        Field field = new Field(rowNumber, colNumber);
        JFrame frame = new JFrame();
        frame.setSize(colNumber * 40 + 20, rowNumber * 40 + 40);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Game Of Life");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(field);
    }

    private static boolean checkArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-R":
                    rowNumber = Integer.parseInt(args[++i]);
                    break;
                case "-C":
                    colNumber = Integer.parseInt(args[++i]);
                    break;
                default:
                    System.out.println("Wrong parameters. Add -C flag to specify number of columns. Add -R flag to specify number of rows. ");
                    return false;
            }
        }
        return true;
    }
}
