import java.awt.*;
import java.util.Random;

public class Carrot {
    public static final Rectangle IMG_CARROT = new Rectangle(48, 48);
    private Image imgCarrot;
    private int x;
    private int y;
    private static Carrot INSTANCE;

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

    private Carrot(){ } //Singletone

    public Carrot getInstance(int panelWidth, int panelHeight){
        if(INSTANCE == null){
            INSTANCE = new Carrot();
            INSTANCE.imgCarrot = imgCarrot.getScaledInstance(IMG_CARROT.width, IMG_CARROT.height, Image.SCALE_SMOOTH);
        }
        return INSTANCE;
    }   //carrot은 사용자가 먹었을 시 없어졌다가 새로운 위치에 나타나게 할 건데, 매번 새로운 객체를 만드는 것이 아니라 기존에 생성된 객체를 사용
    //위치에 대한 정보는 panel의 너비와 높이를 알아야하므로 panel Class에서 설정하도록 한다.

    void move(){
        Random random = new Random();
        int direction = random.nextInt(4);  //0 ~ 3까지의 난수를 생성한다.
        switch (direction){
            case 0 -> y -= IMG_CARROT.height; break;
            case 1 -> x += IMG_CARROT.width; break;
            case 2 ->
        }
    }

}
