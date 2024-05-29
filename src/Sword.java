import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sword {
    private Player player;
    private BufferedImage image;
    private double xCoord;
    private double yCoord;

    public Sword(String image) {
        try {
            this.image = ImageIO.read(new File(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public Rectangle swordRect() {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
