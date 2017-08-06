import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class View extends JPanel {
    
    public static final double CAMERA_TO_PROJECTION_PLANE_DISTANCE = 800;
    
    public static final Vec3 gravity = new Vec3(0, 0.35, 0);
    
    public List<Point> points = new ArrayList<Point>();
    public List<Stick> sticks = new ArrayList<Stick>();
    public List<Ball> balls = new ArrayList<Ball>();
    
    private Point[][] grid = new Point[60][60]; // grid[z][x]
    
    public View() {
        addKeyListener(new KeyHandler());
    
        double size = 10;
        for (int z = 0; z < grid.length; z++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[z][x] = new Point(this, 300 + size * x, 0, -1500 + size * z);
                points.add(grid[z][x]);
            }
        }

        for (int z = 0; z < grid.length; z++) {
            for (int x = 0; x < grid[0].length - 1; x++) {
                Stick stick = new Stick(grid[z][x], grid[z][x + 1], true);
                sticks.add(stick);
            }
        }

        for (int z = 0; z < grid.length - 1; z++) {
            for (int x = 0; x < grid[0].length; x++) {
                Stick stick = new Stick(grid[z][x], grid[z + 1][x], true);
                sticks.add(stick);
            }
        }

        balls.add(new Ball(this, 150, 600, 300, -1200));
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
                repaint();
            }
        }, 100, 1000 / 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }
    
    public void update() {
        for (Point point : points) {
            point.update();
        }
        for (int i = 0; i < 30; i++) {
            for (Stick stick : sticks) {
                stick.update();
            }
            for (Point point : points) {
                point.updateCollision();
            }
        }
    }
    
    public void reset() {
        for (Point point : points) {
            point.reset();
        }
    }
    
    public void draw(Graphics2D g) {
//        for (Point point : points) {
//            //point.draw(g);
//            point.draw3D(g);
//        }
        for (Stick stick : sticks) {
            if (stick.isVisible()) {
                //stick.draw(g);
                stick.draw3D(g);
            }
        }
        
        for (Ball ball : balls) {
            ball.draw3D(g);
        }
        
        g.setColor(Color.BLACK);
        g.drawString("Press space key to simulate again ", 10, 20);
    }
    
    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getID() != KeyEvent.KEY_PRESSED) {
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                reset();
            }
        }
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                JFrame frame = new JFrame();
                frame.setTitle("Java Verlet Integration 3D test #3 - Simple cloth simulation");
                frame.getContentPane().add(view);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
                view.requestFocus();
            }
        });
    }    
    
}
