import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
    abstract void moveTick(ArrayList<Entity> gravSources,Player player);
    
    public double getMass()
    {
        return 0;
    }
}
