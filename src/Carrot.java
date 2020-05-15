import java.awt.*;
import java.util.Random;

public class Carrot {
    private static final Rectangle IMG_CARROT = new Rectangle(48, 48);   //당근 이미지 크기
    private static final int MOV_DIST = 8;    //랜덤으로 이동할 때 이동할 좌표값
    private Image imgCarrot;    //당근 이미지
    private int x;  //중심점 x좌표
    private int y;  //중심점 y좌표
    private Random random;
    private int panelWidth;
    private int panelHeight;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Carrot(){ } //Singletone access

    private static class LazyCarrotHolder{
        public static final Carrot INSTANCE = new Carrot();
    }

    public Carrot getInstance(Toolkit toolkit, int panelWidth, int panelHeight){
        if(INSTANCE == null){
            INSTANCE = new Carrot();
            INSTANCE.imgCarrot = toolkit.getImage("res/carrot.png");
            INSTANCE.imgCarrot = imgCarrot.getScaledInstance(IMG_CARROT.width, IMG_CARROT.height, Image.SCALE_SMOOTH);
            random = new Random();
            this.panelWidth = panelWidth;
            this.panelHeight = panelHeight;
        }
        x = random.nextInt(panelWidth-IMG_CARROT.width)+IMG_CARROT.width/2;
        y = random.nextInt(panelHeight-IMG_CARROT.height)+IMG_CARROT.height/2;      //랜덤위치에 생성
        return INSTANCE;
    }   //carrot은 사용자가 먹었을 시 없어졌다가 새로운 위치에 나타나게 할 건데, 매번 새로운 객체를 만드는 것이 아니라 기존에 생성된 객체를 사용

    void move(){
        int direction = random.nextInt(4);  //0 ~ 3까지의 난수를 생성한다.
        switch (direction){
            case 0 -> y -= MOV_DIST;   //북
            case 1 -> x += MOV_DIST;    //동
            case 2 -> y += MOV_DIST;   //남
            case 4 -> x -= MOV_DIST;    //서
        }

        if(x >= panelWidth+IMG_CARROT.width/2){   //당근 이미지가 오른쪽 화면을 넘어갔다면
            x = IMG_CARROT.width/2;
        }else if(x <= -IMG_CARROT.width/2){   //당근 이미지가 왼쪽 화면을 넘어갔다면
            x = panelWidth - IMG_CARROT.width/2;
        }else if(y >= panelHeight + IMG_CARROT.height/2) {    //당근 이미지가 아래쪽 화면을 넘어갔다면
            y = IMG_CARROT.height/2;
        }else{  //위쪽 화면을 넘어간 경우
            y = panelHeight-IMG_CARROT.height/2;
        }
    }

}
