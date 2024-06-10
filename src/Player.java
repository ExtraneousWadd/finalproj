import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private final double MOVE_AMT = .375;
    private BufferedImage imageRight;
    private BufferedImage imageLeft;
    private BufferedImage imageRightSword;
    private BufferedImage imageLeftSword;
    private BufferedImage jumpLeft;
    private BufferedImage dead;
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
    private Animation sliceRight;
    private Animation sliceLeft;
    private boolean isRun;
    private boolean jumping;
    private boolean hasSword;
    private boolean attacking;
    private boolean attackCD;
    private boolean isDead;
    private boolean p1;
    private ArrayList<BufferedImage> jump_animationRight;
    private Player playerOther;


    public Player(String imageRight, String imageLeft, String imageRightSword, String imageLeftSword, String name, Player player, boolean p1) {
        this.name = name;
        this.p1 = p1;
        playerOther = player;
        if (p1) {
            xCoord = 50; // starting position is (50, 435), right on top of ground
            yCoord = 750;
        } else {
            xCoord = 1750;
            yCoord = 750;
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
        ArrayList<BufferedImage> sliceAnimation = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "player/slice" + i + ".png";
            try {
                sliceAnimation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        sliceRight = new Animation(sliceAnimation,20);
        ArrayList<BufferedImage> sliceAnimationleft = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "player/slice" + i + "left.png";
            try {
                sliceAnimationleft.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        sliceLeft = new Animation(sliceAnimationleft,20);
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
        try {
            jumpLeft = (ImageIO.read(new File("player/player1_jump_3left.png")));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            dead = (ImageIO.read(new File("player/dead.jpg")));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        jumpRight = new Animation(jump_animationRight,3);
        isRun = false;
        jumping = false;
        attackCD = false;
        hasSword = true;
        isDead = false;
        runRight.startAnimation();
        runLeft.startAnimation();
        runRightSword.startAnimation();
        runLeftSword.startAnimation();
        jumpRight.startAnimation();
        sliceRight.startAnimation();
        sliceLeft.startAnimation();
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
    public boolean getDead(){return isDead;}
    public boolean isHasSword(){return hasSword;}
    public boolean isFacingRight(){return facingRight;}

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

    public void attack(boolean setter) {
        if(hasSword) {
            if (!attackCD) {
                attackCD = true;
                attacking = setter;
                if (setter) {
                    attackTimer();
                }
            }
        }
    }

    public void throwSword(){
        if(hasSword) {
            hasSword = false;
            throwTimer();
        }
    }

    public void dead(boolean setter){
        if(isDead == setter){
            return;
        }
        isDead = setter;
        if(setter){
            if(p1){
                xCoord = 50;
            } else {
                xCoord = 1750;
            }
            yCoord = 750;
            deadTimer();
        }
    }

    private void attackTimer(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                attacking = false;
            }
        };
        timer.schedule(timerTask, 100);

        Timer attackCDTimer = new Timer();
        TimerTask attackCDTask = new TimerTask() {

            @Override
            public void run() {
                attackCD = false;
            }
        };
        attackCDTimer.schedule(attackCDTask, 750);
    }
    private void deadTimer(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                isDead = false;
            }
        };
        timer.schedule(timerTask, 3000);
    }

    private void throwTimer(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                hasSword = true;
            }
        };
        timer.schedule(timerTask, 3000);
    }

    public void reset(){
        if (p1) {
            xCoord = 50;
            yCoord = 750;
        } else {
            xCoord = 1750;
            yCoord = 750;
        }
        isRun = false;
        jumping = false;
        attackCD = false;
        hasSword = true;
        isDead = false;
    }

    public void gravity(){
        yCoord += .35;
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }

    public BufferedImage getPlayerImage() {
        if(isDead){
            return dead;
        }
        if(attacking){
            if(facingRight){
                return sliceRight.getActiveFrame();
            } else {
                return sliceLeft.getActiveFrame();
            }
        }
        if(jumping){
            yCoord -= 2;
            if(yCoord < preJumpYCoord - 200) {
                jumping = false;
            }
            if(facingRight) {
                return jumpRight.getActiveFrame();
            } else {
                return jumpLeft;
            }
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
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, 77, 119);
        return rect;
    }

    public Rectangle hitRectangle(){
        if(attacking) {
            Rectangle rect = new Rectangle((int) xCoord + 30, (int) yCoord + 5, 77, 119);
            return rect;
        }
        Rectangle rect = new Rectangle(0, 0, 0, 0);
        return rect;
    }
}
