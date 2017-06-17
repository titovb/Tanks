/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanks;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Ammo implements Runnable{

    private Point centerTower;
    private Point point;
    private int damage;
    private int damageToBase;
    private long speed;
    private double angle;
    private Graphics g;
    private ArrayList<Box> boxes = new ArrayList<Box>();
    private Tank enemyTank;
    private int roznX = 15;
    private int roznY = 145;
    
    public Ammo(Point obj, double angle, Graphics g, Point center, ArrayList<Box> arr, Tank enemyTank){
        point = new Point(Point.rotatePointByCenter(obj, center, angle).getX()+10+5, Point.rotatePointByCenter(obj, center, angle).getY()+140);
        this.angle = angle;
        this.g = g;
        centerTower = new Point(center);
        boxes.addAll(arr);
        damage = 50;
        speed = 1;
        damageToBase = 8;
        this.enemyTank = enemyTank;
    }
    
    
    
    private boolean checkForBox(){
        boolean noBoxes = true;
        for(int i=0;i<boxes.size();i++){
            if(point.getX()+2>=boxes.get(i).getCenter().getX()+15-Box.getWIDTH()/2&&
               point.getX()-2<=boxes.get(i).getCenter().getX()+15+Box.getWIDTH()/2&&
               point.getY()+2>=boxes.get(i).getCenter().getY()+140-Box.getHEIGHT()/2&&
               point.getY()-2<=boxes.get(i).getCenter().getY()+140+Box.getHEIGHT()/2){
                noBoxes = false;
            }
        }
        return noBoxes;
    }
    
    private boolean checkForTank(){
        boolean noTanks = true;
        Line2D k;
        Polygon p = new Polygon();
        p.addPoint((int)enemyTank.getLD().getX()+15, (int)enemyTank.getLD().getY()+140);
        p.addPoint((int)enemyTank.getLU().getX()+15, (int)enemyTank.getLU().getY()+140);
        p.addPoint((int)enemyTank.getRU().getX()+15, (int)enemyTank.getRU().getY()+140);
        p.addPoint((int)enemyTank.getRD().getX()+15, (int)enemyTank.getRD().getY()+140);
        if(p.contains(point.getX(), point.getY())){
            noTanks = false;
            enemyTank.setHealth(enemyTank.getHealth()-damage);
            if(Tanks.getBase().checkForTank(enemyTank)){
                Tanks.getBase().setNumberOfLine(Tanks.getBase().getNumberOfLine()-damageToBase);
            }
            
        }
        return noTanks;
    }
    
    private boolean checkForMove(){
        if (point.getX()<15 || point.getX()>Tanks.SIZE.getX()+10 ||
            point.getY()<140 || point.getY()>Tanks.SIZE.getY()+135 ||
            !checkForBox()){
            SoundPlayer.playSound("sounds\\HittingBoom.wav");
            return false;
        }
        if (!checkForTank()){
            if(enemyTank.getHealth()!=0){
                SoundPlayer.playSound("sounds\\TankHitting.wav", (float)0.9);
            }
            return false;
        }
        return true;
    }
    
    public void move(){
            point.setX(point.getX() + 2*Math.cos(angle-Math.PI/2));//только -Math.PI/2 из-за того, что мы крутим с помощью rotate на угол
            point.setY(point.getY() + 2*Math.sin(angle-Math.PI/2));
    }
    
    public void paintAmmo(){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillArc((int)point.getX()-2, (int)point.getY()-2, 4, 4, 0, 360);
    }
    
    public void paintBoom(int i){
        Image img;
        String adr = "image\\animation of shoot\\" + i + ".png";
        img = Toolkit.getDefaultToolkit().getImage(adr);

        for(int j = 0;j<100;j++){
            g.drawImage(img, (int)point.getX()-(16+i)/2, (int)point.getY() - (16+i)/2, 16+i, 16+i, Tanks.panelGame);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {}
            Tanks.panelGame.repaint();
        }
    }
    
    public void paintBoomTank(int i){
        Image img;
        String adr = "image\\animation of Tank Boom\\" + i +".png";
        img = Toolkit.getDefaultToolkit().getImage(adr);

        for(int j = 0;j<120;j++){
            g.drawImage(img, (int)enemyTank.getCenter().getX()+10-(Tank.getHEIGHT()+i)/2, (int)enemyTank.getCenter().getY()+140 - (Tank.getHEIGHT()+i)/2, Tank.getHEIGHT()+i, Tank.getHEIGHT()+i, Tanks.panelGame);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {}
            Tanks.panelGame.repaint();
        }
       
    }
    
    @Override
    public void run() {
        while(!Tanks.getGameOver()){
            if(checkForMove()){
                move();
                paintAmmo();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {}
                Tanks.panelGame.repaint();
            }
            else{
                if(enemyTank.getHealth()<=0 && !enemyTank.getDead()){
                    SoundPlayer.playSound("sounds\\TankBoom.wav");
                    for(int i=1;i<9;i++){
                        paintBoomTank(i);
                    }
                }
                else{
                    for(int i=1;i<9;i++){
                        paintBoom(i);
                    }
                }
                break;
            }
        }
    }
    
}
