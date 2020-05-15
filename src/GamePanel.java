import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private int panelWidth, panelHeight;
    private int score;

    private Rabbit rabbit;
    private Carrot carrot;

    GamePanel(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        imgRabbit = toolkit.getImage("res/rabbit.png");
        imgCarrot = toolkit.getImage("res/carrot.png");

        GraphicsConfiguration configuration = getGraphicsConfiguration();
        panelWidth = configuration.getBounds().width;
        panelHeight = configuration.getBounds().height;
        imgRabbit = imgRabbit.getScaledInstance(IMG_RABBIT.width, IMG_RABBIT.height, Image.SCALE_SMOOTH);
        imgCarrot = imgCarrot.getScaledInstance(IMG_CARROT.width, IMG_CARROT.height, Image.SCALE_SMOOTH);

        rabbit = new Rabbit();
        carrot = new Carrot();
        score = 0;
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
