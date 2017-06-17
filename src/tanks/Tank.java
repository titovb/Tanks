
package tanks;

import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.io.File;

public class Tank {

    private Point center;
    private double toRotate;
    private Point LU;
    private Point LD;
    private Point RU;
    private Point RD;
    private double speedOfTank;
    private double speedOfRotate;
    private final static int HEIGHT = 44;
    private final static int WIDTH = 28;
    private boolean reloading = false;
    private ArrayList<Box> boxes = new ArrayList<Box>();
    private Tower t;
    private Color color;
    private int health;
    private boolean dead = false;
    private int reloadingTime;
    private double MaxSpeedOfTank;
    private double MaxSpeedOfRotate;
    private int numberOfAmmo;
    private SoundPlayer soundMove;
    private SoundPlayer soundMoveTower;

    public Tank(Point obj, ArrayList<Box> arr, Color color){
        center = new Point(obj);
        t = new Tower(obj, color);
        toRotate = 0;
        LU = new Point(center.getX() - WIDTH/2, center.getY() - HEIGHT/2);
        LD = new Point(center.getX() - WIDTH/2, center.getY() + HEIGHT/2);
        RU = new Point(center.getX() + WIDTH/2, center.getY() - HEIGHT/2);
        RD = new Point(center.getX() + WIDTH/2, center.getY() + HEIGHT/2);
        boxes.addAll(arr);
        this.color = color;
        health = 150;
        reloadingTime = 3000;
        speedOfTank = 0;
        speedOfRotate = 0;
        MaxSpeedOfTank = 2;
        MaxSpeedOfRotate = 0.04;
        numberOfAmmo = 10;
        soundMove = new SoundPlayer(new File("sounds\\TankMoving.wav"));
        soundMoveTower = new SoundPlayer(new File("sounds\\RotateTower.wav"));
    }

    public int getAmmo(){
        return numberOfAmmo;
    }
    
    public void addAmmo(int number){
        numberOfAmmo+=number;
    }
    
    public void setReloadingTime(int reloading){
        reloadingTime = reloading;
    }

    public int getReloadingTime(){
        return reloadingTime;
    }

    public static int getWIDTH(){
        return WIDTH;
    }
  
    public static int getHEIGHT(){
        return HEIGHT;
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
    
    public double getMaxSpeedOfTank(){
        return MaxSpeedOfTank;
    }
    
    public void setMaxSpeedOfTank(double speed){
        MaxSpeedOfTank = speed;
    }

    public double getMaxSpeedOfRotate(){
        return MaxSpeedOfRotate;
    }
    
    public void setMaxSpeedOfRotate(double speed){
        MaxSpeedOfRotate = speed;
    }
    
    public Tower getTower(){
        return t;
    }

    public void setReloading(boolean obj){
        reloading = obj;
    }

    public boolean getReloading(){
        return reloading;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
    
    public Point getCenter(){
        return center;
    }
    
    public void shoot(Graphics g, Tank enemyTank){
        if(!reloading && !dead && numberOfAmmo > 0){
            t.getWeapon().shoot(g, boxes, enemyTank);
            numberOfAmmo--;
        }
    }
    
    
    public void setSpeedOfRotate(double speed){
        speedOfRotate = speed;
    }
    
    public double getSpeedOfRotate(){
        return speedOfRotate;
    }
    
    public void speedOfRotateUp(){
        if(speedOfRotate <= MaxSpeedOfRotate)
             speedOfRotate+=0.005;
    }
    
    public void speedOfRotateDown(){
        if(speedOfRotate >= 0)
             speedOfRotate-=0.005;
    }
    
     private boolean checkForRotateRight(){
        checkForBonuse(LD, RU);
        return LD.getX()>=0&&LD.getX()<=Tanks.SIZE.getX()&&
               LD.getY()>=0&&LD.getY()<=Tanks.SIZE.getY()&&
               RU.getX()>=0&&RU.getX()<=Tanks.SIZE.getX()&&
               RU.getY()>=0&&RU.getY()<=Tanks.SIZE.getY()&&
               checkForBoxes(LD, RU)&&!dead;
    }
    
    private boolean checkForRotateLeft(){
        checkForBonuse(LU, RD);
        return LU.getX()>=0&&LU.getX()<=Tanks.SIZE.getX()&&
               LU.getY()>=0&&LU.getY()<=Tanks.SIZE.getY()&&
               RD.getX()>=0&&RD.getX()<=Tanks.SIZE.getX()&&
               RD.getY()>=0&&RD.getY()<=Tanks.SIZE.getY()&&
               checkForBoxes(LU, RD)&&!dead;
    }
    
    public void startingRotateTankToRight(){
        if(checkForRotateRight()){
            toRotate+=0.04;
            if(toRotate>=2*Math.PI) toRotate = 0;
            t.rotateTowerToRight(0.04);
            
            LU = new Point(Point.rotateToRightPointByCenter(LU, center));
            LD = new Point(Point.rotateToRightPointByCenter(LD, center));
            RU = new Point(Point.rotateToRightPointByCenter(RU, center));
            RD = new Point(Point.rotateToRightPointByCenter(RD, center));
        }
    }
    
    public void startingRotateTankToLeft(){
        if(checkForRotateLeft()){
            toRotate-=0.04;
            if(toRotate<=-2*Math.PI) toRotate = 0;
            t.rotateTowerToLeft(0.04);
            
            LU = new Point(Point.rotateToLeftPointByCenter(LU, center));
            LD = new Point(Point.rotateToLeftPointByCenter(LD, center));
            RU = new Point(Point.rotateToLeftPointByCenter(RU, center));
            RD = new Point(Point.rotateToLeftPointByCenter(RD, center));
        }
    }
    
    public synchronized void rotateTankToRight(){
        if(checkForRotateRight()){
            toRotate+=speedOfRotate;
            if(toRotate>=2*Math.PI) toRotate = 0;
            t.rotateTowerToRight(speedOfRotate);
            
            LU = new Point(Point.rotateToRightPointByCenter(LU, center, speedOfRotate));
            LD = new Point(Point.rotateToRightPointByCenter(LD, center, speedOfRotate));
            RU = new Point(Point.rotateToRightPointByCenter(RU, center, speedOfRotate));
            RD = new Point(Point.rotateToRightPointByCenter(RD, center, speedOfRotate));
        }
    }
    
    public synchronized void rotateTankToLeft(){
        if(checkForRotateLeft()){
            toRotate-=speedOfRotate;
            if(toRotate<=-2*Math.PI) toRotate = 0;
            t.rotateTowerToLeft(speedOfRotate);
            
            
            LU = new Point(Point.rotateToLeftPointByCenter(LU, center, speedOfRotate));
            LD = new Point(Point.rotateToLeftPointByCenter(LD, center, speedOfRotate));
            RU = new Point(Point.rotateToLeftPointByCenter(RU, center, speedOfRotate));
            RD = new Point(Point.rotateToLeftPointByCenter(RD, center, speedOfRotate));
        }
    }
    
    private boolean checkForBoxes(Point obj1, Point obj2){
        boolean noBoxes = true;
        for(int i=0;i<boxes.size();i++){
            Polygon p = new Polygon();
            p.addPoint((int)boxes.get(i).getLD().getX(), (int)boxes.get(i).getLD().getY());
            p.addPoint((int)boxes.get(i).getLU().getX(), (int)boxes.get(i).getLU().getY());
            p.addPoint((int)boxes.get(i).getRU().getX(), (int)boxes.get(i).getRU().getY());
            p.addPoint((int)boxes.get(i).getRD().getX(), (int)boxes.get(i).getRD().getY());
            
            Line2D l = new Line2D.Double();
            l.setLine(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
            
            if(p.contains(obj1.getX(), obj1.getY())
                    ||
               p.contains(obj2.getX(), obj2.getY())
                    ||
               l.intersectsLine((int)boxes.get(i).getLD().getX(), (int)boxes.get(i).getLD().getY(), (int)boxes.get(i).getLU().getX(), (int)boxes.get(i).getLU().getY())
                    ||
               l.intersectsLine((int)boxes.get(i).getLU().getX(), (int)boxes.get(i).getLU().getY(), (int)boxes.get(i).getRU().getX(), (int)boxes.get(i).getRU().getY())
                    ||
               l.intersectsLine((int)boxes.get(i).getRU().getX(), (int)boxes.get(i).getRU().getY(), (int)boxes.get(i).getRD().getX(), (int)boxes.get(i).getRD().getY())
                    ||
               l.intersectsLine((int)boxes.get(i).getRD().getX(), (int)boxes.get(i).getRD().getY(), (int)boxes.get(i).getLD().getX(), (int)boxes.get(i).getLD().getY()))
                    noBoxes = false;
        }
        return noBoxes;
    }
    
    public boolean checkForBonuse(Point obj1, Point obj2){
        if(Tanks.bonuse1.getActive()){
            Polygon p = new Polygon();
            p.addPoint((int)Tanks.bonuse1.getLD().getX(), (int)Tanks.bonuse1.getLD().getY());
            p.addPoint((int)Tanks.bonuse1.getLU().getX(), (int)Tanks.bonuse1.getLU().getY());
            p.addPoint((int)Tanks.bonuse1.getRU().getX(), (int)Tanks.bonuse1.getRU().getY());
            p.addPoint((int)Tanks.bonuse1.getRD().getX(), (int)Tanks.bonuse1.getRD().getY());
            
            Line2D l = new Line2D.Double();
            l.setLine(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
            
            if(p.contains(obj1.getX(), obj1.getY())
                    ||
                p.contains(obj2.getX(), obj2.getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse1.getLD().getX(), (int)Tanks.bonuse1.getLD().getY(), (int)Tanks.bonuse1.getLU().getX(), (int)Tanks.bonuse1.getLU().getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse1.getLU().getX(), (int)Tanks.bonuse1.getLU().getY(), (int)Tanks.bonuse1.getRU().getX(), (int)Tanks.bonuse1.getRU().getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse1.getRU().getX(), (int)Tanks.bonuse1.getRU().getY(), (int)Tanks.bonuse1.getRD().getX(), (int)Tanks.bonuse1.getRD().getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse1.getRD().getX(), (int)Tanks.bonuse1.getRD().getY(), (int)Tanks.bonuse1.getLD().getX(), (int)Tanks.bonuse1.getLD().getY())){
                SoundPlayer.playSound("sounds\\Bonuse.wav");
                Tanks.bonuse1.addBonuse(this);
                return true;
            }
        }
           
        if(Tanks.bonuse2.getActive()){
            Polygon p = new Polygon();
            p.addPoint((int)Tanks.bonuse2.getLD().getX(), (int)Tanks.bonuse2.getLD().getY());
            p.addPoint((int)Tanks.bonuse2.getLU().getX(), (int)Tanks.bonuse2.getLU().getY());
            p.addPoint((int)Tanks.bonuse2.getRU().getX(), (int)Tanks.bonuse2.getRU().getY());
            p.addPoint((int)Tanks.bonuse2.getRD().getX(), (int)Tanks.bonuse2.getRD().getY());
            
            Line2D l = new Line2D.Double();
            l.setLine(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
            
            if(p.contains(obj1.getX(), obj1.getY())
                    ||
                p.contains(obj2.getX(), obj2.getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse2.getLD().getX(), (int)Tanks.bonuse2.getLD().getY(), (int)Tanks.bonuse2.getLU().getX(), (int)Tanks.bonuse2.getLU().getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse2.getLU().getX(), (int)Tanks.bonuse2.getLU().getY(), (int)Tanks.bonuse2.getRU().getX(), (int)Tanks.bonuse2.getRU().getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse2.getRU().getX(), (int)Tanks.bonuse2.getRU().getY(), (int)Tanks.bonuse2.getRD().getX(), (int)Tanks.bonuse2.getRD().getY())
                    ||
                l.intersectsLine((int)Tanks.bonuse2.getRD().getX(), (int)Tanks.bonuse2.getRD().getY(), (int)Tanks.bonuse2.getLD().getX(), (int)Tanks.bonuse2.getLD().getY())){
                SoundPlayer.playSound("sounds\\Bonuse.wav");
                Tanks.bonuse2.addBonuse(this);
                return true;
            }
        }
        return false;             
    }
    
    private boolean checkForMoveUp(){
        checkForBonuse(LU,RU);
        return LU.getX()>=0&&LU.getX()<=Tanks.SIZE.getX()&&
               LU.getY()>=0&&LU.getY()<=Tanks.SIZE.getY()&&
               RU.getX()>=0&&RU.getX()<=Tanks.SIZE.getX()&&
               RU.getY()>=0&&RU.getY()<=Tanks.SIZE.getY()&&
               checkForBoxes(LU, RU)&&!dead;
    }
    
    private boolean checkForMoveDown(){
        checkForBonuse(LD, RD);
        return LD.getX()>=0&&LD.getX()<=Tanks.SIZE.getX()&&
               LD.getY()>=0&&LD.getY()<=Tanks.SIZE.getY()&&
               RD.getX()>=0&&RD.getX()<=Tanks.SIZE.getX()&&
               RD.getY()>=0&&RD.getY()<=Tanks.SIZE.getY()&&
               checkForBoxes(LD, RD)&&!dead;
    }
    
    public void setSpeedOfTank(double speed){
        speedOfTank = speed;
    }
    
    public double getSpeedOfTank(){
        return speedOfTank;
    }
    
    public void speedOfTankUp(){
        if(speedOfTank <= MaxSpeedOfTank)
             speedOfTank+=0.1;
    }
    
    public void speedOfTankDown(){
        if(speedOfTank > 0)
             speedOfTank-=0.1;
    }
    
    public synchronized void moveUp(){
        if(checkForMoveUp()){
            if(!soundMove.isPlaying()&&speedOfTank!=0){
                soundMove.setVolume(0.7f);
                soundMove.play();
            }
            center.setX(center.getX()+speedOfTank*Math.cos(toRotate-Math.PI/2));
            center.setY(center.getY()+speedOfTank*Math.sin(toRotate-Math.PI/2));
            
            LU.setX(LU.getX()+speedOfTank*Math.cos(toRotate-Math.PI/2));
            LU.setY(LU.getY()+speedOfTank*Math.sin(toRotate-Math.PI/2));
            LD.setX(LD.getX()+speedOfTank*Math.cos(toRotate-Math.PI/2));
            LD.setY(LD.getY()+speedOfTank*Math.sin(toRotate-Math.PI/2));
            RU.setX(RU.getX()+speedOfTank*Math.cos(toRotate-Math.PI/2));
            RU.setY(RU.getY()+speedOfTank*Math.sin(toRotate-Math.PI/2));
            RD.setX(RD.getX()+speedOfTank*Math.cos(toRotate-Math.PI/2));
            RD.setY(RD.getY()+speedOfTank*Math.sin(toRotate-Math.PI/2));
            
            t.changeCenter(center);
        }
    }
    
   
    
    public synchronized void moveDown(){
        if(checkForMoveDown()){
            if(!soundMove.isPlaying()&&speedOfTank!=0){
                soundMove.setVolume(0.7f);
                soundMove.play();
            }
            center.setX(center.getX()+(3*speedOfTank/4)*Math.cos(toRotate+Math.PI/2));
            center.setY(center.getY()+(3*speedOfTank/4)*Math.sin(toRotate+Math.PI/2));
            
            LU.setX(LU.getX()+3*speedOfTank/4*Math.cos(toRotate+Math.PI/2));
            LU.setY(LU.getY()+3*speedOfTank/4*Math.sin(toRotate+Math.PI/2));
            LD.setX(LD.getX()+3*speedOfTank/4*Math.cos(toRotate+Math.PI/2));
            LD.setY(LD.getY()+3*speedOfTank/4*Math.sin(toRotate+Math.PI/2));
            RU.setX(RU.getX()+3*speedOfTank/4*Math.cos(toRotate+Math.PI/2));
            RU.setY(RU.getY()+3*speedOfTank/4*Math.sin(toRotate+Math.PI/2));
            RD.setX(RD.getX()+3*speedOfTank/4*Math.cos(toRotate+Math.PI/2));
            RD.setY(RD.getY()+3*speedOfTank/4*Math.sin(toRotate+Math.PI/2));
            t.changeCenter(center);
        }
    }
    
    public void rotateTowerToLeft(){
        if(!dead){
            if(!soundMoveTower.isPlaying()){
                soundMoveTower.setVolume(0.75f);
                soundMoveTower.play();
            }
            t.rotateTowerToLeft();
        }
    }
    
    public void rotateTowerToRight(){
        if(!dead){
            if(!soundMoveTower.isPlaying()){
                soundMoveTower.setVolume(0.75f);
                soundMoveTower.play();
            }
            t.rotateTowerToRight();
        }
    }
    
    public double getRotate(){
        return toRotate;
    }
    
    public void paintTank(Graphics g){
        
        
        
        if(health > 0){
            Graphics2D g2d = (Graphics2D)g;
        
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Image img;
            String adr = "";
        
            if(color.equals(Color.RED))
                adr = "image\\RedTank\\RedTank.png";
            if(color.equals(Color.BLUE))
                adr = "image\\BlueTank\\BlueTank.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g2d.rotate(toRotate, (int)center.getX(), (int)center.getY());
            g2d.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-HEIGHT/2, WIDTH, HEIGHT, Tanks.panelGame);
            g2d.rotate(-toRotate, (int)center.getX(), (int)center.getY());
            
            t.paintTower(g, false);
            
            switch(health){
                case 150:
                   adr = "image\\HP\\MAX.png"; 
                   break;
                case 100:
                   adr = "image\\HP\\MID.png"; 
                   break;
                case 50:
                   adr = "image\\HP\\LOW.png"; 
                   break;
                default:
                   break;
            }
            
            img = Toolkit.getDefaultToolkit().getImage(adr);
            
            g2d.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-3*HEIGHT/4+5, 30, 5, Tanks.panelGame);
            
            if(!reloading&&numberOfAmmo>0)
                adr = "image\\HP\\Shoot.png";
            else
                adr = "image\\HP\\Reloading.png";
            
            img = Toolkit.getDefaultToolkit().getImage(adr);
            
            g2d.drawImage(img, (int)center.getX()+WIDTH/2+5, (int)center.getY()-3*HEIGHT/4+2, 10, 10, Tanks.panelGame);
            
            g2d.setColor(Color.DARK_GRAY);
            String ammo = "" + numberOfAmmo;
            Font f = new Font("Calibri", Font.BOLD, 10);
            g2d.setFont(f);
            g2d.drawString(ammo, (int)center.getX()-WIDTH/2-11, (int)center.getY()-HEIGHT/2-2);
            
        }
        else{
            dead = true;
            Graphics2D g2d = (Graphics2D)g;
        
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Image img;
            String adr = "image\\Tank after Boom\\BoomTank.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g2d.rotate(toRotate, (int)center.getX(), (int)center.getY());
            g2d.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-HEIGHT/2, WIDTH, HEIGHT, Tanks.panelGame);
            g2d.rotate(-toRotate, (int)center.getX(), (int)center.getY());
            
            t.paintTower(g, true);
        }
        
        /*g.setColor(Color.BLACK);
        Polygon p = new Polygon();
        p.addPoint((int)LU.getX(), (int)LU.getY());
        p.addPoint((int)LD.getX(), (int)LD.getY());
        p.addPoint((int)RD.getX(), (int)RD.getY());
        p.addPoint((int)RU.getX(), (int)RU.getY());
        
        g.fillPolygon(p);*/
        
    }
    
    public synchronized boolean getDead(){
        return dead;
    }
    
    public void setDead(boolean dead){
        this.dead = dead;
    }

    public Color getColor() {
        return this.color;
    }
    
    public SoundPlayer getSoundMove(){
        return soundMove;
    }
    
    public SoundPlayer getSoundMoveTower(){
        return soundMoveTower;
    }
}
