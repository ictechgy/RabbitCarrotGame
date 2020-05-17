import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class GamePanel extends JPanel implements KeyListener, ContainerListener, ComponentListener, PropertyChangeListener, HierarchyListener, AncestorListener, VetoableChangeListener, HierarchyBoundsListener {
    private int score;

    private final Rabbit rabbit;
    private final Image rabbit_image;

    private final Carrot carrot;
    private final Image carrot_image;

    private final Rectangle rabbit_rect;
    private final Rectangle carrot_rect;

    GamePanel(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        score = 0;

        carrot = Carrot.getInstance(toolkit, 700, 700);
        carrot.setLocation();   //랜덤위치에 생성
        rabbit = Rabbit.getInstance(toolkit, 700, 700);
        rabbit.setLocation();   //화면 왼쪽 위에 생성

        carrot_image = carrot.getImage();
        rabbit_image = rabbit.getImage();

        rabbit_rect = Rabbit.IMG_RABBIT.getBounds();
        rabbit_rect.setLocation(rabbit.getX(), rabbit.getY());

        carrot_rect = Carrot.IMG_CARROT.getBounds();
        carrot_rect.setLocation(carrot.getX(), carrot.getY());

        addContainerListener(this);
        addComponentListener(this);
        addPropertyChangeListener(this);
        addHierarchyListener(this);
        addAncestorListener(this);
        addVetoableChangeListener(this);
        addHierarchyBoundsListener(this);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(rabbit_image, rabbit.getX(), rabbit.getY(), this);
        g.drawImage(carrot_image, carrot.getX(), carrot.getY(), this);
        g.setFont(new Font(null, Font.BOLD, 20));
        g.drawString("Score: " + score + "점", 10, 30);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        rabbit.move(e.getKeyCode());
        rabbit_rect.setLocation(rabbit.getX(), rabbit.getY());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    void checkCollision(){
        if(rabbit_rect.intersects(carrot_rect)){
            score += 10;
            carrot.setLocation();   //새로운 위치로 새로 생성
            carrot_rect.setLocation(carrot.getX(), carrot.getY());
        }
    }

    void moveCarrot(){
        carrot.move();
    }


    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("componentResized");
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println("componentMoved");
    }

    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println("componentShown");
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        System.out.println("componentHidden");
    }

    @Override
    public void componentAdded(ContainerEvent e) {
        System.out.println("componentAdded");
    }

    @Override
    public void componentRemoved(ContainerEvent e) {
        System.out.println("componentRemoved");
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        System.out.println("hierarchyChanged");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propertyChange");
    }

    @Override
    public void ancestorMoved(HierarchyEvent e) {
        System.out.println("ancestorMoved");
    }

    @Override
    public void ancestorResized(HierarchyEvent e) {
        System.out.println("ancestorResized");
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        System.out.println("vetoableChange");

    }

    @Override
    public void ancestorAdded(AncestorEvent event) {
        System.out.println("ancestorAdded");

    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
        System.out.println("ancestorRemoved");

    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
        System.out.println("ancestorMoved");
    }
}
