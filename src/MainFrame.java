import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 800;
    private static final String WINDOW_TITLE = "Rabbit Carrot Game by AJH / Character Design by PYJ";

    public MainFrame(){
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2-WINDOW_WIDTH, screenSize.height/2-WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(new GamePanel(), BorderLayout.CENTER);

        setVisible(true);
    }


    public static void main(String[] args){
        new MainFrame();
    }
}
