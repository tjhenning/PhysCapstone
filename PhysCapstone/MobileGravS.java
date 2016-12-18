import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

class MobileGravS extends Entity
{
    Point2D.Double center;
    double mass;
    double radius;
    double yVelocity=0;
    double xVelocity=0;  
    
    public MobileGravS(double staticX, double staticY, double mass,double xV,double yV)//mass is unitless.
    {        
        center=new Point2D.Double(staticX,staticY);                 
        this.mass=mass;
        yVelocity=yV;
        xVelocity=xV;
        if (mass>0)
        {
            radius=mass;
        }
        else
        {radius=mass*-1;}
            
    }    
    
     public void moveTick(ArrayList<Entity> gravSources,Player player)
    {        
        for(Entity grav:gravSources)
       {
           double distance=center.distance(grav.getCenter());           
           distance=(mass*grav.getMass())/Math.pow(distance,2);
           if (distance<10)
           {
               double angle=Math.atan((center.getY()-grav.getCenter().getY())/(grav.getCenter().getX()-center.getX()));
           
               if(grav.getCenter().getX()-center.getX()<0)
                    {angle+=Math.PI;}               
               yVelocity-=distance*Math.sin(angle)/mass;  
               xVelocity-=distance*Math.cos(angle)/mass;           
           }           
       }
       double distance=center.distance(player.getCenter());           
       distance=(mass*player.getMass())/Math.pow(distance,2);
       if (distance<10)
       {
           double angle=Math.atan((center.getY()-player.getCenter().getY())/(player.getCenter().getX()-center.getX()));
           if(player.getCenter().getX()-center.getX()<0)
                {angle+=Math.PI;}               
           yVelocity-=distance*Math.sin(angle)/mass;  
           xVelocity-=distance*Math.cos(angle)/mass;           
       }           
       center.setLocation(center.getX()-xVelocity,center.getY()+yVelocity);
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
    public void move(double x, double y)
    {
        center=new Point2D.Double(center.getX()+x,center.getY()+y);
        System.out.println("hey:_");
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
}