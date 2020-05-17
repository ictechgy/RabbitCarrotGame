import java.awt.*;
import java.awt.event.KeyEvent;

public class Rabbit {
    public static final Rectangle RECT_RABBIT = new Rectangle(48, 48);
    private static final int MOV_DIST = 20;  //사용자가 이동버튼을 눌렀을 시 한번에 이동할 변위 값
    private Image imgRabbit;

    private int panelWidth;
    private int panelHeight;

    private Rabbit(){}  //singleton

    private static class LazyRabbitHolder{
        public static final Rabbit INSTANCE = new Rabbit();
    }

    public static Rabbit getInstance(Toolkit toolkit){
        Rabbit INSTANCE = LazyRabbitHolder.INSTANCE;
        if(INSTANCE.imgRabbit == null){
            INSTANCE.imgRabbit = toolkit.getImage("res/rabbit.png");
            INSTANCE.imgRabbit = INSTANCE.imgRabbit.getScaledInstance(RECT_RABBIT.width, RECT_RABBIT.height, Image.SCALE_SMOOTH);
        }
        return INSTANCE;
    }

    void setPanelSize(int panelWidth, int panelHeight){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    void setLocation(){
        RECT_RABBIT.x = RECT_RABBIT.width/2;
        RECT_RABBIT.y = RECT_RABBIT.height/2 + 30;
        //Rabbit의 기본 위치는 화면 왼쪽 위 부근.
    }

    Image getImage(){
        return imgRabbit;
    }

    void move(int keyCode){   //사용자가 입력한 키 방향에 따라 객체의 좌표값 변경
        switch (keyCode){
            case KeyEvent.VK_UP -> {
                RECT_RABBIT.y -= MOV_DIST;
                System.out.println("윗방향키");
            }
            case KeyEvent.VK_RIGHT -> {
                RECT_RABBIT.x += MOV_DIST;
                System.out.println("오른방향키");
            }
            case KeyEvent.VK_DOWN -> {
                RECT_RABBIT.y += MOV_DIST;
                System.out.println("아래방향키");
            }
            case KeyEvent.VK_LEFT -> {
                RECT_RABBIT.x -= MOV_DIST;
                System.out.println("왼쪽방향키");
            }
        }

        if(RECT_RABBIT.x > panelWidth+RECT_RABBIT.width/2){   //당근 이미지가 오른쪽 화면을 넘어갔다면
            RECT_RABBIT.x = 0;
        }else if(RECT_RABBIT.x < -RECT_RABBIT.width/2){   //당근 이미지가 왼쪽 화면을 넘어갔다면
            RECT_RABBIT.x = panelWidth;
        }else if(RECT_RABBIT.y > panelHeight + RECT_RABBIT.height/2) {    //당근 이미지가 아래쪽 화면을 넘어갔다면
            RECT_RABBIT.y = 0;
        }else{  //위쪽 화면을 넘어간 경우
            RECT_RABBIT.y = panelHeight;
        }

    }

}
