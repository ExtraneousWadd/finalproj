import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = 2.5;
    private BufferedImage imageRight;
    private BufferedImage imageLeft;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private String name;
    private boolean gravity;
    private Animation runRight;
    private Animation runLeft;
    private boolean isRun;


    public Player(String imageRight, String imageLeft, String name) {
        this.name = name;
        xCoord = 50; // starting position is (50, 435), right on top of ground
        yCoord = 435;
        try {
            this.imageRight = ImageIO.read(new File(imageRight));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            this.imageLeft = ImageIO.read(new File(imageLeft));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        gravity = true;

        ArrayList<BufferedImage> run_animationRight = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "player/player1_run_" + i + ".png";
            try {
                run_animationRight.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        runRight = new Animation(run_animationRight,66);
        ArrayList<BufferedImage> run_animationLeft = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "player/player1_run_left_" + i + ".png";
            try {
                run_animationLeft.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        runLeft = new Animation(run_animationLeft,66);
        isRun = false;
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public String getName() {
        return name;
    }

    public void faceRight() {
        facingRight = true;
    }

    public void faceLeft() {
        facingRight = false;
    }

    public void moveRight() {
        if (xCoord + MOVE_AMT <= 1900) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void jump() {
        if (yCoord - MOVE_AMT >= 0) {
            for(int i = 0; i < 100; i++) {
                yCoord -= MOVE_AMT;
            }
        }
    }

    public void gravity(){
        if(gravity){
            yCoord += 1.25;
        }
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }

    public BufferedImage getPlayerImage() {
        if(isRun) {
            if (facingRight) {
                return runRight.getActiveFrame();
            } else {
                return runLeft.getActiveFrame();
            }
        }

        if(facingRight) {
            return imageRight;
        } else {
            return imageLeft;
        }
    }

    public void setRun(boolean set){
        isRun = set;
    }


    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
