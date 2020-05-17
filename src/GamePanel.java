import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GamePanel extends JPanel implements KeyListener {
    private int score;

    private final Rabbit rabbit;
    private final Image rabbit_image;

    private final Carrot carrot;
    private final Image carrot_image;

    private final Rectangle rabbit_rect;
    private final Rectangle carrot_rect;

    GamePanel(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        score = 0;

        carrot = Carrot.getInstance(toolkit, 700, 700);
        carrot.setLocation();   //랜덤위치에 생성
        rabbit = Rabbit.getInstance(toolkit, 700, 700);
        rabbit.setLocation();   //화면 왼쪽 위에 생성

        carrot_image = carrot.getImage();
        rabbit_image = rabbit.getImage();

        rabbit_rect = Rabbit.IMG_RABBIT.getBounds();
        rabbit_rect.setLocation(rabbit.getX(), rabbit.getY());

        carrot_rect = Carrot.IMG_CARROT.getBounds();
        carrot_rect.setLocation(carrot.getX(), carrot.getY());

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent called!!!");

        g.drawImage(rabbit_image, rabbit.getX(), rabbit.getY(), this);
        g.drawImage(carrot_image, carrot.getX(), carrot.getY(), this);
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
     */

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        rabbit.move(e.getKeyCode());
        rabbit_rect.setLocation(rabbit.getX(), rabbit.getY());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    void checkCollision(){
        if(rabbit_rect.intersects(carrot_rect)){
            score += 10;
            carrot.setLocation();   //새로운 위치로 새로 생성
            carrot_rect.setLocation(carrot.getX(), carrot.getY());
        }
    }

    void moveCarrot(){
        carrot.move();
    }

}
