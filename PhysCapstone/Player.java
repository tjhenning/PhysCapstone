
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class Player
{
    Point2D.Double center;
    double radius;
    Rectangle rect;
    Color color;
    double yVelocity=0;
    double xVelocity=0;    
    public Player(Point2D.Double center, double radius)
    {
        this.center=center;
        this.radius=radius;          
    }
    
    public void moveTick(ArrayList<GravitySource> gravSources)
    {
       for(GravitySource grav:gravSources)
       {
           double distance=center.distance(grav.getCenter());
           
           distance=(25*grav.getMass())/Math.pow(distance,2);
           if (distance<7)
           {
           double angle=Math.atan((center.getY()-grav.getCenter().getY())/(grav.getCenter().getX()-center.getX()));
           
           if(grav.getCenter().getX()-center.getX()<0)
                {angle+=Math.PI;}               
           yVelocity-=distance*Math.sin(angle);  
           xVelocity-=distance*Math.cos(angle);
           
           }
           //System.out.println(distance);
       }
       center.setLocation(center.getX()-xVelocity,center.getY()+yVelocity);
       //System.out.println(yVelocity);
    }    
    public void changeVelocity(double XV,double YV)
    {        
       
    } 
    public void bounce(int behavior)//0 is bounce, 1 is tangible, 2 is intangible
    {
        
    }       
    
    void draw(Graphics2D g2)
    {                
        
        g2.setColor(Color.BLACK);
        Ellipse2D.Double rect2=new Ellipse2D.Double((int)(center.getX()-radius),(int)(center.getY()-radius),(int)radius*2,(int)radius*2);
        g2.fill(rect2);
   
        g2.setColor(Color.GRAY);  
        Ellipse2D.Double rect=new Ellipse2D.Double((int)(center.getX()-radius+1),(int)(center.getY()-radius+1),(int)radius*2-2,(int)radius*2-4);
        g2.fill(rect);      
        g2.setColor(Color.RED);
        Ellipse2D.Double rect3=new Ellipse2D.Double((int)(center.getX()),(int)(center.getY()),2,2);
        g2.fill(rect3);

        
    }
    public void stop()
    {
        xVelocity=0;
        yVelocity=0;
    }
            
    public double getRadius()
    {
        return radius;
    }
    public double getYV()
    {
        return yVelocity;
    }    
    public double getXV()
    {
        return xVelocity;
    }    
    public double getY()
    {
        return center.getY();
    }
    public double getX()
    {
        return center.getX();
    }
    public void addVector(double XV,double YV)
    {
        xVelocity+=XV;
        yVelocity+=YV;
    }
    public void setVector(double XV,double YV)
    {
        xVelocity=XV;
        yVelocity=YV;
    }
    
    public void move(double x, double y)
    {
        center=new Point2D.Double(center.getX()+x,center.getY()+y);
    }    
    public void goTo(double x, double y)
    {
        center=new Point2D.Double(x,y);
    }
    
    public boolean isInside(Point2D.Double point)
    {
        return (center.distance(point)<=25);
    }
    
    public boolean isOnTopOfNextFrame(Entity on)
    {
        Point2D.Double center2=new Point2D.Double(center.getX()+xVelocity,center.getY()-yVelocity);        
        return(yVelocity<0)&&((on.isInside(new Point2D.Double(center2.getX()+radius,center2.getY()+radius)))||(on.isInside(new Point2D.Double(center2.getX()-radius,center2.getY()+radius))));
        
    }        
    public boolean isHitNextFrame(Entity on)
    {
        Point2D.Double center2=new Point2D.Double(center.getX()+xVelocity,center.getY()-yVelocity);        
        return (on.isInside(new Point2D.Double(center2.getX()+radius+1,center2.getY()+(radius*3/4))))||(on.isInside(new Point2D.Double(center2.getX()-radius-1,center2.getY()+(radius*.75))))||(on.isInside(new Point2D.Double(center2.getX()-radius-1,center2.getY()-radius)))||(on.isInside(new Point2D.Double(center2.getX()+radius+1,center2.getY()-radius)));
        
    }        
    public void hitWall(double objX, double objY, double xLength,double yLength, Entity object)
    {
        if(objX-xLength>center.getX()&&objY+yLength>center.getY()-radius+1)
        {            
            goTo(objX-xLength-radius,center.getY());
            xVelocity=-.25;            
            
        }
        else if (objX+xLength<center.getX()&&objY+yLength>center.getY()-radius+1)
        {            
            goTo(objX+xLength+radius,center.getY());
            xVelocity=.25;            
        }
        else if (objY+yLength<center.getY())
        {           
            goTo(center.getX(),objY+yLength+radius);
            yVelocity=0;
            //object.hitFromBottom();
        }   
    }
        
}

