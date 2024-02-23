import javax.swing.*;
public class App {

    public static void main(String[] args) throws Exception {
       int boarderWidth = 600;
       int boarderHeight = boarderWidth;
       
       JFrame frame = new JFrame("Snake - DannyP");
       frame.setVisible(true);
       frame.setSize(boarderWidth, boarderHeight);
       frame.setLocationRelativeTo(null);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       SnakeGame snakeGame = new SnakeGame(boarderWidth, boarderHeight);
       frame.add(snakeGame);
       frame.pack();
       snakeGame.requestFocus();
    }
}
