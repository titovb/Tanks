
package tanks;

public class Point {
    private double x;
    private double y;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Point(Point obj){
        this(obj.x, obj.y);
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public static Point rotateToRightPointByCenter(Point obj, Point center){
        return new Point(center.getX()+(obj.getX()-center.getX())*Math.cos(0.04)+
                (center.getY()-obj.getY())*Math.sin(0.04),
                         center.getY()+(obj.getX()-center.getX())*Math.sin(0.04)+
                (obj.getY()-center.getY())*Math.cos(0.04));
    }
    
    public static Point rotateToLeftPointByCenter(Point obj, Point center){
        return new Point(center.getX()+(obj.getX()-center.getX())*Math.cos(-0.04)+
                (center.getY()-obj.getY())*Math.sin(-0.04),
                         center.getY()+(obj.getX()-center.getX())*Math.sin(-0.04)+
                (obj.getY()-center.getY())*Math.cos(-0.04));
    }
    
    public static Point rotatePointByCenter(Point obj, Point center, double angle){
        return new Point(center.getX()+(obj.getX()-center.getX())*Math.cos(angle)+
                (center.getY()-obj.getY())*Math.sin(angle),
                         center.getY()+(obj.getX()-center.getX())*Math.sin(angle)+
                (obj.getY()-center.getY())*Math.cos(angle));
    }
    
    public static Point rotateToRightPointByCenter(Point obj, Point center, double angle){
        return new Point(center.getX()+(obj.getX()-center.getX())*Math.cos(angle)+
                (center.getY()-obj.getY())*Math.sin(angle),
                         center.getY()+(obj.getX()-center.getX())*Math.sin(angle)+
                (obj.getY()-center.getY())*Math.cos(angle));
    }
    
    public static Point rotateToLeftPointByCenter(Point obj, Point center, double angle){
        return new Point(center.getX()+(obj.getX()-center.getX())*Math.cos(-angle)+
                (center.getY()-obj.getY())*Math.sin(-angle),
                         center.getY()+(obj.getX()-center.getX())*Math.sin(-angle)+
                (obj.getY()-center.getY())*Math.cos(-angle));
    }
    
    public static Point randomPoint(Point obj){
        return new Point(obj.getX()*Math.random()+10, obj.getY()*Math.random()+30);
    }
}
