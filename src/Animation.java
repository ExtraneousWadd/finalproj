import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements ActionListener {
    private ArrayList<BufferedImage> frames;
    private Timer timer;
    private int currentFrame;

    public Animation(ArrayList<BufferedImage> frames, int delay ) {
        this.frames = frames;
        currentFrame = 0;
        timer = new Timer(delay, this);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void startAnimation(){
        setCurrentFrame(0);
        timer.start();
    }
    public void setCurrentFrame(int setter){
        currentFrame = setter;
    }

    public ArrayList<BufferedImage> getFrames() {
        return frames;
    }

    public BufferedImage getActiveFrame() {
        return frames.get(currentFrame);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            currentFrame = (currentFrame + 1) % frames.size();
            //if(currentFrame == 0){
            //    timer.stop();
            //    startAnimation();
            // }
        }
    }
}
