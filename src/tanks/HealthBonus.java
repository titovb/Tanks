/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author 1
 */
public class HealthBonus extends Bonuse {

    private int HPAdd;
    
    public HealthBonus(Point center) {
        super(center);
        HPAdd = 50;
    }
    
    public HealthBonus(double x, double y){
        super(x, y);
        HPAdd = 50;
    }
    
    public int getHPAdd(){
        return HPAdd;
    }
    
    @Override
    public void addBonuse(Tank tank) {
        super.addBonuse(tank);
        if(tank.getHealth()<150)
            tank.setHealth(tank.getHealth()+HPAdd);
    }

    @Override
    public void paint(Graphics g) {
        if(!super.active){
            
        }else{
            Image img;
            String adr = "image\\Bonuces\\Heal.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, (int)center.getX()-Width/2, (int)center.getY()-Height, Width, Height, Tanks.panelGame);
        }
    }
    
}
