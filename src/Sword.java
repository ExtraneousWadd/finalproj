import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sword {
    private Player player;
    private BufferedImage right;
    private BufferedImage left;
    private BufferedImage dead;
    private double xCoord;
    private double yCoord;
    private boolean facingRight;
    private boolean thrown;

    public Sword(String dead, String image, String image2, Player player) {
        this.player = player;
        facingRight = false;
        thrown = false;
        try {
            right = ImageIO.read(new File(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            left = ImageIO.read(new File(image2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public void setxCoord(double setter){
        xCoord = setter;
    }

    public void setyCoord(double setter){
        yCoord = setter;
    }

    public boolean isThrown(){return thrown;}

    public BufferedImage getImage(){
        if(thrown) {
            if (player.isFacingRight()) {
                facingRight = true;
                return right;
            } else {
                facingRight = false;
                return left;
            }
        }
        return dead;
    }

    public void reset(){
        facingRight = false;
        xCoord = 0;
        yCoord = 0;
        thrown = false;
    }

    public void throwMe(){
        thrown = true;
        if(player.isFacingRight()){
            facingRight = true;
        } else {
            facingRight = false;
        }

    }

    public void movement(){
        if(facingRight){
            xCoord += .65;
        } else {
            xCoord -= .65;
        }
    }

    public Rectangle swordRect() {
        if(thrown) {
            Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, 100, 20);
            return rect;
        }
        Rectangle rect = new Rectangle(0,0,0,0);
        return rect;
    }
}