import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int panelWidth, panelHeight;
    private int score;
    private Toolkit toolkit;
    private Rabbit rabbit;
    private Carrot carrot;

    GamePanel(){
        toolkit = Toolkit.getDefaultToolkit();

        GraphicsConfiguration configuration = getGraphicsConfiguration();
        panelWidth = configuration.getBounds().width;
        panelHeight = configuration.getBounds().height;
        score = 0;

        carrot = Carrot.getInstance(toolkit, panelWidth, panelHeight);
        carrot.setLocation();   //랜덤위치에 생성
        rabbit = Rabbit.getInstance(toolkit, panelWidth, panelHeight);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imgRabbit, rabbit.getX(), rabbit.getY(), this);
        g.drawImage(imgCarrot, carrot.getX(), carrot.getY(), this);
        g.setFont(new Font(null, Font.BOLD, 20));
        g.drawString("Score: " + score + "점", 10, 10);
    }

    private void moveCarrot(){
        carrot.move();
    }
}
