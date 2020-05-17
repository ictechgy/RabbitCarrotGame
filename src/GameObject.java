import java.awt.*;

public abstract class GameObject {      //게임 객체에 대한 추상클래스
    int x;  //x좌표
    int y;  //y좌표

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(Point point){
        x = point.x;
        y = point.y;
    }
    //getter&setter 및 Point객체를 통해 한번에 위치를 지정하는 메소드

}
