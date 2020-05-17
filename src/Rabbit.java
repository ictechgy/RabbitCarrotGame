import java.awt.*;
import java.awt.event.KeyEvent;

public class Rabbit {
    public static final int WIDTH = 48;
    public static final int HEIGHT = 48;

    private int x;
    private int y;
    private final Image imgRabbit;

    private Rabbit(){
        imgRabbit = Toolkit.getDefaultToolkit().getImage("res/rabbit.png").getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }  //singleton

    private static class LazyRabbitHolder{
        public static final Rabbit INSTANCE = new Rabbit();
    }

    public static Rabbit getInstance(){
        return LazyRabbitHolder.INSTANCE;
    }

    void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    Image getImage(){
        return imgRabbit;
    }

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
}
