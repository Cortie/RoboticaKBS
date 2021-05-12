package com.mygdx.game;

import java.io.*;
import java.net.*;

public class PiListener implements Runnable
{
    @Override
    public void run()
    {
        try{
            Socket clientSocket = new Socket("192.168.178.46", 8080);
            InputStream is = clientSocket.getInputStream();
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println("GET / HTTP/1.0");
            pw.println();
            pw.flush();
            byte[] buffer = new byte[1024];
            int read;
            while((read = is.read(buffer)) != -1) {
                String output = new String(buffer, 0, read);
                System.out.print(output);
                System.out.flush();
                if(output.length() > 1)
                {
                    output = output.substring(0,1);
                }
                if(output.equals("4"))
                {
                    MyGdxGame.player2.moveLeft();
                }
                if(output.equals("6"))
                {
                    MyGdxGame.player2.moveRight();
                }
            };
            clientSocket.close();
        }catch(IOException ioe)
        {
        
        }
    }
}
