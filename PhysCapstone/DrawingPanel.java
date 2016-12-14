import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class DrawingPanel extends JPanel
{
    Entity selected;
    boolean selecting=false;
    ArrayList<Entity> groundBlocks;
    ArrayList<GravitySource> gravSources;
    Player player=new Player(new Point2D.Double(400,250),25);   
    Color current;    
    int currentLevel=1;
    static double wobble=1;
    boolean freeze=false;
    
        
    public DrawingPanel()
    {        
        groundBlocks=new ArrayList<Entity>();
        gravSources=new ArrayList<GravitySource>();
        setBackground(Color.WHITE);       
        current=new Color(0,0,0);
        //addMouseListener(new ClickListener());
        //addMouseMotionListener(new MovementListener());
        setFocusable(true);
        addKeyListener(new KeysListener());
        loadLevel(0);
        player.addVector(1,1);
        
    }
    
    public Color getColor()
    {
        return current;
    }
    
    public Dimension getPreferredSize()
    {
        Dimension d=new Dimension(350,300);
        return d;
    }       
   
    public void nextLevel()
    {
        
           
        player.goTo(100,300);
        loadLevel(currentLevel);
        
    }
           
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        
        double position=0;               
        for (GravitySource grav:gravSources)
        {            
            grav.draw(g2);
        }
        
        for (Entity Entity:groundBlocks)
        {            
           
        }
        
        player.draw(g2);
        // System.out.println(current);
    }
    public void nextFrame()
    {        
        if (!freeze)
        {
             for (GravitySource enemy:gravSources)
            {                            
                
            }    
            for (Entity Entity:groundBlocks)
            {            
                if(player.isHitNextFrame(Entity))
                {
                    player.hitWall(Entity.getCenter().getX(),Entity.getCenter().getY(),Entity.getXL(),Entity.getYL(),Entity);
                  
                }
                boolean b=player.isOnTopOfNextFrame(Entity);
                int top=(int)(Entity.getCenter().getY()-Entity.getYL());                
            }         
            
            player.moveTick(gravSources);     
            
            repaint();
            requestFocusInWindow();
        }
    }   
    public void loadLevel(int which)
    {
        groundBlocks=new ArrayList<Entity>();
        gravSources=new ArrayList<GravitySource>();        
        
        if (which==0)
        {
            gravSources.add(new GravitySource(200,300,50));
            gravSources.add(new GravitySource(200,200,-40));
        }
        else if (which==2)
        {
                  
        }
        else if (which==3)
        {
                          
        }
        else if (which==4)
        {
//             groundBlocks.add(new RectObj(new Point2D.Double(1000,800),1000,75,Color.BLACK));
//             groundBlocks.add(new RectObj(new Point2D.Double(1000,125),1000,75,Color.BLACK));
//             gravSources.add(new FlyingEnemy(Color.ORANGE,600,400,150,.001,40));
//             gravSources.add(new FlyingEnemy(Color.ORANGE,1100,0,550,.0005,40));
//             gravSources.add(new MatedEnemy(Color.RED,groundBlocks.get(1),75,.001,75));
//             gravSources.add(new MatedEnemy(Color.RED,groundBlocks.get(0),35,.002,5));
//             gravSources.add(new Killplane(Color.RED,1350,675,50));
//             gravSources.add(new Killplane(Color.RED,1350,575,50));
//             gravSources.add(new Killplane(Color.RED,1350,250,50));
//             gravSources.add(new Killplane(Color.RED,900,675,50));                       
//             gravSources.add(new Killplane(Color.RED,1850,625,100));  
//             gravSources.add(new Killplane(Color.RED,1850,475,50));   
//             
//             finish=new RectObj(new Point2D.Double(2000,100),50,1000,Color.BLACK);
        }
    }
    public class KeysListener implements KeyListener
    {
        
        public void keyPressed(KeyEvent e)
        {              
          if (e.getKeyCode()==KeyEvent.VK_SPACE)
            {                
                if (freeze)
                freeze=false;
                else
                freeze=true;
            }
            requestFocusInWindow();           
        }
        public void keyReleased(KeyEvent e)
        {
            
            
            
            
        }
        public void keyTyped(KeyEvent e)
        {
            
        }
    }
     public class ClickListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {}
        public void mouseEntered(MouseEvent e)
        {}
        public void mouseExited(MouseEvent e)
        {}
        public void mousePressed(MouseEvent e)
        {            
            
            Point2D.Double point=new Point2D.Double(e.getPoint().getX(),e.getPoint().getY());
            for(int i=0;i<gravSources.size();i++)
         
            {
                if (gravSources.get(i).isInside(point))
                {
                    selected=gravSources.get(i);
                }        
            }
            requestFocusInWindow();
        }
        public void mouseReleased(MouseEvent e)
        {
            //canDrag=false;
            //nowResize=false;
            requestFocusInWindow();
        }
    }
    public class MovementListener implements MouseMotionListener
    {
        public void mouseDragged(MouseEvent e)
        {
            Point2D.Double point=new Point2D.Double(e.getPoint().getX(),e.getPoint().getY());
//             if (dragMode)
//             {                
//                 if (canDrag)
//                 {
//                     lastActiveEntity.goTo(point.getX(),point.getY());
//                     repaint();
//                 }
//             }
//             else
//             {                
//                 if (nowResize)
//                 {                    
//                     lastActiveEntity.setRadius(lastActiveEntity.getCenter().distance(point));
//                     repaint();
//                 }
//             }     
            requestFocusInWindow();
        }
        public void mouseMoved(MouseEvent e)
        {}
     }
}
