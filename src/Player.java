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
    private BufferedImage imageRightSword;
    private BufferedImage imageLeftSword;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private double preJumpYCoord;
    private String name;
    private Animation runRight;
    private Animation runLeft;
    private Animation runRightSword;
    private Animation runLeftSword;
    private Animation jumpRight;
    private boolean isRun;
    private boolean jumping;
    private boolean hasSword;
    private ArrayList<BufferedImage> jump_animationRight;
    private Player playerOther;


    public Player(String imageRight, String imageLeft, String imageRightSword, String imageLeftSword, String name, Player player, boolean p1) {
        this.name = name;
        playerOther = player;
        if (p1) {
            xCoord = 50; // starting position is (50, 435), right on top of ground
            yCoord = 100;
        } else {
            xCoord = 850;
            yCoord = 100;
        }
        preJumpYCoord = yCoord;
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
        try {
            this.imageRightSword = ImageIO.read(new File(imageRightSword));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            this.imageLeftSword = ImageIO.read(new File(imageLeftSword));
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

        ArrayList<BufferedImage> run_animationRightSword = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "player/player1_run_" + i + "_sword.png";
            try {
                run_animationRightSword.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        runRightSword = new Animation(run_animationRightSword,66);
        ArrayList<BufferedImage> run_animationLeftSword = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "player/player1_run_" + i + "_swordleft.png";
            try {
                run_animationLeftSword.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        runLeftSword = new Animation(run_animationLeftSword,66);
        jump_animationRight = new ArrayList<>();
        /*for (int i = 1; i <= 7; i++) {
            String filename = "player/player1_jump_" + i + ".png";
            try {
                jump_animationRight.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }*/
        try {
            jump_animationRight.add(ImageIO.read(new File("player/player1_jump_3.png")));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        jumpRight = new Animation(jump_animationRight,3);
        isRun = false;
        jumping = false;
        hasSword = true;
        runRight.startAnimation();
        runLeft.startAnimation();
        runRightSword.startAnimation();
        runLeftSword.startAnimation();
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
        preJumpYCoord = yCoord;
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
            yCoord -= 3;
            if(yCoord < preJumpYCoord - 200) {
                jumping = false;
            }
            return jumpRight.getActiveFrame();
        }
        if(isRun) {
            if (facingRight) {
                if(hasSword){
                    return runRightSword.getActiveFrame();
                }
                return runRight.getActiveFrame();
            } else {
                if(hasSword){
                    return runLeftSword.getActiveFrame();
                }
                return runLeft.getActiveFrame();
            }
        }
        if(facingRight) {
            if(hasSword){
                return imageRightSword;
            }
            return imageRight;
        } else {
            if(hasSword){
                return imageLeftSword;
            }
            return imageLeft;
        }
    }

    public void setRun(boolean set){
        isRun = set;
    }
    public Rectangle playerRect() {
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord + 5, 77, 119);
        return rect;
    }
}
