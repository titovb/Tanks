
package tanks;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;


public class Tanks extends JFrame{

    public static JPanel cp;
    public static JPanel panelGame;
    public static JPanel panelInfo;
    public static MenuNames menuNames;
    public static MenuTools menuTools;
    public static Menu menu;
    private static Tank tank1;
    private static Tank tank2;
    private static ArrayList<Box> boxes = new ArrayList<Box>();
    private int numberOfBoxes = 60;
    private int numberOfRounds = 1;
    public static Point SIZE;
    public static Point SIZE2;
    private static Base base;
    public static TimerForGame timer;
    public static Bonuse bonuse1;
    public static Bonuse bonuse2;
    private static boolean gameOver = false;
    private String namePlayer1;
    private String namePlayer2;
    public static SoundPlayer menuMusic = new SoundPlayer(new File("sounds\\MenuMusic.wav"));
    
    public void setNamePlayer1(String name){
        namePlayer1 = name;
    }
    
    public String getNamePlayer1(){
        return namePlayer1;
    }
    
    public void setNamePlayer2(String name){
        namePlayer2 = name;
    }
    
    public String getNamePlayer2(){
        return namePlayer2;
    }
    
    public void setNumberOfBoxes(int number){
        numberOfBoxes = number;
    }
    
    public int getNumberOfBoxes(){
        return numberOfBoxes;
    }
    
    public void setNumberOfRounds(int number){
        numberOfRounds = number;
    }
    
    public int getNumberOfRounds(){
        return numberOfRounds;
    }
    
    public static Base getBase(){
        return base;
    }
    
    public Tank getTank1(){
        return tank1;
    }
    
    public Tank getTank2(){
        return tank2;
    }
    
    public void setGameOver(boolean obj){
        gameOver = obj;
    }
    
    public static boolean getGameOver(){
        return gameOver;
    }
    
    private class GamePanel extends JPanel{
        
        @Override
        protected void paintComponent(Graphics g){
            Image img;
            String adr = "image\\fon\\fon.png";
            img = Toolkit.getDefaultToolkit().getImage(adr);

            g.drawImage(img, 0, 0, (int)SIZE.getX(), (int)SIZE.getY(), panelGame);
            
            checkingForBase(g);
            
            for(int i=0;i<boxes.size();i++){
                boxes.get(i).paintBox(g);
            }
            bonuse1.paint(g);
            bonuse2.paint(g);
            tank1.paintTank(g);
            tank2.paintTank(g);
        }
    }
    
    private void checkingForBase(Graphics g){
        if(base.checkForTank(tank1)&&base.checkForTank(tank2)){
                base.setTypeTank(0);
                base.setNumberOfLine(0);
                base.paintBase(g);
                base.setThreadDone(false);
                panelInfo.repaint();
            }
            else
                if(base.checkForTank(tank1)){
                    base.setTypeTank(1);
                    base.paintBase(g);
                    if(!base.getThreadDone()){
                        new Thread(new Runnable(){
                            @Override
                            public void run(){
                                SoundPlayer timeSound = new SoundPlayer(new File("sounds\\Time.wav"));
                                timeSound.play();
                                while(base.getNumberOfLine()!=27 && 
                                      !(base.checkForTank(tank1)&&base.checkForTank(tank2)) && 
                                      base.checkForTank(tank1)&&!gameOver){
                                    base.upNumberOfLine();
                                    try{
                                        Thread.sleep(1000);
                                    }catch(InterruptedException e){}
                                    panelInfo.repaint();
                                    if(!timeSound.isPlaying())
                                        timeSound.play();
                                }
                                timeSound.stop();
                            }
                        }).start();
                        base.setThreadDone(true);
                    }
                }
                else
                    if(base.checkForTank(tank2)){
                        base.setTypeTank(2);
                        base.paintBase(g);
                        if(!base.getThreadDone()){
                            new Thread(new Runnable(){
                                @Override
                                public void run(){
                                    SoundPlayer timeSound = new SoundPlayer(new File("sounds\\Time.wav"));
                                    timeSound.play();
                                    while(base.getNumberOfLine()!=27 && 
                                          !(base.checkForTank(tank1)&&base.checkForTank(tank2)) && 
                                          base.checkForTank(tank2)&&!gameOver){
                                        base.upNumberOfLine();
                                        try{
                                            Thread.sleep(1000);
                                        }catch(InterruptedException e){}
                                        panelInfo.repaint();
                                        if(!timeSound.isPlaying())
                                            timeSound.play();
                                    }
                                    timeSound.stop();
                                }
                            }).start();
                            base.setThreadDone(true);
                        }
                    }   
                    else{
                        base.setThreadDone(false);
                        base.setTypeTank(0);
                        base.setNumberOfLine(0);
                        base.paintBase(g);
                        panelInfo.repaint();
                    }
    }
    
    private class InfoPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            Image img;
            String adr = "image\\InfoPanel\\fon.png";
            img = Toolkit.getDefaultToolkit().getImage(adr);

            g.drawImage(img, 0, 0, (int)SIZE2.getX(), (int)SIZE2.getY(), panelInfo);
            
            base.paintTimeLine(g);
            
            timer.paintTimer(g);
            
            g.setFont(new Font("Segoe Print", Font.BOLD, 34));
            g.drawString(namePlayer1, 0, 34);
            g.drawString(namePlayer2, 1920-namePlayer2.length()*26, 34);
        }
    }
    
    public final void initMenu(Tanks frame){
        menu = new Menu(frame);
    }
    
    public final void initMenuNames(Tanks frame){
        menuNames = new MenuNames(frame);
    }
    
    public final void initMenuTools(Tanks frame){
        menuTools = new MenuTools(frame);
    }
    
    public final void initGamePanels(Tanks frame){
        panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        panelInfo.setBounds(5,5,(int)SIZE2.getX(), (int)SIZE2.getY());
        panelInfo.add(new InfoPanel(), BorderLayout.CENTER);
        
        
        panelGame = new JPanel(new BorderLayout());
        panelGame.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        panelGame.setBounds(5, 100,(int)SIZE.getX(), (int)SIZE.getY());
        panelGame.add(new GamePanel(), BorderLayout.CENTER);
        panelGame.setFocusable(true);
        panelGame.addKeyListener(new KeyListener(){

            private java.util.List<String> keysChar = new ArrayList<String>();
            
            @Override
            public void keyPressed(KeyEvent e) {
                if(gameOver){
                    keysChar.removeAll(keysChar);
                }else
                if(e.getKeyLocation()!=2)
                    if(!keysChar.contains(KeyEvent.getKeyText(e.getKeyCode()))&&!gameOver){
                        keysChar.add(KeyEvent.getKeyText(e.getKeyCode()));//если значения клавиши нету в list, то добавляем его
                        new Thread (new Runnable(){//создаем поток, выполняющий действия соответственно нажатой кнопке
                            @Override
                            public void run() {
                                if(!keysChar.isEmpty()&&!gameOver){
                                    typed(e);
                                }
                            }    
                        }).start();//запуск потока с функцией typed
                    }
            }
            
            public synchronized void typed(KeyEvent e) {
                if(!keysChar.isEmpty()&&!gameOver){
                            long speed = 40;
                            while(keysChar.contains(KeyEvent.getKeyText(e.getKeyCode()))){//пока list со значениями кнопок содержит значение переданое в функцию - выполняем действия
                                if(keysChar.contains ("A") && !keysChar.contains("D")&&
                                    keysChar.contains("W") && !keysChar.contains("S")){
                                    tank1.speedOfTankUp();
                                    tank1.moveUp();
                                    tank1.speedOfRotateUp();
                                    tank1.rotateTankToLeft();
                                    panelGame.repaint();
                                }
                                if(!keysChar.contains ("A") && keysChar.contains("D")&& 
                                    keysChar.contains("W") && !keysChar.contains("S")){
                                    tank1.speedOfTankUp();
                                    tank1.moveUp();
                                    tank1.speedOfRotateUp();
                                    tank1.rotateTankToRight();
                                    panelGame.repaint();
                                }
                                if(keysChar.contains ("A") && !keysChar.contains("D")&& 
                                    !keysChar.contains("W") && keysChar.contains("S")){
                                    tank1.speedOfTankUp();
                                    tank1.moveDown();
                                    tank1.speedOfRotateUp();
                                    tank1.rotateTankToRight();
                                    panelGame.repaint();
                                }
                                if(!keysChar.contains ("A") && keysChar.contains("D")&& 
                                    !keysChar.contains("W") && keysChar.contains("S")){
                                    tank1.speedOfTankUp();
                                    tank1.moveDown();
                                    tank1.speedOfRotateUp();
                                    tank1.rotateTankToLeft();
                                    panelGame.repaint();
                                }
                                
                                if(keysChar.contains ("A") && !keysChar.contains("D")&& 
                                    !keysChar.contains("W") && !keysChar.contains("S")){
                                    tank1.speedOfRotateUp();
                                    tank1.rotateTankToLeft();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains ("A") && keysChar.contains("D")&& 
                                    !keysChar.contains("W") && !keysChar.contains("S")){
                                    tank1.speedOfRotateUp();
                                    tank1.rotateTankToRight();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains ("A") && !keysChar.contains("D")&& 
                                    keysChar.contains("W") && !keysChar.contains("S")){
                                    tank1.speedOfTankUp();
                                    tank1.moveUp();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains ("A") && !keysChar.contains("D")&& 
                                    !keysChar.contains("W") && keysChar.contains("S")){
                                    tank1.speedOfTankUp();
                                    tank1.moveDown();
                                    panelGame.repaint();
                                }
                                
                                if(keysChar.contains("E") && !keysChar.contains("Q")){
                                    tank1.getTower().speedOfRotateUp();
                                    tank1.rotateTowerToRight();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains("E") && keysChar.contains("Q")){
                                    tank1.getTower().speedOfRotateUp();
                                    tank1.rotateTowerToLeft();
                                    panelGame.repaint();
                                }
                                if(keysChar.contains("Space")){
                                    if(tank1.getReloading()==false&&tank1.getAmmo()>0){
                                        tank1.shoot(getGraphics(), tank2);
                                        tank1.setReloading(true);
                                        SoundPlayer.playSound("sounds\\Shoot.wav");
                                        java.util.Timer timer = new java.util.Timer();//создание таймера
                                        timer.schedule(new TimerTask(){//timer, на 3 секунды, по окончанию которого закончится перезарядка
                                            @Override
                                            public void run() {
                                                tank1.setReloading(false);
                                                panelGame.repaint();
                                            }               
                                        }, tank1.getReloadingTime());
                                    }
                                }
                                
                                
                                if(keysChar.contains ("Left") && !keysChar.contains("Right")&&
                                    keysChar.contains("Up") && !keysChar.contains("Down")){
                                    tank2.speedOfTankUp();
                                    tank2.moveUp();
                                    tank2.speedOfRotateUp();
                                    tank2.rotateTankToLeft();
                                    panelGame.repaint();
                                }
                                if(!keysChar.contains ("Left") && keysChar.contains("Right")&& 
                                    keysChar.contains("Up") && !keysChar.contains("Down")){
                                    tank2.speedOfTankUp();
                                    tank2.moveUp();
                                    tank2.speedOfRotateUp();
                                    tank2.rotateTankToRight();
                                    panelGame.repaint();
                                }
                                if(keysChar.contains ("Left") && !keysChar.contains("Right")&& 
                                    !keysChar.contains("Up") && keysChar.contains("Down")){
                                    tank2.speedOfTankUp();
                                    tank2.moveDown();
                                    tank2.speedOfRotateUp();
                                    tank2.rotateTankToRight();
                                    panelGame.repaint();
                                }
                                if(!keysChar.contains ("Left") && keysChar.contains("Right")&& 
                                    !keysChar.contains("Up") && keysChar.contains("Down")){
                                    tank2.speedOfTankUp();
                                    tank2.moveDown();
                                    tank2.speedOfRotateUp();
                                    tank2.rotateTankToLeft();
                                    panelGame.repaint();
                                }
                                
                                if(keysChar.contains ("Left") && !keysChar.contains("Right")&& 
                                    !keysChar.contains("Up") && !keysChar.contains("Down")){
                                    tank2.speedOfRotateUp();
                                    tank2.rotateTankToLeft();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains ("Left") && keysChar.contains("Right")&& 
                                    !keysChar.contains("Up") && !keysChar.contains("Down")){
                                    tank2.speedOfRotateUp();
                                    tank2.rotateTankToRight();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains ("Left") && !keysChar.contains("Right")&& 
                                    keysChar.contains("Up") && !keysChar.contains("Down")){
                                    tank2.speedOfTankUp();
                                    tank2.moveUp();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains ("Left") && !keysChar.contains("Right")&& 
                                    !keysChar.contains("Up") && keysChar.contains("Down")){
                                    tank2.speedOfTankUp();
                                    tank2.moveDown();
                                    panelGame.repaint();
                                }
                                
                                if(keysChar.contains("NumPad-6") && !keysChar.contains("NumPad-4")){
                                    tank2.getTower().speedOfRotateUp();
                                    tank2.rotateTowerToRight();
                                    panelGame.repaint();
                                }
                                
                                if(!keysChar.contains("NumPad-6") && keysChar.contains("NumPad-4")){
                                    tank2.getTower().speedOfRotateUp();
                                    tank2.rotateTowerToLeft();
                                    panelGame.repaint();
                                }
                                if(keysChar.contains("Ctrl")){
                                    if(tank2.getReloading()==false&&tank2.getAmmo()>0){
                                        tank2.shoot(getGraphics(), tank1);
                                        tank2.setReloading(true);
                                        SoundPlayer.playSound("sounds\\Shoot.wav");
                                        java.util.Timer timer = new java.util.Timer();//создание таймера
                                        timer.schedule(new TimerTask(){//timer, на 3 секунды, по окончанию которого закончится перезарядк
                                            @Override
                                            public void run() {
                                                tank2.setReloading(false);
                                                panelGame.repaint();
                                            }               
                                        }, tank2.getReloadingTime());
                                    }
                                }                                                              
                                
                                try {
                                    Thread.sleep(speed);
                                } catch (InterruptedException ex) {}
                            }
                        }
                
            }//описание действий при нажатии кнопок
            
            @Override
            public void keyTyped(KeyEvent e) {
                if(gameOver){
                    keysChar.removeAll(keysChar);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!keysChar.isEmpty())
                    if(keysChar.contains(KeyEvent.getKeyText(e.getKeyCode()))){
                        if(!gameOver)
                            released(e);
                        keysChar.remove(KeyEvent.getKeyText(e.getKeyCode()));//удаляем значение клавиши из list, если она была отжата и такое значение существует в list
                    }
            }
            
            public void released(KeyEvent e){
                if(!gameOver){
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("W")){
                    new Thread(new Runnable(){
                        public void run(){
                            while(tank1.getSpeedOfTank()>0&&!gameOver){
                                if(keysChar.contains("W")){
                                    break;
                                }
                                if(keysChar.contains("S")){
                                    tank1.setSpeedOfTank(0);
                                    break;
                                }
                                tank1.speedOfTankDown();
                                tank1.moveUp();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank1.getSoundMove().stop();
                        }
                    }).start();
                }
                        
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("S")){
                    new Thread(new Runnable(){
                        public void run(){
                            while(tank1.getSpeedOfTank()>0){
                                if(keysChar.contains("W")){
                                    tank1.setSpeedOfTank(0);
                                    break;
                                }
                                if(keysChar.contains("S")){
                                    break;
                                }
                                tank1.speedOfTankDown();
                                tank1.moveDown();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank1.getSoundMove().stop();
                        }
                    }).start();
                }  
                        
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("D")){
                    new Thread(new Runnable(){
                        public void run(){
                            boolean wasSPressed = false;
                            while(tank1.getSpeedOfRotate()>0&&!gameOver){
                                if(wasSPressed){
                                    if(keysChar.contains("A")){
                                        tank1.setSpeedOfRotate(0);
                                        break;
                                    }
                                    if(keysChar.contains("D")){
                                        break;
                                    }
                                    tank1.speedOfRotateDown();
                                    tank1.rotateTankToLeft();
                                    panelGame.repaint();
                                    try{
                                        Thread.sleep(40);
                                    }catch(Exception e){}
                                }else{
                                    if((keysChar.contains("W")&&!keysChar.contains("S"))||(!keysChar.contains("W")&&!keysChar.contains("S"))){
                                        if(keysChar.contains("A")){
                                            tank1.setSpeedOfRotate(0);
                                            break;
                                        }
                                        if(keysChar.contains("D")){
                                            break;
                                        }
                                        tank1.speedOfRotateDown();
                                        tank1.rotateTankToRight();
                                        panelGame.repaint();
                                        try{
                                            Thread.sleep(40);
                                        }catch(Exception e){}
                                    }
                                    if((!keysChar.contains("W")&&keysChar.contains("S"))){
                                        wasSPressed = true;
                                    }
                                }
                            }
                        }
                    }).start();
                }  
                        
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("A")){
                    new Thread(new Runnable(){
                        public void run(){
                            boolean wasSPressed = false;
                            while(tank1.getSpeedOfRotate()>0&&!gameOver){
                                if(wasSPressed){
                                    if(keysChar.contains("D")){
                                        tank1.setSpeedOfRotate(0);
                                        break;
                                    }
                                    if(keysChar.contains("A")){
                                        break;
                                    }
                                    tank1.speedOfRotateDown();
                                    tank1.rotateTankToRight();
                                    panelGame.repaint();
                                    try{
                                        Thread.sleep(40);
                                    }catch(Exception e){}
                                }else{
                                    if((keysChar.contains("W")&&!keysChar.contains("S"))||(!keysChar.contains("W")&&!keysChar.contains("S"))){
                                        if(keysChar.contains("D")){
                                            tank1.setSpeedOfRotate(0);
                                            break;
                                        }
                                        if(keysChar.contains("A")){
                                            break;
                                        }
                                        tank1.speedOfRotateDown();
                                        tank1.rotateTankToLeft();
                                        panelGame.repaint();
                                        try{
                                            Thread.sleep(40);
                                        }catch(Exception e){}
                                    }
                                    if((!keysChar.contains("W")&&keysChar.contains("S"))){
                                        wasSPressed = true;
                                    }
                                }
                            }
                        }
                    }).start();
                }
                
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("E")){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            while(tank1.getTower().getSpeedOfRotate()>0&&!gameOver){
                                if(keysChar.contains("E")){
                                    break;
                                }
                                if(keysChar.contains("Q")){
                                    tank1.getTower().setSpeedOfRotate(0);
                                    break;
                                }
                                tank1.getTower().speedOfRotateDown();
                                tank1.getTower().rotateTowerToRight();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank1.getSoundMoveTower().stop();
                        }
                    }).start();
                }
                
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("Q")){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            while(tank1.getTower().getSpeedOfRotate()>0&&!gameOver){
                                if(keysChar.contains("Q")){
                                    break;
                                }
                                if(keysChar.contains("E")){
                                    tank1.getTower().setSpeedOfRotate(0);
                                    break;
                                }
                                tank1.getTower().speedOfRotateDown();
                                tank1.getTower().rotateTowerToLeft();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank1.getSoundMoveTower().stop();
                        }
                    }).start();
                }
                    
                    
                    
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("Up")){
                    new Thread(new Runnable(){
                        public void run(){
                            while(tank2.getSpeedOfTank()>0&&!gameOver){
                                if(keysChar.contains("Up")){
                                    break;
                                }
                                if(keysChar.contains("Down")){
                                    tank2.setSpeedOfTank(0);
                                    break;
                                }
                                tank2.speedOfTankDown();
                                tank2.moveUp();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank2.getSoundMove().stop();
                        }
                    }).start();
                }
                        
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("Down")){
                    new Thread(new Runnable(){
                        public void run(){
                            while(tank2.getSpeedOfTank()>0&&!gameOver){
                                if(keysChar.contains("Up")){
                                    tank2.setSpeedOfTank(0);
                                    break;
                                }
                                if(keysChar.contains("Down")){
                                    break;
                                }
                                tank2.speedOfTankDown();
                                tank2.moveDown();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank2.getSoundMove().stop();
                        }
                    }).start();
                }  
                        
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("Right")){
                    new Thread(new Runnable(){
                        public void run(){
                            boolean wasSPressed = false;
                            while(tank2.getSpeedOfRotate()>0&&!gameOver){
                                if(wasSPressed){
                                    if(keysChar.contains("Left")){
                                        tank2.setSpeedOfRotate(0);
                                        break;
                                    }
                                    if(keysChar.contains("Right")){
                                        break;
                                    }
                                    tank2.speedOfRotateDown();
                                    tank2.rotateTankToLeft();
                                    panelGame.repaint();
                                    try{
                                        Thread.sleep(40);
                                    }catch(Exception e){}
                                }else{
                                    if((keysChar.contains("Up")&&!keysChar.contains("Down"))||(!keysChar.contains("Up")&&!keysChar.contains("Down"))){
                                        if(keysChar.contains("Left")){
                                            tank2.setSpeedOfRotate(0);
                                            break;
                                        }
                                        if(keysChar.contains("Right")){
                                            break;
                                        }
                                        tank2.speedOfRotateDown();
                                        tank2.rotateTankToRight();
                                        panelGame.repaint();
                                        try{
                                            Thread.sleep(40);
                                        }catch(Exception e){}
                                    }
                                    if((!keysChar.contains("Up")&&keysChar.contains("Down"))){
                                        wasSPressed = true;
                                    }
                                }
                            }
                        }
                    }).start();
                }  
                        
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("Left")){
                    new Thread(new Runnable(){
                        public void run(){
                            boolean wasSPressed = false;
                            while(tank2.getSpeedOfRotate()>0&&!gameOver){
                                if(wasSPressed){
                                    if(keysChar.contains("Right")){
                                        tank2.setSpeedOfRotate(0);
                                        break;
                                    }
                                    if(keysChar.contains("Left")){
                                        break;
                                    }
                                    tank2.speedOfRotateDown();
                                    tank2.rotateTankToRight();
                                    panelGame.repaint();
                                    try{
                                        Thread.sleep(40);
                                    }catch(Exception e){}
                                }else{
                                    if((keysChar.contains("Up")&&!keysChar.contains("Down"))||(!keysChar.contains("Up")&&!keysChar.contains("Down"))){
                                        if(keysChar.contains("Right")){
                                            tank2.setSpeedOfRotate(0);
                                            break;
                                        }
                                        if(keysChar.contains("Left")){
                                            break;
                                        }
                                        tank2.speedOfRotateDown();
                                        tank2.rotateTankToLeft();
                                        panelGame.repaint();
                                        try{
                                            Thread.sleep(40);
                                        }catch(Exception e){}
                                    }
                                    if((!keysChar.contains("Up")&&keysChar.contains("Down"))){
                                        wasSPressed = true;
                                    }
                                }
                            }
                        }
                    }).start();
                }
                
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("NumPad-6")){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            while(tank2.getTower().getSpeedOfRotate()>0&&!gameOver){
                                if(keysChar.contains("NumPad-6")){
                                    break;
                                }
                                if(keysChar.contains("NumPad-4")){
                                    tank2.getTower().setSpeedOfRotate(0);
                                    break;
                                }
                                tank2.getTower().speedOfRotateDown();
                                tank2.getTower().rotateTowerToRight();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank2.getSoundMoveTower().stop();
                        }
                    }).start();
                }
                
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("NumPad-4")){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            while(tank2.getTower().getSpeedOfRotate()>0&&!gameOver){
                                if(keysChar.contains("NumPad-4")){
                                    break;
                                }
                                if(keysChar.contains("NumPad-6")){
                                    tank2.getTower().setSpeedOfRotate(0);
                                    break;
                                }
                                tank2.getTower().speedOfRotateDown();
                                tank2.getTower().rotateTowerToLeft();
                                panelGame.repaint();
                                try{
                                    Thread.sleep(40);
                                }catch(Exception e){}
                            }
                            tank2.getSoundMoveTower().stop();
                        }
                    }).start();
                }
                }
            }
        
        });
        
        cp = new JPanel(null);
        frame.setContentPane(cp);
        cp.add(panelInfo);
        cp.add(panelGame);
        
        initData();
        
        new Thread(new Runnable(){
            @Override
            public void run() {
                int numberOfWinning1 = 0;
                int numberOfWinning2 = 0;
                while(true){
                    if(frame.getTank2().getDead()||frame.getBase().getWinner()==1){
                        frame.setGameOver(true);
                    
                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e){}
                    
                        JOptionPane.showMessageDialog(frame, frame.getNamePlayer1()+" won this roumd!", "Blue Tank won!", JOptionPane.INFORMATION_MESSAGE);
                        numberOfWinning1++;
                        numberOfRounds--;
                        frame.setGameOver(false);
                        if(numberOfRounds>0){
                            frame.initGamePanels(frame);
                            frame.setVisible(false);
                            frame.setVisible(true);
                        }
                        if(numberOfRounds<=-1){
                            break;
                        }
                    }
                    else if(frame.getTank1().getDead()||frame.getBase().getWinner()==2){
                        frame.setGameOver(true);
                    
                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e){}
                    
                        JOptionPane.showMessageDialog(frame, frame.getNamePlayer2()+" won this round!", "Red Tank won!", JOptionPane.INFORMATION_MESSAGE);
                        numberOfWinning2++;
                        numberOfRounds--;
                        frame.setGameOver(false);
                        if(numberOfRounds>0){
                            frame.initGamePanels(frame);
                            frame.setVisible(false);
                            frame.setVisible(true);
                        }
                        if(numberOfRounds<=-1){
                            break;
                        }
                    }
                    else if((!frame.getTank1().getDead()&&!frame.getTank2().getDead()||frame.getTank1().getDead()&&frame.getTank2().getDead())&&frame.getBase().getWinner()==0&&timer.getM()==5){
                        frame.setGameOver(true);
                    
                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e){}
                    
                        JOptionPane.showMessageDialog(frame,"Round Draw!", "Round Draw!", JOptionPane.INFORMATION_MESSAGE);
                        numberOfRounds--;
                        frame.setGameOver(false);
                        if(numberOfRounds>0){
                            frame.initGamePanels(frame);
                            frame.setVisible(false);
                            frame.setVisible(true);
                        }
                        if(numberOfRounds<=-1){
                            break;
                        }

                    }
                }
                if(numberOfWinning1>numberOfWinning2)
                    JOptionPane.showMessageDialog(frame,frame.getNamePlayer1() + " won this game!", "Blue Tank won!", JOptionPane.INFORMATION_MESSAGE);
                else if(numberOfWinning2>numberOfWinning1)
                    JOptionPane.showMessageDialog(frame,frame.getNamePlayer2() + " won this game!", "Red Tank won!", JOptionPane.INFORMATION_MESSAGE);
                else if(numberOfWinning2==numberOfWinning1)
                    JOptionPane.showMessageDialog(frame,"Game Draw!", "Game Draw!", JOptionPane.INFORMATION_MESSAGE);
                frame.initMenuTools(frame);
                frame.initMenuNames(frame);
                frame.initMenu(frame);
                frame.setVisible(false);
                frame.setVisible(true);
            }
            
        }).start();
    }
    
    public final void initData(){
        timer = new TimerForGame();
        
        new Thread(timer).start();
        
        bonuse1 = new HealthBonus(SIZE.getX()/2, 120.0);
        bonuse2 = new HealthBonus(SIZE.getX()/2, SIZE.getY()-120.0);
        
        boxes.removeAll(boxes);
        for(int i=0;i<numberOfBoxes;i++){
            boxes.add(Box.randomBox(boxes, bonuse1, bonuse2));//создание 70-ти рандомных ящиков
        }
        
        base = new Base(SIZE.getX()/2, SIZE.getY()/2);
        
        tank1 = new Tank(new Point(70, 70), boxes, Color.BLUE);//создание танка с центром в точке (100,100) и синим цветом (танк игрока 1)
        tank2 = new Tank(new Point(SIZE.getX()-70, SIZE.getY()-70), boxes, Color.RED);//создание танка с центром в точке (1900, 900) и красным цветом (танк игрока 2)

        while(tank1.getRotate()<(3*Math.PI)/4){
            tank1.startingRotateTankToRight();
        }
        while(tank2.getRotate()>-Math.PI/4){
            tank2.startingRotateTankToLeft();
        }
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(!gameOver){
                    SoundPlayer.playSound("sounds\\TankStay.wav", 0.5f);
                    try {
                        Thread.sleep(3200);
                    } catch (InterruptedException ex) {}
                }
            }
        }).start();
    }
    
    public Tanks(){
        SIZE = new Point(1910, 940);
        SIZE2 = new Point(1910, 95);
        this.setSize((int)SIZE.getX(), (int)SIZE.getY()+(int)SIZE2.getY());
        setExtendedState(MAXIMIZED_BOTH);
        
        initMenuTools(this);
        
        initMenuNames(this);
        
        initMenu(this);
        
    }
    
    public static void main(String[] args) {
        Tanks t = new Tanks();
        t.setTitle("w55182 - Tanks");
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setVisible(true);
        t.setExtendedState(MAXIMIZED_BOTH);
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
    }
}
