import java.awt.*;
import java.awt.event.KeyEvent;

public class Rabbit {
    private static final Rectangle IMG_RABBIT = new Rectangle(48, 48);
    private static final int MOV_DIST = 8;  //사용자가 이동버튼을 눌렀을 시 한번에 이동할 변위 값
    private Image imgRabbit;
    private int x;
    private int y;
    private static Rabbit INSTANCE;
    private int panelWidth;
    private int panelHeight;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private Rabbit(){}  //singleton

    public Rabbit getInstance(Toolkit toolkit, int panelWidth, int panelHeight){
        if(INSTANCE == null){
            INSTANCE = new Rabbit();
            INSTANCE.imgRabbit = toolkit.getImage("res/rabbit.png");
            INSTANCE.imgRabbit = imgRabbit.getScaledInstance(IMG_RABBIT.width, IMG_RABBIT.height, Image.SCALE_SMOOTH);
            this.panelWidth = panelWidth;
            this.panelHeight = panelHeight;
        }
        return INSTANCE;
    }

    void move(KeyEvent e){   //사용자가 입력한 키 방향에 따라 객체의 좌표값 변경
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> y -= MOV_DIST;
            case KeyEvent.VK_RIGHT -> x += MOV_DIST;
            case KeyEvent.VK_DOWN -> y += MOV_DIST;
            case KeyEvent.VK_LEFT -> x -= MOV_DIST;
        }



    }



}
