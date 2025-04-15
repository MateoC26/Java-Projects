/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs3913hw5;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mateo Castro
 */
public class CS3913HW5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame("HW 5 - Analog Clock");
        jf.setSize(400,400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        
        MyPanel mp = new MyPanel(hour, min, sec);
        jf.add(mp);
        
        mp.clockThread.start();
        
        jf.setVisible(true);
    }
    
}

class MyPanel extends JPanel
{ 
    int hour;
    int minute;
    int second;
    Thread clockThread;
    
    MyPanel(int h, int m, int s)
    {
        super();
        
        hour = h;
        minute = m;
        second = s;

        clockThread = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    try
                    {
                        sleep(1000);
                    }
                    catch(InterruptedException ex){}

                    //Update time
                    second++;
                    
                    if(second == 60)
                    {
                        second = 0;
                        minute++;
                    }
                    
                    if(minute == 60)
                    {
                        minute = 0;
                        hour++;
                    }
                    
                    if(hour == 12)
                    {
                        hour = 0;
                    }
                    
                    repaint();
                }
            }
        };
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); //Don't forget to do everything the parent does!
        
        int height = getSize().height;
        int width = getSize().width;
        
        //Calculate angle for each hand and then convert to radians for Math funcs
        double hourAngle = Math.toRadians(((double)(hour - 3)/12) * 360); 
        double minAngle = Math.toRadians(((double)(minute - 15)/60) * 360);
        double secAngle = Math.toRadians(((double)(second - 15)/60) * 360);
        
        //Getting the x and y for the three hands using cos for x and sin for y
        //Setting the size to 1/4 of height and width for hour and 1/3 for min and sec
        //Adding width/2 and height/2 to account for center of clock
        int hourX = (int) ((Math.cos(hourAngle) * width/4) + width/2);
        int hourY = (int) ((Math.sin(hourAngle) * height/4) + height/2);
       
        int minX = (int) ((Math.cos(minAngle) * width/3) + width/2);
        int minY = (int) ((Math.sin(minAngle) * height/3) + height/2);
        
        int secX = (int) ((Math.cos(secAngle) * width/3) + width/2);
        int secY = (int) ((Math.sin(secAngle) * height/3) + height/2);
        
        //Hour Hand
        g.setColor(Color.red);     
        g.drawLine(width/2, height/2, hourX, hourY);
        
        //Minute Hand
        g.setColor(Color.blue);
        g.drawLine(width/2, height/2, minX, minY);
        
        //Second Hand
        g.setColor(Color.magenta);
        g.drawLine(width/2, height/2, secX, secY);
    }
}

