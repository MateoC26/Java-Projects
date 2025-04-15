/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs3913hw2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 *
 * @author Mateo Castro (mc7212)
 */
public class CS3913HW2 {

    /**
     * @param args the command line arguments
     */
    
    static int numButtons;
    static JButton[] buttons;
    static Random rng;
    
    public static void main(String[] args) {
        numButtons = 8;
        buttons = new JButton[numButtons];
        rng = new Random();
        
        JFrame jf = new JFrame("HW 2");
        JPanel jp = new JPanel();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 400);
        jp.setBackground(Color.CYAN);
        jp.setLayout(new GridLayout(4,2));
        
        jf.add(jp);
        for(int i = 0; i < numButtons; i++)
        {
            buttons[i] = new JButton("BUTTON " + (i + 1));
            buttons[i].setBackground(new Color(rng.nextInt()));
            buttons[i].addActionListener(new ButtonHandler());
            jp.add(buttons[i]);
        }
        
        jf.setVisible(true);
    }
}

class ButtonHandler implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        for(int i = 0; i < CS3913HW2.numButtons; i++)
        {
            if(e.getSource() != CS3913HW2.buttons[i])
                CS3913HW2.buttons[i].setBackground(new Color(CS3913HW2.rng.nextInt()));
        }
    }
}
