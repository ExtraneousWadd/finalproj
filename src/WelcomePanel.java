import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel implements ActionListener {

    private JTextField textField;
    private JTextField textField2;

    private JButton submitButton;
    private JButton clearButton;
    private JFrame enclosingFrame;

    public WelcomePanel(JFrame frame) {
        enclosingFrame = frame;
        textField = new JTextField(10);
        textField2 = new JTextField(10);
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        add(textField);  // textField doesn't need a listener since nothing needs to happen when we type in text
        add(textField2);
        add(submitButton);
        add(clearButton);
        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.RED);
        g.drawString("Please enter your names:", 50, 30);
        textField.setLocation(50, 50);
        textField2.setLocation(50, 70);
        submitButton.setLocation(50, 100);
        clearButton.setLocation(150, 100);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == submitButton) {
                String playerName = textField.getText();
                String playerName2 = textField2.getText();
                MainFrame f = new MainFrame(playerName, playerName2);
                enclosingFrame.setVisible(false);
            } else {
                textField.setText("");
            }
        }
    }
}
