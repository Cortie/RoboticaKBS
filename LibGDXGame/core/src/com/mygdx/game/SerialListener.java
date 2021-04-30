package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class SerialListener implements Runnable
{
    public SerialPort port;
    public Scanner data;
    public static int selectedmenu = 1;
    private long timer = 500000000;
    private long speed = 0;
    public static boolean Click;

    @Override
    public void run()
    {
        SerialPort[] availablePorts = SerialPort.getCommPorts();
        for(SerialPort comPort: availablePorts)
        {
            String naam = comPort.getDescriptivePortName().substring(0, 11);
            if(naam.equalsIgnoreCase("Arduino Uno"))
            {
                this.port = comPort;
            }
        }
        //port = SerialPort.getCommPort("COM4");
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
                Click = true;
            }
            if(number == 1000)
            {
                if(MyGdxGame.menuActive){
                if(TimeUtils.nanoTime() - speed > timer) {
                    if (selectedmenu <= 2) {
                        selectedmenu++;
                        MainMenuScreen.playSound();
                    } else {
                        selectedmenu = 0;
                    }
                    speed = TimeUtils.nanoTime();
                }
                }
                MyGdxGame.player1.moveRight();
            }
        }
    }
}