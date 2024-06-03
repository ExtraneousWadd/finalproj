import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private Player player;
    private boolean[] pressedKeys;
    private Timer timer;
    private Sword sword;
    private int time;
    private Stage background;

    public GraphicsPanel(String name) {
        background = new Stage("src/line.png","stage",0,500);
        player = new Player("src/playerImage.png","src/playerImageleft.png", name);
        sword = new Sword("src/sword.png");
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background.getStageImage(), background.getxCoord(), background.getyCoord(), null);  // the order that things get "painted" matter; we put background down first
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
        g.drawImage(sword.getImage(), player.getxCoord(), player.getyCoord(), null);

        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString(player.getName(), player.getxCoord() - 2, player.getyCoord() - 20);

        // player moves left (A)
        if (pressedKeys[65]) {
            player.moveLeft();
            player.faceLeft();
            player.setRun(true);
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            player.moveRight();
            player.faceRight();
            player.setRun(true);
        }
        Rectangle rect = new Rectangle((int) background.getxCoord(), (int) background.getyCoord() - 5, background.getStageImage().getWidth(), background.getStageImage().getWidth());
        if (!player.playerRect().intersects(rect)) {
            player.gravity();
        } else {
            player.jump(false);
        }
    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
        if(key == 87){
            if (player.playerRect().intersects(background.stageRect())){
                    player.jump(true);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
        if(key == 68 || key == 65){
            player.setRun(false);
        }
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            Point mouseClickLocation = e.getPoint();
        } else {
            Point mouseClickLocation = e.getPoint();
            if (player.playerRect().contains(mouseClickLocation)) {
            }
        }
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    // ACTIONLISTENER INTERFACE METHODS: used for buttons AND timers!
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time++;
        }
    }
}
