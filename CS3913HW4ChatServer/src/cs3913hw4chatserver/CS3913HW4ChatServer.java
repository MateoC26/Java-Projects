package cs3913hw4chatserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mateo Castro
 */
public class CS3913HW4ChatServer {
    static int portNum = 5190;
    static ArrayList<PrintStream> streams;
    
    public static void main(String[] args) {
        streams = new ArrayList<PrintStream>();
        
        try
        {
            ServerSocket chatServer = new ServerSocket(portNum);
            while(true)
            {
                Socket clientSock = chatServer.accept();
                ProcessConnection pc = new ProcessConnection(clientSock);
                pc.start();
            }
        }
        catch(IOException ex)
        {
            System.out.println("Could not bind to port.");
        }
    }
    
}

class ProcessConnection extends Thread
{
    Socket clientSock;
    String clientUsername;
    
    ProcessConnection(Socket newSock)
    {
        clientSock = newSock;
        clientUsername = "";
    }
    
    public void run()
    {
        try
        {
            Scanner sin = new Scanner(clientSock.getInputStream());
            PrintStream ps = new PrintStream(clientSock.getOutputStream());
            CS3913HW4ChatServer.streams.add(ps);
            String line;
            clientUsername = sin.nextLine();
            while(true)
            {
                line = sin.nextLine();
                
                if(line.equalsIgnoreCase("EXIT"))
                {
                    ps.println("EXIT");
                    break;
                }
                
                for(PrintStream stream : CS3913HW4ChatServer.streams)
                {
                    stream.println(clientUsername + ": " + line);
                }
            }
            
            clientSock.close();
        }
        catch(IOException ex){}
    }
}