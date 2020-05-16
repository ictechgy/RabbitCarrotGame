import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFrame extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 800;
    private static final String WINDOW_TITLE = "Rabbit Carrot Game by AJH / Character Design by PYJ";
    private GamePanel gamePanel;

    public MainFrame(){
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2-WINDOW_WIDTH, screenSize.height/2-WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        gamePanel = new GamePanel();
        container.add(gamePanel, BorderLayout.CENTER);
        addKeyListener(gamePanel);

        setVisible(true);
    }

    GamePanel getGamePanel(){
        return gamePanel;
    }


    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        GamePanel panel = mainFrame.getGamePanel();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            while(true){
                panel.moveCarrot();
                panel.checkCollision();
                panel.repaint();
                try{
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    JOptionPane.showMessageDialog(null, "오류 발생", "오류가 발생하였습니다.", JOptionPane.WARNING_MESSAGE);
                    System.exit(1);
                }
            }
        });

    }
}
