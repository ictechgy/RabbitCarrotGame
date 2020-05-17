import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {
    private static final int MOV_RAB = 20;  //rabbit의 이동변위
    private static final int MOV_CAR = 25;  //carrot의 이동변위

    private int score;  //점수

    private final Rabbit rabbit;    //사용자 Rabbit 객체
    private final Carrot carrot;    //당근 객체
    private final Random random;

    private boolean isLoaded;   //최초 로딩인지의 여부 판단 flag

    GamePanel(){
        score = 0;  //0점으로 초기화
        isLoaded = false; //아직 panel의 사이즈 측정 불가능

        carrot = Carrot.getInstance();
        rabbit = Rabbit.getInstance();       //각각의 싱글톤 객체 얻어오기

        random = new Random();
    }

    private Point getCarrotPossition(){
        int x = random.nextInt(getWidth() - Carrot.WIDTH);
        int y = random.nextInt(getHeight() - Carrot.HEIGHT);
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!isLoaded){                  //화면이 최초 로딩되는 것이라면

            rabbit.setLocation(10, 50);   //사용자를 화면 왼쪽 위에 생성
            carrot.setLocation(getCarrotPossition());   //당근을 랜덤위치에 생성

            isLoaded = true;    //로딩 완료
        }

        g.drawImage(rabbit.getImage(), rabbit.getX(), rabbit.getY(), this);
        g.drawImage(carrot.getImage(), carrot.getX(), carrot.getY(), this);
        g.setFont(new Font(null, Font.BOLD, 20));
        g.drawString("Score: " + score + "점", 10, 30);
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
    public void keyPressed(KeyEvent e) {        //화살표 키 눌렀을 시 Rabbit의 이동 처리
        int x = rabbit.getX();
        int y = rabbit.getY();

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> {
                y -= MOV_RAB;
                System.out.println("윗방향키");
            }
            case KeyEvent.VK_RIGHT -> {
                x += MOV_RAB;
                System.out.println("오른방향키");
            }
            case KeyEvent.VK_DOWN -> {
                y += MOV_RAB;
                System.out.println("아래방향키");
            }
            case KeyEvent.VK_LEFT -> {
                x -= MOV_RAB;
                System.out.println("왼쪽방향키");
            }
            default -> {
                return; //다른 키를 입력한 경우 해당 입력 무시
            }
        }

        x = validX(x, Rabbit.WIDTH);
        y = validY(y, Rabbit.HEIGHT);

        rabbit.setX(x);
        rabbit.setY(y);

    }

    private int validX(int x, int imgWidth){
        if(x > getWidth() - imgWidth/2){   // 이미지가 반 이상 오른쪽 화면을 넘어갔다면
            x = -imgWidth/2;   //왼쪽 화면으로 옮기되, 오른쪽 절반만 보이게
        }else if(x < -WIDTH/2) {   // 이미지가 왼쪽 화면을 반 이상 넘어갔다면
            x = getWidth() - imgWidth/2;
        }   //그 외의 경우에 x는 그대로 x
        return x;
    }

    private int validY(int y, int imgHeight){
        if(y > getHeight() - imgHeight/2){   //이미지가 반 이상 아래로 넘어갔다면
            y = -imgHeight/2;   //위쪽으로
        }else if(y < -imgHeight/2) {   //이미지가 위로 반 이상 올라갔다면
            y = getHeight() - imgHeight/2;
        }   //이외의 경우에는 그대로
        return y;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    void checkCollision(){
        int leftX = rabbit.getX();
        int rightX = rabbit.getX() + Rabbit.WIDTH;
        int ceilY = rabbit.getY();
        int floorY = rabbit.getY() + Rabbit.HEIGHT;

        int c_centerX = carrot.getX() + Carrot.WIDTH/2;
        int c_centerY = carrot.getY() + Carrot.HEIGHT/2;

        if(c_centerX > leftX && c_centerX < rightX && c_centerY > ceilY && c_centerY < floorY){    //두 객체 충돌 시
            score += 10;
            carrot.setLocation(getCarrotPossition());   //carrot을 새로운 위치로 새로 생성
        }
    }

    void moveCarrot(){  //Carrot을 일정확률로 이동
        int num = random.nextInt(50);
        if(num >= 4) return;

        int x = carrot.getX();
        int y = carrot.getY();

        switch (num){
            case 0 -> y -= MOV_RAB;
            case 1 -> x += MOV_RAB;
            case 2 -> y += MOV_RAB;
            case 3 -> x -= MOV_RAB;
        }
        //랜덤수에 의해 각 방향으로 MOV_RAB만큼 이동한다.

        x = validX(x, Carrot.WIDTH);
        y = validY(y, Carrot.HEIGHT);

        carrot.setX(x);
        carrot.setY(y);
    }

}