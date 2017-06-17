
package tanks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.TimerTask;

public class SpeedBonus extends Bonuse{
    public SpeedBonus(Point center) {
        super(center);
    }
    
    public SpeedBonus(double x, double y){
        super(x, y);
    }
    
    @Override
    public void addBonuse(Tank tank) {
        super.addBonuse(tank);
        if(tank.getMaxSpeedOfTank()==2 && tank.getMaxSpeedOfRotate()==0.04){
            tank.setMaxSpeedOfTank(tank.getMaxSpeedOfTank()*2);
            tank.setMaxSpeedOfRotate(tank.getMaxSpeedOfRotate()*2);
            java.util.Timer timer = new java.util.Timer();//создание таймера
            timer.schedule(new TimerTask(){//timer, на 3 секунды, по окончанию которого закончится перезарядка
                @Override
                public void run() {
                    tank.setMaxSpeedOfTank(tank.getMaxSpeedOfTank()/2);
                    tank.setMaxSpeedOfRotate(tank.getMaxSpeedOfRotate()/2);
                }               
            }, 10000);
        }
    }

    @Override
    public void paint(Graphics g) {
        if(!super.active){
            
        }else{
            Image img;
            String adr = "image\\Bonuces\\SpeedUp.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, (int)center.getX()-Width/2, (int)center.getY()-Height, Width, Height, Tanks.panelGame);
        }
    }
}
