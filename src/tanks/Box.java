/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanks;

import java.awt.*;
import java.util.ArrayList;

public class Box {
    private Point center;//центр ящика
    private static final int HEIGHT = 46;//высота ящика
    private static final int WIDTH = 46;//ширина ящика
    private Point LU;
    private Point LD;
    private Point RU;
    private Point RD;
    private Polygon p;
    
    public Box(Point obj){
        center = new Point(obj);
        LU = new Point(obj.getX()-WIDTH/2, obj.getY()-HEIGHT/2);
        LD = new Point(obj.getX()-WIDTH/2, obj.getY()+HEIGHT/2);
        RU = new Point(obj.getX()+WIDTH/2, obj.getY()-HEIGHT/2);
        RD = new Point(obj.getX()+WIDTH/2, obj.getY()+HEIGHT/2);
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
    
    public static int getHEIGHT(){
        return HEIGHT;
    }
    
    public static int getWIDTH(){
        return WIDTH;
    }
    
    public static Box randomBox(ArrayList<Box> arr, Bonuse b1, Bonuse b2){
        double x = 0;
        double y = 0;
        boolean ready = false;
        while(!ready){
            boolean wasBreak = false;
            do{
                x = (Tanks.SIZE.getX()-15 - WIDTH/2) * Math.random() + WIDTH/2;
                y = (Tanks.SIZE.getY()-45 - HEIGHT/2) * Math.random() + HEIGHT/2;
            }while((x<150&&y<150) || (x > 1800&& y > 850) || 
                    (b1.getCenter().getX()-b1.getWidth()/2<=x+WIDTH/2&&b1.getCenter().getX()+b1.getWidth()/2>=x-WIDTH/2&&
                      b1.getCenter().getY()-b1.getHeight()/2<=y+HEIGHT/2&&b1.getCenter().getY()+b1.getHeight()/2>=y-HEIGHT/2) ||
                    (b2.getCenter().getX()-b2.getWidth()/2<=x+WIDTH/2&&b2.getCenter().getX()+b2.getWidth()/2>=x-WIDTH/2&&
                      b2.getCenter().getY()-b2.getHeight()/2<=y+HEIGHT/2&&b2.getCenter().getY()+b2.getHeight()/2>=y-HEIGHT/2));
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).getCenter().getX()-WIDTH/2<=x+WIDTH/2&&arr.get(i).getCenter().getX()+WIDTH/2>=x-WIDTH/2&&
                   arr.get(i).getCenter().getY()-HEIGHT/2<=y+HEIGHT/2&&arr.get(i).getCenter().getY()+HEIGHT/2>=y-HEIGHT/2){
                    wasBreak = true;
                    break;
                }
            }
            if(!wasBreak){
                ready = true;
            }
        }
        return new Box(new Point(x, y));
    }
    
    public void paintBox(Graphics g){
        Image img;
        String adr = "image\\fon\\piramida.png";
        img = Toolkit.getDefaultToolkit().getImage(adr);
        
        g.drawImage(img, (int)center.getX()-WIDTH/2, (int)center.getY()-HEIGHT/2, WIDTH, HEIGHT, Tanks.panelGame);
    }
}
