import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class FlappyBirdGame extends JPanel implements ActionListener, KeyListener {
    private int birdY = 200; // Initial bird Y position
    private int birdVelocity = 0; // Vertical velocity of the bird
    private int gravity = 1; // Gravity value
    private int gap = 200; // Gap between the pipes
    private int pipeWidth = 100;
    private int pipeHeight = 300;
    private int pipeX = 400;
    private Timer timer;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    
    private int score = 0; // Score variable

    private Image birdImage;

    public FlappyBirdGame() {
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(20, this);
        timer.start();

        ImageIcon birdIcon = new ImageIcon("C:\\Users\\91991\\OneDrive\\Pictures\\bird.png");
        birdImage = birdIcon.getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameOver) {
            // Draw background
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw bird
            g.drawImage(birdImage, 100, birdY, 50, 50, this);

            // Draw pipes
            g.setColor(Color.green);
            g.fillRect(pipeX, 0, pipeWidth, getHeight() - pipeHeight - gap);
            g.fillRect(pipeX, getHeight() - pipeHeight, pipeWidth, pipeHeight);

            // Draw score
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, 20, 30);
        } else {
            // Display game over message and final score
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 300, 300);
            
            g.setColor(Color.blue);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Score: " + score, 400, 350);
        }
    }

    
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver) {
            // Bird physics
            birdVelocity += gravity;
            birdY += birdVelocity;

            // Move the pipes
            pipeX -= 5;

            // Check for collision
            if (birdY < 0 || birdY + 50 > getHeight() || (birdY + 50 > getHeight() - pipeHeight && (pipeX < 150 && pipeX + pipeWidth > 100)) || birdY < 0 || (birdY < getHeight() - pipeHeight - gap && (pipeX < 150 && pipeX + pipeWidth > 100))) {
                gameOver = true;
            }
            


            // Check if bird passed through the pipe
            if (pipeX + pipeWidth < 100) {
                pipeX = getWidth();
                gameOver = false;
                score++; // Increment the score when the bird passes through the pipe
            }

            repaint();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameStarted) {
                gameStarted = true;
            }
            if (!gameOver) {
                birdVelocity = -10; // Jump by reducing the Y velocity
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird Game");
        FlappyBirdGame game = new FlappyBirdGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
