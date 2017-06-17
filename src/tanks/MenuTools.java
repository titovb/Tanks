
package tanks;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuTools{
    private Polygon Continue;
    private JComboBox comboBoxRounds;
    private JPanel panelTools;
    private JSlider sliderBoxes;
    
    public void initComboBox(){
        String[] items = {
            "One round",
            "Three rounds",
            "Five rounds"
        };
        comboBoxRounds = new JComboBox(items);
        comboBoxRounds.setBounds(711, 451, 1201-711, 540-451);
        comboBoxRounds.setFont(new Font("Segoe Print", Font.BOLD, 34));
    }
    public void initSlider(){
        sliderBoxes = new JSlider(JSlider.HORIZONTAL, 20, 80, 60);
        sliderBoxes.setBounds(669, 663, 1197-669, 787-663);
        sliderBoxes.setFont(new Font("Segoe Print", Font.BOLD, 34));
        sliderBoxes.setBackground(Color.decode("#ffc83f"));
        sliderBoxes.setMajorTickSpacing(10);
        sliderBoxes.setMinorTickSpacing(1);
        sliderBoxes.setPaintTicks(true);
        sliderBoxes.setPaintLabels(true);
    }
    
    public void initPolygon(){
        Continue = new Polygon();
        Continue.addPoint(1325, 767);
        Continue.addPoint(1301, 783);
        Continue.addPoint(1358, 825);
        Continue.addPoint(1451, 737);
        Continue.addPoint(1426, 722);
        Continue.addPoint(1355, 785);
    }
    
    public boolean checkForContinue(Point point){
        if(Continue.contains(point.getX(), point.getY()))
            return true;
        return false;
    }
    
    public JPanel getPanel(){
        return panelTools;
    }
    
    public MenuTools(Tanks frame){
        initComboBox();
        initSlider();
        initPolygon();
        panelTools = new JPanel(new BorderLayout());
        panelTools.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        panelTools.setBounds(5, 5, 1920, 1050);
        JPanel toolsPanel = new ToolsPanel(null);
        toolsPanel.add(comboBoxRounds);
        toolsPanel.add(sliderBoxes);
        panelTools.add(toolsPanel, BorderLayout.CENTER);
        panelTools.setFocusable(true);
        panelTools.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if(checkForContinue(new Point(e.getX(), e.getY()))){
                    Tanks.menuMusic.stop();
                    String resultCombo = String.valueOf(comboBoxRounds.getSelectedItem());
                    switch(resultCombo){
                        case "One round":
                            frame.setNumberOfRounds(1);
                            break;
                        case "Three rounds":
                            frame.setNumberOfRounds(3);
                            break;
                        case "Five rounds":
                            frame.setNumberOfRounds(5);
                            break;
                    }
                    frame.setNumberOfBoxes(sliderBoxes.getValue());
                    frame.initGamePanels(frame);
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
    }
    
    private class ToolsPanel extends JPanel{
        
        public ToolsPanel(LayoutManager layout){
            super(layout);
        }
        
        @Override
        protected void paintComponent(Graphics g){
            Image img;
            String adr = "image\\Menu\\Tools.png";
            
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, 0, 0, 1920, 1050, this);
        }
    }
}
