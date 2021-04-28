package com.mygdx.game;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class SerialListener implements Runnable
{
    public SerialPort port;
    public Scanner data;
    @Override
    public void run()
    {
        port = SerialPort.getCommPort("COM3");
        if(port.openPort()) {
            System.out.println("Successfully opened the port.");
        } else {
            System.out.println("Unable to open the port.");
            return;
        }
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        data = new Scanner(port.getInputStream());
        while(data.hasNextLine())
        {
            int number = 0;
            try{number = Integer.parseInt(data.nextLine());}catch(Exception e){}
            if(number == 500)
            {
                MyGdxGame.player1.moveLeft();
            }
            if(number == 1000)
            {
                MyGdxGame.player1.moveRight();
            }
        }
    }
}
