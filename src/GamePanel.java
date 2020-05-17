import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel implements KeyListener {
    private static final int MOV_RAB = 20;  //rabbit의 이동변위
    private static final int MOV_CAR = 25;  //carrot의 이동변위

    private int score;  //점수
    private int remain; //남은 시간
    private final Timer timer;  //남은 시간을 줄이기 위한 timer 객체

    private final Rabbit rabbit;    //사용자 Rabbit 객체
    private final Carrot carrot;    //당근 객체
    private final Random random;    //난수값을 얻기 위한 변수

    private boolean isLoaded;   //panel 최초 로딩인지의 여부 판단 flag

    GamePanel(){
        score = 0;  //0점으로 초기화
        remain = 45;    //시간은 기본적으로 45초 부여
        isLoaded = false; //아직 panel의 사이즈 측정 불가능

        timer = new Timer();    //타이머 생성
        TimerTask t_task = new TimerTask() {    //타이머가 해야 할 일을 지정
            @Override
            public void run() {
                if (remain > 0) {
                    remain -= 1;        //아직 시간이 남아있다면 1초씩 줄인다.
                } else {
                    timer.cancel();     //타이머가 끝나면 타이머 작동을 멈추고 메시지를 띄운 후 게임 종료
                    JOptionPane.showMessageDialog(null, "제한시간 내에 점수를 달성하지 못했습니다. Carrot Win!!", "시간 초과!!", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        };

        timer.schedule(t_task, 1050, 1000); //타이머 스케쥴링.
        // 첫 딜레이는 1초, 이후 1초마다 run 작동 (바로 panel이 로딩되지 않으므로 50밀리초 보정값 부여)


        carrot = Carrot.getInstance();
        rabbit = Rabbit.getInstance();       //각각의 싱글톤 객체 얻어오기

        random = new Random();  //랜덤객체 인스턴스화
    }

    private Point getRandomPossition(int imgWidth, int imgHeight){     //랜덤 위치값을 얻는다.
        int x = random.nextInt(getWidth() - imgWidth);  //x좌표와 y좌표는 이미지의 좌측 상단 좌표이므로 가능한 좌표값의 바운드는 0 ~ 너비(높이)에서 이미지 너비(높이)를 뺀 값.
        int y = random.nextInt(getHeight() - imgHeight);    //따라서 이미지는 랜덤 위치를 얻었을 시 잘리는 부분 없이 온전히 panel 안에 있을 수 있다.
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!isLoaded){                  //화면이 최초 로딩되는 것이라면 단 한번 실행.
            rabbit.setLocation(getRandomPossition(Rabbit.WIDTH, Rabbit.HEIGHT));   //사용자도 매번 랜덤위치에 생성 (게임 실행시 최초 한번)
            carrot.setLocation(getRandomPossition(Carrot.WIDTH, Carrot.HEIGHT));   //당근을 랜덤위치에 생성

            isLoaded = true;    //로딩 완료
        }

        g.drawImage(rabbit.getImage(), rabbit.getX(), rabbit.getY(), this); //토끼 이미지를 지정된 위치에 그리기
        g.drawImage(carrot.getImage(), carrot.getX(), carrot.getY(), this); //당근 이미지를 지정된 위치에 그리기
        g.setFont(new Font(null, Font.BOLD, 20));   //폰트 설정
        g.drawString("Score: " + score + "점", 10, 30);  //점수 표시
        g.drawString("남은 시간: " + remain + "초", 550, 30);    //남은 시간 표시
    }
    /*
        테스팅해보니 paintComponent는 repaint()마다 호출되며, 사용자가 버튼으로 움직일때는 어떠한 콜백도 작동하지 않음
        최초 paintComponent 호출 전에는 componentResized가 한번 호출된다.
        테스팅한 Listener 종류는 아래와 같다.(생성자에서 등록)
        addContainerListener(this);
        addComponentListener(this);
        addPropertyChangeListener(this);
        addHierarchyListener(this);
        addAncestorListener(this);
        addVetoableChangeListener(this);
        addHierarchyBoundsListener(this);
        -> Listener는 GamePanel 자체에 구현
        -> 여러가지 테스팅을 해보았지만 콜백 리스너만으로 panel사이즈 값을 얻어서 Rabbit 및 Carrot에 초기화하는 것은 힘들어보임
     */

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {        //화살표 키 눌렀을 시 Rabbit의 이동 처리 콜백 메소드
        int x = rabbit.getX();
        int y = rabbit.getY();      //기존 좌표값 얻어오기

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> {        //화살표 위 키를 눌렀을 시
                y -= MOV_RAB;
            }
            case KeyEvent.VK_RIGHT -> {     //화살표 오른쪽 키를 눌렀을 시
                x += MOV_RAB;
            }
            case KeyEvent.VK_DOWN -> {      //화살표 아래 키를 눌렀을 시
                y += MOV_RAB;
            }
            case KeyEvent.VK_LEFT -> {      //화살표 왼쪽 키를 눌렀을 시
                x -= MOV_RAB;
            }
            default -> {
                return; //다른 키를 입력한 경우 해당 입력 무시
            }
        }

        x = validX(x, Rabbit.WIDTH);
        y = validY(y, Rabbit.HEIGHT);
        //이동 후의 좌표값에 대한 위치 유효성 검증 및 조정

        rabbit.setX(x);
        rabbit.setY(y);
        //해당 좌표값을 반영시킨다.

    }

    private int validX(int x, int imgWidth){
        if(x > getWidth() - imgWidth/2){   // 이미지가 반 이상 오른쪽 화면을 넘어갔다면
            x = -imgWidth/2;   //왼쪽 화면으로 옮기되, 오른쪽 절반만 보이게
        }else if(x < -imgWidth/2) {   // 이미지가 왼쪽 화면을 반 이상 넘어갔다면
            x = getWidth() - imgWidth/2;    //오른쪽 화면에서 왼쪽부분을 보이게
        }   //그 외의 경우에 x는 그대로 x
        return x;
    }

    private int validY(int y, int imgHeight){
        if(y > getHeight() - imgHeight/2){   //이미지가 반 이상 아래로 넘어갔다면
            y = -imgHeight/2;   //위쪽으로
        }else if(y < -imgHeight/2) {   //이미지가 위로 반 이상 올라갔다면
            y = getHeight() - imgHeight/2;  //아래쪽으로
        }   //이외의 경우에는 그대로 둔다.
        return y;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    void checkCollision(){      //충돌 체크 메소드
        int leftX = rabbit.getX();
        int rightX = rabbit.getX() + Rabbit.WIDTH;
        int ceilY = rabbit.getY();
        int floorY = rabbit.getY() + Rabbit.HEIGHT;
        //네개의 변수값을 얻어오는데, 토끼 객체의 사각형 범위를 얻어온다.

        int c_centerX = carrot.getX() + Carrot.WIDTH/2;
        int c_centerY = carrot.getY() + Carrot.HEIGHT/2;
        //당근 객체의 중심점 좌표값 얻어오기

        if(c_centerX > leftX && c_centerX < rightX && c_centerY > ceilY && c_centerY < floorY){    //두 객체 충돌 시에 대한 판단식. 당근의 중심점이 토끼객체의 사각형 범위 내로 들어온 경우
            score += 10;        //10점 획득
            if(score >= 100){
                timer.cancel();     //100점 획득 시 타이머를 종료하고 메시지를 띄운 뒤 게임 종료
                JOptionPane.showMessageDialog(null, "축하드립니다!! 100점 달성! Rabbit Win!!", "점수 달성!!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            carrot.setLocation(getRandomPossition(Carrot.WIDTH, Carrot.HEIGHT));
            //충돌은 했지만 아직 100점이 아닌경우 새로운 위치에 당근을 새로 생성
        }
    }

    void moveCarrot(){  //Carrot을 일정확률로 이동
        int num = random.nextInt(50);
        if(num >= 4) return;    //0~49의 숫자 중에 4 이상의 숫자가 나오면 무시

        //0~3의 숫자가 나온 경우
        int x = carrot.getX();
        int y = carrot.getY();

        switch (num){
            case 0 -> y -= MOV_CAR;
            case 1 -> x += MOV_CAR;
            case 2 -> y += MOV_CAR;
            case 3 -> x -= MOV_CAR;
        }
        //좌표값을 MOV_RAB만큼 이동한다.

        x = validX(x, Carrot.WIDTH);
        y = validY(y, Carrot.HEIGHT);   //유효성 체크

        carrot.setX(x);
        carrot.setY(y); //이동시킨다.
    }

}