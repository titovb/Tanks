
package tanks;

import java.awt.*;
import java.awt.geom.*;

public class Base {
    
    private Point center;
    private int HEIGHT;
    private int WIDTH;
    private int typeTank;
    private int numberOfLine;
    private boolean threadDone;
    
    private int lineHeight;
    private int lineWidth;
    
    public Base(Point center){
        this.center = new Point(center);
        HEIGHT = 200;
        WIDTH = 200;
        lineHeight = 15;
        lineWidth = 200;
        typeTank = 0;
        numberOfLine = 0;
        threadDone = false;
    }
    
    public Base(double x, double y){
        this.center = new Point(x, y);
        HEIGHT = 200;
        WIDTH = 200;
        lineHeight = 15;
        lineWidth = 200;
        typeTank = 0;
        numberOfLine = 0;
        threadDone = false;
    }
    
    public synchronized boolean checkForTank(Tank t){
        
        Polygon p = new Polygon();
        p.addPoint((int)center.getX()-WIDTH/2, (int)center.getY());
        p.addPoint((int)center.getX(), (int)center.getY()+HEIGHT/2);
        p.addPoint((int)center.getX()+WIDTH/2, (int)center.getY());
        p.addPoint((int)center.getX(), (int)center.getY()-HEIGHT/2);
        
        if(p.contains((int)t.getLU().getX(), (int)t.getLU().getY()) || 
           p.contains((int)t.getLD().getX(), (int)t.getLD().getY()) ||
           p.contains((int)t.getRD().getX(), (int)t.getRD().getY()) ||
           p.contains((int)t.getRU().getX(), (int)t.getRU().getY()))
            return true;
        return false;
       
    }
    
    public void setThreadDone(boolean obj){
        threadDone = obj;
    }
    
    public boolean getThreadDone(){
        return threadDone;
    }
    
    public void setTypeTank(int type){
        typeTank = type;
    }
    
    public int getTypeTank(){
        return typeTank;
    }
    
    public void setNumberOfLine(int number){
        if(number<0)
            numberOfLine = 0;
        else
            numberOfLine = number;
    }
    
    public int getWinner(){
        if(typeTank == 1 && numberOfLine >= 27)
            return 1;
        if(typeTank == 2 && numberOfLine >= 27)
            return 2;
        return 0;
    }
    
    public synchronized void upNumberOfLine(){
        numberOfLine++;
    }
    
    public synchronized int getNumberOfLine(){
        return numberOfLine;
    }    
    public synchronized void paintTimeLine(Graphics g){
        Image img;
        
        String adr = "";
        switch(typeTank){
            case 0:
                adr = "image\\Base\\neutralLine.png";
                break;
            case 1:
                if(numberOfLine == 0)
                    adr = "image\\Base\\neutralLine.png";
                else
                    adr = "image\\Base\\BlueLine\\" + numberOfLine + ".png";
                break;
            case 2:
                if(numberOfLine == 0)
                    adr = "image\\Base\\neutralLine.png";
                else
                    adr = "image\\Base\\RedLine\\" + numberOfLine + ".png";
                break;
            default:
                break;
        }
        
        
        
        img = Toolkit.getDefaultToolkit().getImage(adr);
      
        g.drawImage(img, (int)(Tanks.SIZE2.getX()/2-lineWidth/2 + 5), (int)(Tanks.SIZE2.getY()-5-lineHeight) , lineWidth, lineHeight, Tanks.panelInfo);
    }
    
    public void paintBase(Graphics g){
        Image img;
        
        String adr = "";
        switch(typeTank){
            case 0:
                adr = "image\\Base\\BaseN.png";
                break;
            case 1:
                adr = "image\\Base\\BaseB.png";
                break;
            case 2:
                adr = "image\\Base\\BaseR.png";
                break;
            default:
                break;
        }
        
        img = Toolkit.getDefaultToolkit().getImage(adr);
      
        g.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-HEIGHT/2, WIDTH, HEIGHT, Tanks.panelGame);
        
        
    }
    
}
