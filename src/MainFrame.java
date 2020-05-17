import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFrame extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 800;
    private static final String WINDOW_TITLE = "Rabbit Carrot Game by AJH / Character Design by PYJ";
    private final GamePanel gamePanel;

    public MainFrame(){
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2-WINDOW_WIDTH, screenSize.height/2-WINDOW_HEIGHT);    //사용자 화면의 가운데에 배치
        setResizable(false);    //창 크기 변경 불가
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //닫기 버튼 눌렀을 시 닫히도록

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

        ExecutorService executorService = Executors.newSingleThreadExecutor();  //별도의 쓰레드 1개 받아옴
        executorService.execute(()->{
            while(true){
                try{
                    if(!mainFrame.isActive()) {
                        Thread.sleep(50);
                        continue;           //아직 메인 프레임이 준비되지 않은 경우 잠시 기다림.
                    }
                    panel.checkCollision(); //플레이어와의 충돌체크
                    panel.moveCarrot(); //panel에 있는 당근 움직이기
                    panel.repaint();    //화면 다시 그리기

                    Thread.sleep(50);   //쓰레드를 일시 정지.
                }catch (InterruptedException e){
                    JOptionPane.showMessageDialog(null, "오류 발생", "오류가 발생하였습니다.", JOptionPane.WARNING_MESSAGE);
                    System.exit(1); //쓰레드 작동 도중 오류 발생시 프로그램 종료
                }
            }
        });

    }
}
