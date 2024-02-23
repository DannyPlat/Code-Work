import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int boarderWidth;
    int boarderHeight;
    int tileSize = 25;

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;

    //sets velocity
    int velocityX;
    int velocityY;

    //gameover boolean (true = gameover)
    boolean gameOver = false;

    SnakeGame(int boarderWidth, int boarderHeight){
        this.boarderWidth = boarderWidth;
        this.boarderHeight = boarderHeight;
        setPreferredSize(new Dimension(this.boarderWidth, this.boarderHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        //sets spawn position
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        //places food randomly
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        //starting velocity is zero
        velocityX = 0;
        velocityY =0;
        
        //refreshes every 100ms (go lower for harder gameplay)
        gameLoop = new Timer(100, this);
        gameLoop.start();

    }

    //paint component, call on this to paint things
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    //graphics method, draws everything with command g --> paint component
    public void draw (Graphics g){

        for(int i = 0; i < boarderWidth/tileSize; i++){
            //grid
            //g.setColor(Color.darkGray);
            //g.drawLine(i*tileSize, 0, i*tileSize, boarderHeight);
            //g.drawLine(0, i*tileSize, boarderWidth, i*tileSize);
            
        }
        //food
        g.setColor(Color.red);
        //fills the rectangle of the food on variable x and y, so that they can be randomy positioned
        //g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        //snake head
        g.setColor(Color.green);
        //fills the rectangle of the snake on variable x and y so that it can be moved
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //snakebody
        for(int i = 0; i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //score and gameover
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("GAME OVER! \nYour Score:" + String.valueOf(snakeBody.size()), tileSize-16 , tileSize*2);
            if(snakeBody.size()<=5)
                g.drawString("You suck bro", tileSize-16, tileSize*3);
            else if(snakeBody.size()>=25)
                g.drawString("Stand proud, you are strong!", tileSize-16, tileSize*3);
            
        } 
        else
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);

    }
    public void placeFood(){

        //randomly positions food around the tiled map
        food.x = random.nextInt(boarderWidth/tileSize);
        food.y = random.nextInt(boarderHeight/tileSize);
    }

    //collision mechanics (if array is equal to other array, they must be on same tile, therefore collision)
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y ==tile2.y;
    }

    public void move(){
        //eats food, and places new food when eaten
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snakeHead - moves because i add the velocity by a factor of
        //tile, to determine the speed in tiles/s
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for(int i = 0; i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakeHead,snakePart))
                gameOver = true;
        }
        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boarderWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize >boarderHeight)
            gameOver = true;
            
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver)
            gameLoop.stop();

    }
   
    @Override
    public void keyPressed(KeyEvent e) {
        //CONTROLS
        if(e.getKeyCode() == KeyEvent.VK_W && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        if(e.getKeyCode() == KeyEvent.VK_A && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_S && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_D && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    //dont need this
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

}
