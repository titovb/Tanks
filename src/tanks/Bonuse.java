
package tanks;

import java.awt.Graphics;

public abstract class Bonuse {
    protected Point center;
    protected int Height;
    protected int Width;
    protected Point LU;
    protected Point LD;
    protected Point RD;
    protected Point RU;
    protected boolean active;
    
    public Bonuse(Point center){
        this.center = new Point(center);
        Height = 30;
        Width = 30;
        LU = new Point(center.getX()-Width/2, center.getY()-Height/2);
        LD = new Point(center.getX()-Width/2, center.getY()+Height/2);
        RD = new Point(center.getX()+Width/2, center.getY()+Height/2);
        RU = new Point(center.getX()+Width/2, center.getY()-Height/2);
        active = true;
    }
    
    public Bonuse(double x, double y){
        this.center = new Point(x, y);
        Height = 30;
        Width = 30;
        LU = new Point(center.getX()-Width/2, center.getY()-Height/2);
        LD = new Point(center.getX()-Width/2, center.getY()+Height/2);
        RD = new Point(center.getX()+Width/2, center.getY()+Height/2);
        RU = new Point(center.getX()+Width/2, center.getY()-Height/2);
        active = true;
    }
    
    public boolean getActive(){
        return active;
    }
    
    public Point getLU(){
        return LU;
    }
    
    public Point getLD(){
        return LD;
    }
    
    public Point getRU(){
        return RU;
    }
    
    public Point getRD(){
        return RD;
    }
    
    public Point getCenter(){
        return center;
    }
    
    public int getHeight(){
        return Height;
    }
    
    public int getWidth(){
        return Width;
    }
    
    public void addBonuse(Tank tank){
        active = false;
    }
    
    public void paint(Graphics g){
    }
}
