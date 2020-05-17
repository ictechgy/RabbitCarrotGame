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
        if(x > getWidth() - imgWidth/2){   //당근 이미지가 반 이상 오른쪽 화면을 넘어갔다면
            x = -imgWidth/2;   //왼쪽 화면으로 옮기되, 오른쪽 절반만 보이게
        }else if(x < -WIDTH/2) {   //당근 이미지가 왼쪽 화면을 반 이상 넘어갔다면
            x = panelWidth - WIDTH / 2;
        }
        return x;
    }

    private int validY(int y, int imgHeight){

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    void checkCollision(){
        if(r_rect.intersects(c_rect)){    //두 객체에 대해 겹치는 부분이 존재한다면
            score += 10;
            carrot.setLocation();   //carrot을 새로운 위치로 새로 생성
        }
    }

    void moveCarrot(){
        carrot.move();
    }

}


//carrot

    void move(){
        int direction = random.nextInt(100);  //0 ~ 9까지의 난수를 생성한다. 0 ~ 3이 나온 경우 각 방향으로 이동, 나머지의 경우 이동하지 않는다.
        if(direction >= 4) return;

        int x = rect.x;
        int y = rect.y;

        switch (direction){
            case 0 -> y -= MOV_DIST;   //북
            case 1 -> x += MOV_DIST;    //동
            case 2 -> y += MOV_DIST;   //남
            case 3 -> x -= MOV_DIST;    //서
        }   //각 랜덤 방향으로 MOV_DIST만큼 이동한다.

        if(x > panelWidth - WIDTH/2){   //당근 이미지가 반 이상 오른쪽 화면을 넘어갔다면
            x = -WIDTH/2;   //왼쪽 화면으로 옮기되, 오른쪽 절반만 보이게
        }else if(x < -WIDTH/2){   //당근 이미지가 왼쪽 화면을 반 이상 넘어갔다면
            x = panelWidth - WIDTH/2;
        }else if(y > panelHeight - HEIGHT/2) {    //당근 이미지가 아래쪽 화면을 반 이상 넘어갔다면
            y = -HEIGHT/2;
        }else{  //위쪽 화면을 반 이상 넘어간 경우
            y = panelHeight - HEIGHT/2;
        }

        rect.x = x;
        rect.y = y;
    }

    //rabbit
    void move(int keyCode){   //사용자가 입력한 키 방향에 따라 객체의 좌표값 변경
        int x = rect.x;
        int y = rect.y;

        switch (keyCode){
            case KeyEvent.VK_UP -> {
                y -= MOV_DIST;
                System.out.println("윗방향키");
            }
            case KeyEvent.VK_RIGHT -> {
                x += MOV_DIST;
                System.out.println("오른방향키");
            }
            case KeyEvent.VK_DOWN -> {
                y += MOV_DIST;
                System.out.println("아래방향키");
            }
            case KeyEvent.VK_LEFT -> {
                x -= MOV_DIST;
                System.out.println("왼쪽방향키");
            }
            default -> {
                return; //다른 키를 입력한 경우 해당 입력 무시
            }
        }

        if(x > panelWidth - WIDTH/2){   //당근 이미지가 오른쪽 화면을 넘어갔다면
            x = -WIDTH/2;
        }else if(x < -WIDTH/2){   //당근 이미지가 왼쪽 화면을 넘어갔다면
            x = panelWidth - WIDTH/2;
        }else if(y > panelHeight - HEIGHT/2) {    //당근 이미지가 아래쪽 화면을 넘어갔다면
            y = -HEIGHT/2;
        }else{  //위쪽 화면을 넘어간 경우
            y = panelHeight - HEIGHT/2;
        }

        rect.x = x;
        rect.y = y;
    }