import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private final double MOVE_AMT = 2.5;
    private BufferedImage image;
    private double xCoord;
    private double yCoord;
    private String name;
    private boolean gravity;


    public Player(String image, String name) {
        this.name = name;
        xCoord = 50; // starting position is (50, 435), right on top of ground
        yCoord = 435;
        try {
            this.image = ImageIO.read(new File(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        gravity = true;
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

    public void moveDown() {
        if (yCoord + MOVE_AMT <= 1000) {
            yCoord += 2;
        }
    }

    public void gravity(){
        if(gravity){
            yCoord += 1.25;
        }
    }

    public BufferedImage getPlayerImage() {
        return image;
    }


    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
