import java.awt.*;

public class Rabbit extends GameObject{
    public static final int WIDTH = 48;
    public static final int HEIGHT = 48;        //토끼 객체의 너비와 높이

    private int x;
    private int y;
    private final Image imgRabbit;

    private Rabbit(){
        imgRabbit = Toolkit.getDefaultToolkit().getImage("res/rabbit.png").getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        //이미지 불러오기
    }  //싱글톤을 위해 생성자 private화

    private static class LazyRabbitHolder{
        public static final Rabbit INSTANCE = new Rabbit();
    }

    public static Rabbit getInstance(){
        return LazyRabbitHolder.INSTANCE;
    }//Carrot에서의 getInstance와 같다.

    @Override
    public void setLocation(Point point){
        x = point.x;
        y = point.y;
    }   //Point객체를 통해 한번에 위치 지정

    Image getImage(){
        return imgRabbit;
    }   //이미지 반환

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
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
    //getter & setter
}
