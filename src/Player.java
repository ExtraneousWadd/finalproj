import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = 2.5;
    private BufferedImage imageRight;
    private BufferedImage imageLeft;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private String name;
    private Animation runRight;
    private Animation runLeft;
    private Animation jumpRight;
    private boolean isRun;
    private boolean jumping;
    private ArrayList<BufferedImage> jump_animationRight;


    public Player(String imageRight, String imageLeft, String name) {
        this.name = name;
        xCoord = 50; // starting position is (50, 435), right on top of ground
        yCoord = 400;
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
        jump_animationRight = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String filename = "player/player1_jump_" + i + ".png";
            try {
                jump_animationRight.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        jumpRight = new Animation(jump_animationRight,20);
        isRun = false;
        jumping = false;
        runRight.startAnimation();
        runLeft.startAnimation();
        jumpRight.startAnimation();
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
    public boolean isJump(){return jumping;}

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

    public void jump(boolean yes) {
        jumping = yes;
    }

    public void gravity(){
            yCoord += 1.25;
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }

    public BufferedImage getPlayerImage() {
        if(jumping){
            yCoord -= 1;
            if(jumpRight.getCurrentFrame() == 0){
                jumping = false;
            }
            return jumpRight.getActiveFrame();
        }
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
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord + 5, imageWidth, imageHeight);
        return rect;
    }

    public Rectangle playerRect2() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
