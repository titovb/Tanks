
package tanks;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class Menu{
    private Polygon newGame;
    private Polygon exit;
    private JPanel panelMenu;
    
    private void initData(){
        newGame = new Polygon();
        newGame.addPoint(720, 475);
        newGame.addPoint(720, 545);
        newGame.addPoint(1210, 545);
        newGame.addPoint(1210, 475);
        
        exit = new Polygon();
        exit.addPoint(860, 585);
        exit.addPoint(860, 655);
        exit.addPoint(1070, 655);
        exit.addPoint(1070, 585);
    }
    
    private boolean checkForNewGame(Point point){
        if(newGame.contains(point.getX(), point.getY()))
            return true;
        return false;
    }
    
    private boolean checkForExit(Point point){
        if(exit.contains(point.getX(), point.getY()))
            return true;
        return false;
    }
    
    public Menu(Tanks frame){
        Tanks.menuMusic.play();
        initData();
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        panelMenu.setBounds(5, 5, 1920, 1050);
        panelMenu.add(new MenuPanel(), BorderLayout.CENTER);
        panelMenu.setFocusable(true);
        panelMenu.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if(checkForExit(new Point(e.getX(), e.getY()))){
                    System.exit(0);
                }
                if(checkForNewGame(new Point(e.getX(), e.getY()))){
                    frame.setContentPane(Tanks.menuNames.getPanel());
                    frame.setVisible(false);
                    frame.setVisible(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });
        frame.setContentPane(panelMenu);
    }
    
    private class MenuPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            Image img;
            String adr = "image\\Menu\\Main.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, 0, 0, 1920, 1050, this);
        }
    }
}
