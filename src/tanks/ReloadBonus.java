/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.TimerTask;

/**
 *
 * @author 1
 */
public class ReloadBonus extends Bonuse{
    
    public ReloadBonus(Point center) {
        super(center);
    }
    
    public ReloadBonus(double x, double y){
        super(x, y);
    }
    
    @Override
    public void addBonuse(Tank tank) {
        super.addBonuse(tank);
        if(tank.getReloadingTime()==3000){
            tank.setReloadingTime(tank.getReloadingTime()/2);
            java.util.Timer timer = new java.util.Timer();//создание таймера
            timer.schedule(new TimerTask(){//timer, на 3 секунды, по окончанию которого закончится перезарядка
                @Override
                public void run() {
                    tank.setReloadingTime(tank.getReloadingTime()*2);
                }               
            }, 10000);
        }
    }

    @Override
    public void paint(Graphics g) {
        if(!super.active){
            
        }else{
            Image img;
            String adr = "image\\Bonuces\\Reloading.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, (int)center.getX()-Width/2, (int)center.getY()-Height, Width, Height, Tanks.panelGame);
        }
    }
}
