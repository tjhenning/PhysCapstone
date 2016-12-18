import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

class GravitySource extends Entity
{
    Point2D.Double center;
    double mass;
    double radius;
    public GravitySource(double staticX, double staticY, double mass)//mass is unitless.
    {        
        center=new Point2D.Double(staticX,staticY);                 
        this.mass=mass;
        if (mass>0)
        {
            radius=mass;
        }
        else
        {radius=mass*-1;}
            
    }    
    public void draw(Graphics2D g2)
    {
        if (mass>0)
        {
            radius=mass;
        }
        else
        {radius=mass*-1;}
        if (mass>0)
            g2.setColor(Color.BLUE);
        else
            g2.setColor(Color.RED);
        Ellipse2D.Double rect=new Ellipse2D.Double((int)(center.getX()-(radius/2)),(int)(center.getY()-(radius/2)),radius,radius);
        g2.fill(rect);
        g2.setColor(Color.RED);
        Ellipse2D.Double rect2=new Ellipse2D.Double((int)(center.getX()),(int)(center.getY()),2,2);
        g2.fill(rect2);        
    }
    
    boolean isInside(Point2D.Double point)
    {
        return (Math.abs(center.getX()-point.getX())<radius)&&(Math.abs(center.getY()-point.getY())<radius);
    }                 
    
    public double getXL()
    {        
        return radius;
    }
    public double getYL()
    {        
        return radius;
    }
    
    public void goTo(Point2D.Double to)
    {
        center=to;        
    }

    public Point2D.Double getCenter()
    {
        return center;
    }
    public int interactionType()
    {
        return 1;
    }
    public double getMass()
    {
        return mass;
    }
    
    public void changeMass(double delta)
    {
        mass+=delta;
    }
    
     public void moveTick(ArrayList<Entity> gravSources,Player player)
    {}
}