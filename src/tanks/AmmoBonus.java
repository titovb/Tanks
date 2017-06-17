
package tanks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class AmmoBonus extends Bonuse{
    
    private int numberOfAmmo;
    
    public AmmoBonus(Point center) {
        super(center);
        numberOfAmmo = 5;
    }
    
    public AmmoBonus(double x, double y){
        super(x, y);
        numberOfAmmo = 5;
    }
    
    @Override
    public void addBonuse(Tank tank) {
        super.addBonuse(tank);
        tank.addAmmo(numberOfAmmo);
    }

    @Override
    public void paint(Graphics g) {
        if(!super.active){
            
        }else{
            Image img;
            String adr = "image\\Bonuces\\Ammo.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, (int)center.getX()-Width/2, (int)center.getY()-Height, Width, Height, Tanks.panelGame);
        }
    }
    
}
