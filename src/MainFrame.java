import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFrame extends JFrame {             //RaCaGame
    private static final int WINDOW_WIDTH = 700;        //윈도우의 너비
    private static final int WINDOW_HEIGHT = 800;       //윈도우의 높이
    private static final String WINDOW_TITLE = "Rabbit Carrot Game - was made by AJH / icons were made by PYJ";     //프로그램창 제목
    private final GamePanel gamePanel;      //윈도우 안에 들어갈 패널

    public MainFrame(){
        setTitle(WINDOW_TITLE);     //타이틀 설정
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);   //사이즈 설정
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2-WINDOW_WIDTH, screenSize.height/2-WINDOW_HEIGHT);    //사용자 화면의 가운데에 배치

        setResizable(false);    //창 크기 변경 불가
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //닫기 버튼 눌렀을 시 닫히도록

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        gamePanel = new GamePanel();
        container.add(gamePanel, BorderLayout.CENTER);
        //윈도우 내의 컨텐츠 영역에 panel 추가, 레이아웃은 Border로 하고 가운데에 하나만 추가

        addKeyListener(gamePanel);      //사용자 키 입력에 대한 리스너 추가 -> 처리는 GamePanel클래스에서

        setVisible(true);       //사용자에게 보이기
    }

    GamePanel getGamePanel(){
        return gamePanel;
    }       //메인에서 패널을 쓰기 위해 getter를 사용. (메인이 클래스 내에 있어 없어도 됨)


    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();      //윈도우(프레임) 생성
        GamePanel panel = mainFrame.getGamePanel(); //패널 가져오기

        ExecutorService executorService = Executors.newSingleThreadExecutor();  //별도의 쓰레드 1개 받아옴
        executorService.execute(()->{       //lamda식
            while(true){
                try{
                    if(!mainFrame.isActive()) {     //아직 윈도우가 active상태가 아니라면 panel에 대한 조정은 exception을 던진다.
                        Thread.sleep(50);
                        continue;           //아직 메인 프레임이 준비되지 않은 경우 잠시 기다림.
                    }
                    panel.checkCollision(); //플레이어(토끼)와의 충돌체크
                    panel.moveCarrot(); //panel에 있는 당근 움직이기
                    panel.repaint();    //화면 다시 그리기 -> 사용자의 위치 이동과 carrot이 이동한 것을 화면에 재반영

                    Thread.sleep(50);   //쓰레드를 일시 정지.
                }catch (InterruptedException e){
                    JOptionPane.showMessageDialog(null, "오류 발생", "오류가 발생하였습니다.", JOptionPane.WARNING_MESSAGE);
                    System.exit(1); //쓰레드 작동 도중 오류 발생시 프로그램 강제종료
                }
            }
        });
        //메인쓰레드에서는 윈도우를 띄우고 있게 하고
        //서브 쓰레드에서는 당근을 움직이면서 화면을 계속 다시 그린다.(이동사항 반영, 사용자가 이동한 것도 반영)
        //그러면서 계속 충돌여부를 감시한다.

    }
}
