import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Stage {
    private BufferedImage image;
    private double xCoord;
    private double yCoord;
    private String name;

    public Stage(String image, String name, double xCoord, double yCoord) {
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        try {
            this.image = ImageIO.read(new File(image));
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

    public String getName() {
        return name;
    }

    public BufferedImage getStageImage() {
        return image;
    }


    public Rectangle stageRect() {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
