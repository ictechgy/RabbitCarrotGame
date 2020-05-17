import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private Toolkit toolkit;    //이미지를 불러오기 위한 객체
    private int score;  //점수

    private Rabbit rabbit;    //사용자 Rabbit 객체
    private Carrot carrot;    //당근 객체

    private boolean isLoaded;   //최초 로딩인지의 여부 판단 flag

    GamePanel(){
        toolkit = Toolkit.getDefaultToolkit();
        score = 0;
        isLoaded = false; //아직 panel의 사이즈 측정 불가능

        carrot = Carrot.getInstance(toolkit);
        rabbit = Rabbit.getInstance(toolkit);       //각각의 싱글톤 객체 얻어오기
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!isLoaded){                  //화면이 최초 로딩되는 것이라면
            rabbit.setPanelSize(getWidth(), getHeight());
            carrot.setPanelSize(getWidth(), getHeight());

            isLoaded = true;    //로딩 완료
        }
        rabbit.setLocation();   //사용자를 화면 왼쪽 위에 생성
        carrot.setLocation();   //당근을 랜덤위치에 생성

        g.drawImage(rabbit.getImage(), Rabbit.RECT_RABBIT.x, Rabbit.RECT_RABBIT.y, this);
        g.drawImage(carrot.getImage(), Carrot.RECT_CARROT.x, Carrot.RECT_CARROT.y, this);
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
    public void keyPressed(KeyEvent e) {
        rabbit.move(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    void checkCollision(){
        System.out.println(Rabbit.RECT_RABBIT.intersects(Carrot.RECT_CARROT));
        if(Rabbit.RECT_RABBIT.intersects(Carrot.RECT_CARROT)){    //두 객체에 대해 겹치는 부분이 존재한다면
            score += 10;
            carrot.setLocation();   //carrot을 새로운 위치로 새로 생성
        }
    }

    void moveCarrot(){
        carrot.move();
    }

}
