import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class StationaryBlock extends Entity
{
    Point2D.Double center;
    int width;
    int height;
    Rectangle rect;
    boolean hasMoved=false;
    public StationaryBlock(double staticX, double staticY, int width, int height)
    {        
        center=new Point2D.Double(staticX,staticY);                 
        this.width=width;
        this.height=height;
        rect=new Rectangle((int)center.getX()-width/2,(int)center.getY()-height/2,width,height);
    }    
    public void draw(Graphics2D g2)
    {
        if (width<0)
        {width=1;}
        if(height<0)
        {height=1;}
        if (hasMoved)
        {
            rect=new Rectangle((int)center.getX()-width/2,(int)center.getY()-height/2,width,height);
            hasMoved=false;
        }
        g2.setColor(Color.BLACK);    

        g2.draw(rect);    
    }
    
    boolean isInside(Point2D.Double point)
    {
        return (Math.abs(center.getX()-point.getX())<width/2)&&(Math.abs(center.getY()-point.getY())<height/2);
    }                 
    
    public double getXL()
    {        
        return width;
    }
    public double getYL()
    {        
        return height;
    }
    
    public void goTo(Point2D.Double to)
    {
        center=to;        
        hasMoved=true;
    }

    public Point2D.Double getCenter()
    {
        return center;
    }
    public int interactionType()
    {
        return 1;
    }    
    
     public void moveTick(ArrayList<Entity> gravSources,Player player)
    {}
    
    public void changeHeight(double delta)
    {
        height+=delta;hasMoved=true;
    }
    public void changeLength(double delta)
    {
        width+=delta;hasMoved=true;
    }
}