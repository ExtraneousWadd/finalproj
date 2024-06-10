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
    private Player player2;
    private boolean[] pressedKeys;
    private Timer timer;
    private int player1Score;
    private int player2Score;
    private Sword swordP1;
    private Sword swordP2;
    private int time;
    private Stage background;

    public GraphicsPanel(String name, String name2) {
        background = new Stage("src/line.png","stage",0,900);
        player = new Player("src/playerImage.png","src/playerImageleft.png", "src/playerImagesword.png", "src/playerImageswordleft.png", name, player2, true);
        player2 = new Player("src/playerImage.png","src/playerImageleft.png", "src/playerImagesword.png", "src/playerImageswordleft.png", name2, player, false);
        swordP1 = new Sword("player/dead.jpg","src/sword.png", "src/swordleft.png", player);
        swordP2 = new Sword("player/dead.jpg","src/sword.png", "src/swordleft.png", player2);
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
        if(player1Score == 5){
            g.setFont(new Font("Courier New", Font.BOLD, 250));
            g.drawString(player.getName() + " wins", 100, 500);
            return;
        }
        if(player2Score == 5){
            g.setFont(new Font("Courier New", Font.BOLD, 250));
            g.drawString(player2.getName() + " wins", 100, 500);
            return;
        }
        g.drawImage(background.getStageImage(), background.getxCoord(), background.getyCoord(), null);
        g.setFont(new Font("Courier New", Font.BOLD, 250));
        if(player1Score > player2Score){
            String str = player.getName() + ": " + (player1Score - player2Score);
            g.drawString(str, 100, 500);
        } else if (player2Score > player1Score){
            String str =  player2.getName() + ": " + (player2Score - player1Score);
            g.drawString(str, 100, 500);
        } else {
            String str = "" + 0;
            g.drawString(str, 800, 500);
        }

        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
        g.drawImage(player2.getPlayerImage(), player2.getxCoord(), player2.getyCoord(), null);
        g.drawImage(swordP1.getImage(), swordP1.getxCoord(), swordP1.getyCoord(), null);
        g.drawImage(swordP2.getImage(), swordP2.getxCoord(), swordP2.getyCoord(), null);

        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString(player.getName(), player.getxCoord() - 2, player.getyCoord() - 20);
        g.drawString(player2.getName(), player2.getxCoord() - 2, player2.getyCoord() - 20);

        if(swordP1.getxCoord() >= 1850 || swordP1.getxCoord() <= 0){
            swordP1.reset();
        }
        if(swordP2.getxCoord() >= 1850 || swordP2.getxCoord() <= 0){
            swordP2.reset();
        }

        if(player2.getxCoord() <= 0){
            player2Score++;
            player.reset();
            player2.reset();
        }

        if(player.getxCoord() >= 1850){
            player1Score++;
            player.reset();
            player2.reset();
        }

        if(player2.getxCoord() <= 0){
            player2Score++;
            player.reset();
            player2.reset();
        }
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

        if (pressedKeys[37]) {
            player2.moveLeft();
            player2.faceLeft();
            player2.setRun(true);
        }


        if (pressedKeys[39]) {
            player2.moveRight();
            player2.faceRight();
            player2.setRun(true);
        }

        Rectangle rect = new Rectangle((int) background.getxCoord(), (int) background.getyCoord(), background.getStageImage().getWidth(), background.getStageImage().getWidth());
        if (!player.playerRect().intersects(rect)) {
            player.gravity();
        } else {
            player.jump(false);
        }

        if (!player2.playerRect().intersects(rect)) {
            player2.gravity();
        } else {
            player2.jump(false);
        }

        if(swordP1.isThrown()){
            swordP1.movement();
        }
        if(swordP2.isThrown()){
            swordP2.movement();
        }

        if(player.hitRectangle().intersects(player2.playerRect())){
            player2.dead(true);
        }
        if(player2.hitRectangle().intersects(player.playerRect())){
            player.dead(true);
        }

        if(swordP1.swordRect().intersects(player2.playerRect())){
            player2.dead(true);
        }
        if(swordP2.swordRect().intersects(player.playerRect())){
            player.dead(true);
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
        if(key == 38){
            if (player2.playerRect().intersects(background.stageRect())){
                player2.jump(true);
            }
        }
        if(key == 70){
            if(!player.getDead()) {
                player.attack(true);
            }
        }
        if(key == 48){
            if(!player2.getDead()) {
                player2.attack(true);
            }
        }
        if(key == 69){
            if(player.isHasSword()) {
                player.throwSword();
                swordP1.throwMe();
            }
        }
        if(key == 50){
            if(player2.isHasSword()) {
                player2.throwSword();
                swordP2.throwMe();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
        if(key == 68 || key == 65){
            player.setRun(false);
        }

        if(key == 37 || key == 39){
            player2.setRun(false);
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
