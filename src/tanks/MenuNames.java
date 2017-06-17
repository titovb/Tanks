
package tanks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MenuNames {
    private JPanel panelNames;
    private JTextField forName1;
    private JTextField forName2;
    private Polygon Continue;
    
    public void initTextFields(){
        forName1 = new JTextField();
        forName1.setBounds(536, 484, 1322-536, 562-484);
        forName1.setFont(new Font("Segoe Print", Font.BOLD, 34));
        forName1.setDocument(new PlainDocument() {
            @Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if ((getLength() + str.length()) <= 15) {
			super.insertString(offset, str, attr);
                    }
		}
	});
        forName2 = new JTextField("name");
        forName2.setBounds(533, 677, 1324-533, 756-677);
        forName2.setFont(new Font("Segoe Print", Font.BOLD, 34));
        forName2.setDocument(new PlainDocument() {
            @Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if ((getLength() + str.length()) <= 15) {
			super.insertString(offset, str, attr);
                    }
		}
	});
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
        return panelNames;
    }
    
    public MenuNames(Tanks frame){
        initPolygon();
        initTextFields();
        panelNames = new JPanel(new BorderLayout());
        panelNames.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        panelNames.setBounds(5, 5, 1920, 1050);
        JPanel namesPanel = new NamesPanel(null);
        namesPanel.add(forName1);
        namesPanel.add(forName2);
        panelNames.add(namesPanel, BorderLayout.CENTER);
        panelNames.setFocusable(true);
        panelNames.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if(checkForContinue(new Point(e.getX(), e.getY()))){
                    frame.setNamePlayer1(forName1.getText());
                    frame.setNamePlayer2(forName2.getText());
                    frame.setContentPane(Tanks.menuTools.getPanel());
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
    
    private class NamesPanel extends JPanel{
        
        public NamesPanel(LayoutManager layout){
            super(layout);
        }
        
        @Override
        protected void paintComponent(Graphics g){
            Image img;
            String adr = "image\\Menu\\Name of Players.png";
        
            img = Toolkit.getDefaultToolkit().getImage(adr);
        
            g.drawImage(img, 0, 0, 1920, 1050, this);
        }
    }
}
