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
import java.awt.geom.Line2D;

public class DrawingPanel extends JPanel
{
    Entity selected;
    int selecting=0;//0=none,1=object,2=player,3=new gravity,4=new block,5=new mob     
    ArrayList<Entity> groundBlocks;
    ArrayList<Entity> gravSources;
    Player player=new Player(new Point2D.Double(400,400),25);   
    Color current;    
    int currentLevel=1;
    static double wobble=1;
    boolean freeze=false;
    
        
    public DrawingPanel()
    {        
        groundBlocks=new ArrayList<Entity>();
        gravSources=new ArrayList<Entity>();
        setBackground(Color.WHITE);       
        current=new Color(0,0,0);
        addMouseListener(new ClickListener());
        addMouseMotionListener(new MovementListener());
        setFocusable(true);
        addKeyListener(new KeysListener());
        loadLevel(1);
        player.addVector(0,0);
        
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
        for (Entity grav:gravSources)
        {            
            grav.draw(g2);
        }
        
        for (Entity Entity:groundBlocks)
        {            
           Entity.draw(g2);
        }        
        
        player.draw(g2);
        // System.out.println(current);
    }
    public void nextFrame()
    {        
        if (!freeze)
        {
             for (Entity mob:gravSources)
            {                            
                mob.moveTick(gravSources,player); 
            }    
            for (Entity Entity:groundBlocks)
            {            
                 if(player.isHitNextFrame(Entity))
                 {
                     player.hitWall(Entity.getCenter().getX(),Entity.getCenter().getY(),Entity.getXL()/2,Entity.getYL()/2);
                   
                 }
                 boolean b=player.isOnTopOfNextFrame(Entity);
                 int top=(int)(Entity.getCenter().getY()-Entity.getYL());                
            }         
            
            player.moveTick(gravSources);                 
        }
        repaint();
            requestFocusInWindow();
    }   
    public void loadLevel(int which)
    {
        groundBlocks=new ArrayList<Entity>();
        gravSources=new ArrayList<Entity>();        
        
        if (which==0)
        {
            //gravSources.add(new GravitySource(400,400,40));
            //gravSources.add(new GravitySource(200,200,-40));
            //groundBlocks.add(new StationaryBlock(400,400,100,100));
            gravSources.add(new MobileGravS(200,200,25,-1,0));
        }
        else if (which==1)
        {
            gravSources.add(new MobileGravS(200,200,300,-.7,0));
        }
        else if (which==2)
        {
              groundBlocks.add(new StationaryBlock(250,25,500,50));   
              groundBlocks.add(new StationaryBlock(250,475,500,50));   
              groundBlocks.add(new StationaryBlock(25,250,50,500));  
              groundBlocks.add(new StationaryBlock(475,250,50,500));    
              gravSources.add(new MobileGravS(200,200,100,-1,0));
              gravSources.add(new GravitySource(600,600,-40));
        }
        else if (which==3)
        {
              groundBlocks.add(new StationaryBlock(250,25,500,50));   
              groundBlocks.add(new StationaryBlock(250,475,500,50));   
              groundBlocks.add(new StationaryBlock(25,250,50,500));  
              groundBlocks.add(new StationaryBlock(475,250,50,500)); 
              gravSources.add(new GravitySource(25,25,-50));
              gravSources.add(new GravitySource(475,25,-50));
              gravSources.add(new GravitySource(475,475,-50));
              gravSources.add(new GravitySource(25,475,-50));
              gravSources.add(new GravitySource(250,250,30));
              gravSources.add(new GravitySource(25,250,40));
              gravSources.add(new GravitySource(475,250,40));
              gravSources.add(new GravitySource(250,475,40));
              gravSources.add(new GravitySource(250,25,40));
              player.goTo(300,300);
        }        
    }
    public class KeysListener implements KeyListener
    {
        
        public void keyPressed(KeyEvent e)
        {              
          if (e.getKeyCode()==KeyEvent.VK_SPACE)
            {                
                if (freeze)
                {freeze=false;selecting=0;selected=null;}
                else
                freeze=true;
            }
            else if (e.getKeyCode()==KeyEvent.VK_P&&freeze)
            {
                selecting=2;
                selected=null;
                System.out.println("Click to send ball back onscreen.");
            }
            else if (e.getKeyCode()==KeyEvent.VK_G&&freeze)
            {
                selecting=3;
                selected=null;                              
            }
            else if (e.getKeyCode()==KeyEvent.VK_B&&freeze)
            {
                selecting=4;
                selected=null;                              
            }
            else if (e.getKeyCode()==KeyEvent.VK_M&&freeze)
            {
                selecting=5;
                selected=null;                              
            }
            if(selected!=null){
                if (e.getKeyCode()==KeyEvent.VK_ESCAPE&&freeze)
                {
                    for (int i=0;i<gravSources.size();i++)
                    {
                        if(gravSources.get(i)==selected)
                        {
                            gravSources.remove(i);
                        }
                    }
                    for (int i=0;i<groundBlocks.size();i++)
                    {
                        if(groundBlocks.get(i)==selected)
                        {
                            groundBlocks.remove(i);
                        }
                    }
                }
                else if (e.getKeyCode()==KeyEvent.VK_UP&&freeze)
                {
                    selected.changeMass(3);
                    selected.changeHeight(5);                  
                }
                else if (e.getKeyCode()==KeyEvent.VK_DOWN&&freeze)
                {
                    selected.changeMass(-3);
                    selected.changeHeight(-5);
                }
                else if (e.getKeyCode()==KeyEvent.VK_LEFT&&freeze)
                {                
                    selected.changeLength(-5);
                }
                else if (e.getKeyCode()==KeyEvent.VK_RIGHT&&freeze)
                {                
                    selected.changeLength(5);
                }
                repaint();
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
            if (freeze)
            {
                Point2D.Double point=new Point2D.Double(e.getPoint().getX(),e.getPoint().getY());
                if(player.isInside(point))
                {
                    selected=null;
                    selecting=2;
                }
                else
                {
                    for(int i=0;i<gravSources.size();i++)         
                    {
                        if (gravSources.get(i).isInside(point))
                        {
                            selected=gravSources.get(i);
                            selecting=1;                        
                        }        
                    }
                    for(int i=0;i<groundBlocks.size();i++)         
                    {
                        if (groundBlocks.get(i).isInside(point))
                        {
                            selected=groundBlocks.get(i);
                            selecting=1;                        
                        }        
                    }
                }        
                requestFocusInWindow();
            }
            
        }
        public void mouseReleased(MouseEvent e)
        {            
            //selected=null;
        }
    }
    public class MovementListener implements MouseMotionListener
    {
        public void mouseDragged(MouseEvent e)
        {
            
            if (freeze)
            {
                Point2D.Double point=new Point2D.Double(e.getPoint().getX(),e.getPoint().getY());   
                        
                if (selecting==2)
                {
                    if(selected==null)
                    {
                        player.setVector(player.getX()-e.getPoint().getX(),e.getPoint().getY()-player.getY());
                    }
                }                     
                else if (selecting==1)
                    {                
                        selected.goTo(point);                                
                    }
                    else if (selecting==3)
                    {                        
                        if(selected==null)
                        {
                            selected=new GravitySource(e.getPoint().getX(),e.getPoint().getY(),30);
                            gravSources.add(selected);                        
                        }
                        selected.goTo(point);
                    }           
                    else if (selecting==4)
                    {                        
                        if(selected==null)
                        {
                            selected=new StationaryBlock(e.getPoint().getX(),e.getPoint().getY(),100,100);
                            groundBlocks.add(selected);                        
                        }
                        selected.goTo(point);
                    }     
                    else if (selecting==5)
                    {                        
                        if(selected==null)
                        {
                            selected=new MobileGravS(e.getPoint().getX(),e.getPoint().getY(),30,0,0);
                            gravSources.add(selected);                        
                        }
                        selected.goTo(point);
                    }     
                
                requestFocusInWindow();
            }
        }
        public void mouseMoved(MouseEvent e)
        {}
     }
}
