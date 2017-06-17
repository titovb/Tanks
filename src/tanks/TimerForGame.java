
package tanks;

import java.awt.*;

public class TimerForGame implements Runnable{
    private int m;
    private int s;
    
    public TimerForGame(){
        m = 0;
        s = 0;
    }
    
    public void paintTimer(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.LIGHT_GRAY);
        String time = "";
        if(s<10)
            time = "" + m + ":0" + s;
        else
            time = "" + m + ":" + s;
        Font f = new Font("Calibri", Font.BOLD, 50);
        g2d.setFont(f);
        g2d.drawString(time, (int)Tanks.SIZE2.getX()/2-30, (int)Tanks.SIZE2.getY()-50);
    }
    
    public synchronized int getM(){
        return m;
    }

    @Override
    public void run() {
        while(m<=5){
            if(Tanks.getGameOver()) break;
            if(s==60){
                s = 0;
                m++;
            }
            if(s%30==0){
                double rand = Math.random();
                
                if(rand>=0&&rand<0.25)
                    Tanks.bonuse1 = new HealthBonus(Tanks.bonuse1.getCenter());
                if(rand>=0.25&&rand<0.50)
                    Tanks.bonuse1 = new ReloadBonus(Tanks.bonuse1.getCenter());
                if(rand>=0.50&&rand<=0.75)    
                    Tanks.bonuse1 = new SpeedBonus(Tanks.bonuse1.getCenter());
                if(rand>=0.75&&rand<=1)    
                    Tanks.bonuse1 = new AmmoBonus(Tanks.bonuse1.getCenter());
                
                rand = Math.random();
                
                if(rand>=0&&rand<0.25)
                    Tanks.bonuse2 = new HealthBonus(Tanks.bonuse2.getCenter());
                if(rand>=0.25&&rand<0.50)
                    Tanks.bonuse2 = new ReloadBonus(Tanks.bonuse2.getCenter());
                if(rand>=0.50&&rand<=0.75)    
                    Tanks.bonuse2 = new SpeedBonus(Tanks.bonuse2.getCenter());
                if(rand>=0.75&&rand<=1)    
                    Tanks.bonuse2 = new AmmoBonus(Tanks.bonuse2.getCenter());
                
                Tanks.panelGame.repaint();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
            s++;
            Tanks.panelInfo.repaint();
        }
    }
}
