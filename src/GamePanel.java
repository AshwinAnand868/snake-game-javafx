import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * @author AshwinAnand868
 * @version 1.0
 * @email anandashwin868@gmail.com
 * @since 2024-05-23
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;

    // number of units in the game
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;

    // the more the delay the slower the snake
    static final int DELAY = 75;

    // body parts of the snake across x axis
    final int[] x = new int[GAME_UNITS];

    // body part of the snake across y axis
    final int[] y = new int[GAME_UNITS];

    // initial number of body parts
    int bodyParts = 6;

    int appleEaten;

    // apple's x axis position
    int appleX;

    // apple's y axis position
    int appleY;

    // R represents right, L for left, U for up, D for you know now
    char direction = 'R';

    boolean running = false;

    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // scale of 25
        if (running) {
            /*
                // comment out to display grid lines
                for(int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; ++i) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
            */

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }  else {
                    // g.setColor(new Color(45, 180, 0)); // for one color
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // multicoloured snake
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else {
            gameOver(g);
        }

        displayScore(g);
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // check if head touches any body part
        for(int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }

        // check if head touches left border:
        if(x[0] < 0) {
            running = false;
        }

        // check if head touches right border:
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // check if head touches top border
        if(y[0] < 0) {
            running = false;
        }

        // check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }

    public void displayScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten))/2, g.getFont().getSize());
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }
}
