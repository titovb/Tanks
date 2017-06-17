
package tanks;

import java.awt.*;

public class Tower {
    private Point center;//центр башни
    private Weapon w;//орудие
    private Color color;//цвет башни
    private int WIDTH = 26;//ширина
    private int HEIGHT = 26;//высота
    private double speedOfRotate = 0;
    private double toRotate;
    
    public Tower(Point obj, Color color){
        center = new Point(obj);
        w = new Weapon(new Point(center.getX(), center.getY()), color);
        toRotate = 0;
        this.color = color;
    }
    
    public void changeCenter(Point obj){
        center.setX(obj.getX());
        center.setY(obj.getY());
        w.changePoints(obj);
    }
    
    public Weapon getWeapon(){
        return w;
    }
    
    public void setSpeedOfRotate(double speed){
        speedOfRotate = speed;
    }
    
    public double getSpeedOfRotate(){
        return speedOfRotate;
    }
    
    public void speedOfRotateUp(){
        if(speedOfRotate<0.06)
            speedOfRotate+=0.005;
    }
    
    public void speedOfRotateDown(){
        if(speedOfRotate>0)
            speedOfRotate-=0.005;
    }
    
    public void rotateTowerToLeft(){
        toRotate-=speedOfRotate;
        if(toRotate<=-2*Math.PI) toRotate = 0;
        w.rotateWeaponToLeft(speedOfRotate);
    }
    
    public void rotateTowerToLeft(double angle){
        toRotate-=angle;
        if(toRotate<=-2*Math.PI) toRotate = 0;
        w.rotateWeaponToLeft(angle);
    }
    
    public void rotateTowerToRight(){
        toRotate+=speedOfRotate;
        if(toRotate>=2*Math.PI) toRotate = 0;
        w.rotateWeaponToRight(speedOfRotate);
    }
    
     public void rotateTowerToRight(double angle){
         toRotate+=angle;
        if(toRotate>=2*Math.PI) toRotate = 0;
        w.rotateWeaponToRight(angle);
    }
    
    public void paintTower(Graphics g1, boolean dead){
        if(!dead){
            Graphics2D g = (Graphics2D)g1;

            Image img;
            String adr = "";
            if(color.equals(Color.RED))
                adr = "image\\RedTank\\RedTower.png";
            if(color.equals(Color.BLUE))
                adr = "image\\BlueTank\\BlueTower.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.rotate(toRotate, (int)center.getX(), (int)center.getY());
            g.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-HEIGHT/2, WIDTH, HEIGHT, Tanks.panelGame);
            g.rotate(-toRotate, (int)center.getX(), (int)center.getY());
        
            w.paintWeapon(g, false);
        }
        else{
            
            Graphics2D g = (Graphics2D)g1;

            Image img;
            String adr = "image\\Tank after Boom\\BoomTower.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.rotate(toRotate, (int)center.getX(), (int)center.getY());
            g.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-HEIGHT/2, WIDTH, HEIGHT, Tanks.panelGame);
            g.rotate(-toRotate, (int)center.getX(), (int)center.getY());
        
            w.paintWeapon(g, true);
        }
        
    }
}
