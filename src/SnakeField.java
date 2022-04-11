import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeField extends JPanel implements ActionListener, MouseListener {
    private final int SIZE = 280;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private final int DELAY = 250;
    private Image dot;
    private Image apple;
    private int aX;
    private int aY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int snakeSize;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int score = 0;

    public SnakeField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new keyFieldListener());
        setFocusable(true);
    }

    public void initGame() {
        snakeSize = 3;
        for (int i = 0; i < snakeSize; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(DELAY, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        aX = new Random().nextInt(20)*DOT_SIZE;
        aY = new Random().nextInt(20)*DOT_SIZE;


    }

    public void loadImages() {
        ImageIcon apple = new ImageIcon("src/apple_png.png");
        this.apple = apple.getImage();
        ImageIcon circle = new ImageIcon("src/circle.png");
        dot = circle.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, aX, aY, this);
            for (int i = 0; i < snakeSize; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 250, 300);
        } else {
            g.setColor(Color.WHITE);
            //g.setFont(new Font("Arial", 60, Font.BOLD));
            g.drawString("Game Over", 125, SIZE/2 - 40);
            g.drawString("Score: " + score, 125, SIZE/2 - 28);

//            g.drawRect(110, 180, 100, 40);
//            g.drawString("New Game", 125, 205);

            add(new JButton("New Game"))
                    .addMouseListener(this);


            JButton newGameButton = new JButton("New Game");

            newGameButton.setPreferredSize(new Dimension(100, 50));
            newGameButton.setLocation(200, 150);
            newGameButton.addMouseListener(this);

        }
    }

    public void newGameClick(ActionEvent e) {
        initGame();
    }

    public void move() {
        for (int i = snakeSize; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == aX && y[0] == aY) {
            snakeSize++;
            score++;
            createApple();
        }
    }

    public void checkBorders() {
        for (int i = snakeSize; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkBorders();
            move();

        }
        repaint();
    }

    class keyFieldListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        initGame();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class newGameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            initGame();
        }
    }
}