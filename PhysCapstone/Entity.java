import java.awt.geom.Point2D;
import java.awt.Graphics2D;

public abstract class Entity
{
    
    public Entity()
    {
        
    }        
   
    abstract Point2D.Double getCenter();
    abstract double getXL();
    abstract double getYL();
    abstract void goTo(Point2D.Double to);
    
    abstract boolean isInside(Point2D.Double point);
    //abstract boolean isOnBorder(Point2D.Double point);
    abstract void draw(Graphics2D g2); 
}