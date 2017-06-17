
package tanks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Weapon {
    private Point pointA;//точка крепления к башне
    private Point pointB;
    private Point centerTower;
    private double toRotate;
    private Ammo ammo;
    private Color color;
    private int HEIGHT = 28;
    private int WIDTH = 6;
    
    
    public Weapon(Point obj, Color color){
        centerTower = new Point(obj);
        pointA = new Point(obj.getX(), obj.getY()-10);
        pointB = new Point(pointA.getX(), pointA.getY()-HEIGHT);
        toRotate = 0;
        this.color = color;
    }
    
    public Point getPointB(){
        return pointB;
    }
    
    public Point getPointA(){
        return pointA;
    }
    
    public void changePoints(Point obj){
        centerTower.setX(obj.getX());
        centerTower.setY(obj.getY());
        
        pointA.setX(obj.getX());
        pointA.setY(obj.getY()-10);
        
        pointB.setX(pointA.getX());
        pointB.setY(pointA.getY()-HEIGHT);
    }
    
    public void rotateWeaponToLeft(){
        toRotate-=0.06;
        if(toRotate<=-2*Math.PI) toRotate = 0;
    }
    
    public void rotateWeaponToLeft(double angle){
        toRotate-=angle;
        if(toRotate<=-2*Math.PI) toRotate = 0;
    }
    
    public void rotateWeaponToRight(){
        toRotate+=0.06;
        if(toRotate>=2*Math.PI) toRotate = 0;
    }
    
    public void rotateWeaponToRight(double angle){
        toRotate+=angle;
        if(toRotate>=2*Math.PI) toRotate = 0;
    }
    
    
    public void shoot(Graphics g, ArrayList<Box> arr, Tank enemyTank){
            ammo = new Ammo(pointB, toRotate, g, centerTower, arr, enemyTank);
            new Thread(ammo).start();
    }
    
    public void paintWeapon(Graphics g1, boolean dead){
        if(!dead){
            Graphics2D g = (Graphics2D)g1;
        
            Image img;
            String adr = "";
            if(color.equals(Color.RED))
                adr = "image\\RedTank\\RedWeapon.png";
            if(color.equals(Color.BLUE))
                adr = "image\\BlueTank\\BlueWeapon.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            BufferedImage scaled = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        
            g.rotate(toRotate, centerTower.getX(), centerTower.getY());
            g.drawImage(img, (int)pointA.getX()-WIDTH/2, (int)pointA.getY()-HEIGHT, WIDTH, HEIGHT, Tanks.panelGame);
            g.rotate(-toRotate, centerTower.getX(), centerTower.getY());
        }
        else{
            Graphics2D g = (Graphics2D)g1;
        
            Image img;
            String adr = "image\\Tank after Boom\\BoomWeapon.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.rotate(toRotate, centerTower.getX(), centerTower.getY());
            g.drawImage(img, (int)pointA.getX()-WIDTH/2, (int)pointA.getY()-HEIGHT, WIDTH, HEIGHT, Tanks.panelGame);
            g.rotate(-toRotate, centerTower.getX(), centerTower.getY());
        }
    }
}
