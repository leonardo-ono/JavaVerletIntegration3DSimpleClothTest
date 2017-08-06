
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author leo
 */
public class Ball {

    public View view;
    public Vec3 position = new Vec3();
    public double radius;

    public Ball(View view, double radius, double x, double y, double z) {
        this.view = view;
        this.radius = radius;
        position.set(x, y, z);
    }
    
    private final Vec3 vTmp = new Vec3();
    
    public Vec3 getCollisionNormalPenetration(Point p) {
        vTmp.set(p.position);
        vTmp.sub(position);
        double size = vTmp.getSize();
        if (size <= radius) {
            double dif = radius - size;
            vTmp.normalize();
            vTmp.scale(dif);
            return vTmp;
        }
        else {
            return null;
        }
    }
    
    public void draw3D(Graphics2D g) {
        g.setColor(Color.RED);
        double ax = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * (position.x / -position.z);
        double ay = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * (position.y / -position.z);
        double bx = View.CAMERA_TO_PROJECTION_PLANE_DISTANCE * ((position.x + radius) / -position.z);
        double size = bx - ax;
        g.drawOval((int) (ax - size), (int) (ay - size), (int) (size * 2), (int) (size * 2));
    }
    
}
