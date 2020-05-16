import java.awt.*;
import java.awt.event.KeyEvent;

public class Rabbit {
    private static final Rectangle IMG_RABBIT = new Rectangle(48, 48);
    private static final int MOV_DIST = 8;  //사용자가 이동버튼을 눌렀을 시 한번에 이동할 변위 값
    private Image imgRabbit;
    private int x;
    private int y;
    private int panelWidth;
    private int panelHeight;

    private Rabbit(){}  //singleton

    private static class LazyRabbitHolder{
        public static final Rabbit INSTANCE = new Rabbit();
    }

    public static Rabbit getInstance(Toolkit toolkit, int panelWidth, int panelHeight){
        Rabbit INSTANCE = LazyRabbitHolder.INSTANCE;
        if(INSTANCE.imgRabbit == null){
            INSTANCE.imgRabbit = toolkit.getImage("res/rabbit.png");
            INSTANCE.imgRabbit = INSTANCE.imgRabbit.getScaledInstance(IMG_RABBIT.width, IMG_RABBIT.height, Image.SCALE_SMOOTH);
            INSTANCE.panelWidth = panelWidth;
            INSTANCE.panelHeight = panelHeight;
        }
        return INSTANCE;
    }

    void setLocation(){
        x = IMG_RABBIT.width/2;
        y = IMG_RABBIT.height/2;
        //Rabbit의 기본 위치는 화면 왼쪽 위.
    }

    void move(KeyEvent e){   //사용자가 입력한 키 방향에 따라 객체의 좌표값 변경
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> y -= MOV_DIST;
            case KeyEvent.VK_RIGHT -> x += MOV_DIST;
            case KeyEvent.VK_DOWN -> y += MOV_DIST;
            case KeyEvent.VK_LEFT -> x -= MOV_DIST;
        }

        if(x > panelWidth+IMG_RABBIT.width/2){   //당근 이미지가 오른쪽 화면을 넘어갔다면
            x = 0;
        }else if(x < -IMG_RABBIT.width/2){   //당근 이미지가 왼쪽 화면을 넘어갔다면
            x = panelWidth;
        }else if(y > panelHeight + IMG_RABBIT.height/2) {    //당근 이미지가 아래쪽 화면을 넘어갔다면
            y = 0;
        }else{  //위쪽 화면을 넘어간 경우
            y = panelHeight;
        }

    }

}
