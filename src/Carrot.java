import java.awt.*;
import java.util.Random;

public class Carrot {
    public static final Rectangle RECT_CARROT = new Rectangle(48, 48);
    //당근 이미지 크기에 대한 정보를 가질 것이며 좌표값 x, y에 대한 정보도 가질 것임

    private static final int MOV_DIST = 25;    //랜덤으로 이동할 때 이동할 좌표 변위값
    private Image imgCarrot;    //당근 이미지
    private static final Random random = new Random();

    private int panelWidth;
    private int panelHeight;

    private Carrot(){} //Singletone access

    private static class LazyCarrotHolder{
        public static final Carrot INSTANCE = new Carrot(); //Thread-Safe access by lazy holder
    }

    public static Carrot getInstance(Toolkit toolkit){
        Carrot INSTANCE = LazyCarrotHolder.INSTANCE;
        if(INSTANCE.imgCarrot == null){     //INSTANCE 최초 호출인 경우
            INSTANCE.imgCarrot = toolkit.getImage("res/carrot.png");
            INSTANCE.imgCarrot = INSTANCE.imgCarrot.getScaledInstance(RECT_CARROT.width, RECT_CARROT.height, Image.SCALE_SMOOTH);
            //이미지 값 설정
        }
        return INSTANCE;
    }   //carrot의 생성에 대해서는 싱글톤 방식을 사용한다. Synchronized 대신 그 역할을 JVM에 위임

    void setPanelSize(int panelWidth, int panelHeight){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    void setLocation(){
        System.out.println("panelWidth in Carrot" + panelWidth);
        RECT_CARROT.x = random.nextInt(panelWidth-RECT_CARROT.width)+RECT_CARROT.width/2;
        RECT_CARROT.y = random.nextInt(panelHeight-RECT_CARROT.height)+RECT_CARROT.height/2;      //랜덤위치에 생성
    }

    Image getImage(){
        return imgCarrot;
    }

    void move(){
        int direction = random.nextInt(10);  //0 ~ 9까지의 난수를 생성한다. 0 ~ 3이 나온 경우 각 방향으로 이동, 나머지의 경우 이동하지 않는다.
        if(direction>=4) return;
        switch (direction){
            case 0 -> RECT_CARROT.y -= MOV_DIST;   //북
            case 1 -> RECT_CARROT.x += MOV_DIST;    //동
            case 2 -> RECT_CARROT.y += MOV_DIST;   //남
            case 3 -> RECT_CARROT.x -= MOV_DIST;    //서
        }   //각 랜덤 방향으로 MOV_DIST만큼 이동한다.

        if(RECT_CARROT.x > panelWidth+RECT_CARROT.width/2){   //당근 이미지가 오른쪽 화면을 넘어갔다면
            RECT_CARROT.x = 0;
        }else if(RECT_CARROT.x < -RECT_CARROT.width/2){   //당근 이미지가 왼쪽 화면을 넘어갔다면
            RECT_CARROT.x = panelWidth;
        }else if(RECT_CARROT.y > panelHeight + RECT_CARROT.height/2) {    //당근 이미지가 아래쪽 화면을 넘어갔다면
            RECT_CARROT.y = 0;
        }else{  //위쪽 화면을 넘어간 경우
            RECT_CARROT.y = panelHeight;
        }
    }

}
