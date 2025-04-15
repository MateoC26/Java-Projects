package cs3913hw4chatclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;

/**
 *
 * @author Mateo Castro
 */
public class CS3913HW4ChatClient {
    static JTextArea chatBox;
    static JTextField messageBox;
    static PrintStream ps;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame("HW 4 - Chat Client");
        JButton sendButton = new JButton("Send");
        chatBox = new JTextArea();
        messageBox = new JTextField("");
        
        JPanel messagePanel = new JPanel();
        JScrollPane chatPane = new JScrollPane(chatBox);
        
        chatBox.setEditable(false);
        chatBox.setLineWrap(true);
        
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 400);
        
        messagePanel.setLayout(new GridLayout(1,2));
        messagePanel.add(messageBox);
        messagePanel.add(sendButton);
        
        ButtonHandler sendHandler = new ButtonHandler();
        sendButton.addActionListener(sendHandler);
        
        jf.add(chatPane, BorderLayout.CENTER);
        jf.add(messagePanel, BorderLayout.SOUTH);
        
        jf.setVisible(true);
        
        String username;
        String serverIP;
        
        chatBox.append("Server: ");
        
        while(true)
        {
            if(!"".equals(sendHandler.getMessage()))
            {
                serverIP = sendHandler.getMessage();
                break;
            }  
        }
        
        chatBox.append("Username: ");
        
        while(true)
        {
            if(!serverIP.equals(sendHandler.getMessage()))
            {
                username = sendHandler.getMessage();
                break;
            } 
        }
        
        chatBox.append("\n");
        
        try
        {
            Socket clientSocket = new Socket(serverIP, 5190);
            sendHandler.connected = true;
            Scanner sin = new Scanner(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            ps.println(username);
            String line;
            while(true)
            {   
                line = sin.nextLine();
                
                if(line.equalsIgnoreCase("EXIT"))
                    break;
                
                chatBox.append(line + "\n");
            }
            chatBox.append("Exiting Server");
            clientSocket.close();
        }
        catch(IOException ex){}        
    }
    
}

class ButtonHandler implements ActionListener 
{
    String message = "";
    boolean connected = false;
    
    @Override
    synchronized public void actionPerformed(ActionEvent e)
    {
        if(!connected)
        {
            message = CS3913HW4ChatClient.messageBox.getText();
            CS3913HW4ChatClient.messageBox.setText("");
            CS3913HW4ChatClient.chatBox.append(message + "\n");
        }
        else
        {
            CS3913HW4ChatClient.ps.println(CS3913HW4ChatClient.messageBox.getText());
            CS3913HW4ChatClient.messageBox.setText("");
        }
    }
    
    synchronized public String getMessage()
    {
        return message;
    }
}
