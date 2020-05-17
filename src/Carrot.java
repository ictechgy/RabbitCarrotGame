import java.awt.*;

public class Carrot {
    public static final int WIDTH = 48;
    public static final int HEIGHT = 48;    //객체 그림의 너비와 높이

    private int x;
    private int y;
    //위치에 대한 정보는 사각형의 왼쪽 상단을 기준으로 한다.

    private final Image imgCarrot;    //당근 이미지

    private Carrot(){
        imgCarrot = Toolkit.getDefaultToolkit().getImage("res/carrot.png").getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        //이미지 정보 초기화
    } //Singletone access를 위한 private 설정

    private static class LazyCarrotHolder{
        public static final Carrot INSTANCE = new Carrot(); //Thread-Safe access by lazy holder
    }

    public static Carrot getInstance(){
        return LazyCarrotHolder.INSTANCE;
    }   //carrot의 생성에 대해서는 싱글톤 방식을 사용한다. Synchronized 대신 LazyHolder를 통해 그 역할을 JVM에 위임

    void setLocation(Point point){ //당근의 위치 지정 메소드
        x = point.x;
        y = point.y;
    }

    Image getImage(){
        return imgCarrot;
    }

    int getX(){
        return x;
    }
    int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
