
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author leonardo
 */
public class Stick {

    public Point a;
    public Point b;
    public double size;
    public boolean visible;
    private final Vec3 vTmp = new Vec3();
    
    public Stick(Point a, Point b, boolean visible) {
        this.a = a;
        this.b = b;
        this.visible = visible;

        vTmp.set(b.position);
        vTmp.sub(a.position);
        this.size = vTmp.getSize();
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void update() {
        vTmp.set(b.position);
        vTmp.sub(a.position);
        double currentSize = vTmp.getSize();
        double dif = (currentSize - size) * 0.5;
        vTmp.normalize();
        vTmp.scale(dif * 1);
        if (!a.isPinned()) {
            a.position.add(vTmp);
        }
        if (!b.isPinned()) {
            b.position.sub(vTmp);
        }
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.drawLine((int) a.position.x, (int) a.position.y, (int) b.position.x, (int) b.position.y);
    }
    
    public void draw3D(Graphics2D g) {
        g.setColor(Color.BLUE);
        double ax = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * (a.position.x / -a.position.z);
        double ay = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * (a.position.y / -a.position.z);
        double bx = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * (b.position.x / -b.position.z);
        double by = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * (b.position.y / -b.position.z);
        g.drawLine((int) ax, (int) ay, (int) bx, (int) by);
    }
    
}
