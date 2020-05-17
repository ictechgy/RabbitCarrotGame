import java.awt.*;

public class Carrot extends GameObject{
    public static final int WIDTH = 48;
    public static final int HEIGHT = 48;    //객체 그림의 너비와 높이

    private int x;
    private int y;
    //위치에 대한 정보는 사각형의 왼쪽 상단을 기준으로 한다.

    private final Image imgCarrot;    //당근 이미지

    private Carrot(){
        imgCarrot = Toolkit.getDefaultToolkit().getImage("res/carrot.png").getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        //이미지 정보 초기화
    } //Singletone access를 위해 생성자를 private 설정

    private static class LazyCarrotHolder{
        public static final Carrot INSTANCE = new Carrot(); //Thread-Safe access by lazy holder
    }

    public static Carrot getInstance(){
        return LazyCarrotHolder.INSTANCE;
    }   //carrot의 생성에 대해서는 싱글톤 방식을 사용한다. Synchronized 대신 LazyHolder를 통해 동기화 문제를 JVM에 떠넘김
    //현재 이 게임에서는 다중 쓰레드에서의 접근을 취하고 있는 형태를 취하고 있지 않다. 따라서 당장은 쓰는 부분이 없음

    @Override
    public void setLocation(Point point){ //당근의 위치 지정 메소드
        x = point.x;
        y = point.y;
    }

    Image getImage(){
        return imgCarrot;
    }   //이미지 반환 메소드

    @Override
    public int getX(){
        return x;
    }
    @Override
    public int getY(){
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
    //private 멤버변수에 대한 getter, setter
}
