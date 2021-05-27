package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class SerialListener implements Runnable
{
    public SerialPort port;
    public Scanner data;
    public static int MainMenuSelecter = 1;
    private long speed = 0;
    public static boolean Click;
    public static int SettingSelecter = 1;
    public static int GameOverSelecter = 1;
    public static int HighscoreSelecter = 1;
    private MyGdxGame game;
    
    public SerialListener(MyGdxGame game)
    {
        this.game = game;
    }
    @Override
    public void run()
    {
        Sound buttonSwitch = Gdx.audio.newSound(Gdx.files.internal("Game_menu.wav"));
        Sound buttonPress = Gdx.audio.newSound(Gdx.files.internal("game_lvlup.wav"));
        //gets all available ports in the system and sets them in an array
        SerialPort[] availablePorts = SerialPort.getCommPorts();
        // goes through the array of available ports and selects the
        // one with the name that suggests it's the connected arduino
        // Arduino Uno is the usual name however some systems do not give this name
        // Therefore if the port is empty after checking for Arduino Uno name
        // it then checks if the USB Serial name is available
        for(SerialPort comPort: availablePorts)
        {
            String naam = comPort.getDescriptivePortName().substring(0, 11);
            if(naam.equalsIgnoreCase("Arduino Uno"))
            {
                this.port = comPort;
            }
            if(this.port == null)
            {
                if(naam.equalsIgnoreCase("USB Serial "))
                {
                    this.port = comPort;
                }
            }
            System.out.println(naam);
        }
        // checks the status of the selected port and prints a message
        // confirming the status
        if(port.openPort()) {
            System.out.println("Successfully opened the port.");
        } else {
            System.out.println("Unable to open the port.");
            return;
        }
        // sets the type of timeout usage for the port
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        // puts the inputstream into a variable that can be read
        data = new Scanner(port.getInputStream());
        while(data.hasNextLine())
        {
            int number = 0;
            //makes sure that the next line of the data can be parsed into an int
            try{number = Integer.parseInt(data.nextLine());}catch(Exception e){}
            long timer = 500000000;
            //checks if the left button is pressed
            if(number == 500)
            {
                MyGdxGame.player1.moveLeft();
                if(TimeUtils.nanoTime() - speed > timer /5) {
                    Click = true;
                    if(MyGdxGame.menuActive || MyGdxGame.SettingsActive || MyGdxGame.GameOverActive || MyGdxGame.HighscoreActive){
                        game.playSound(buttonPress);
                    }
                }
                speed = TimeUtils.nanoTime();
            }
            //checks if the right button is pressed
            if(number == 1000)
            {

                if(TimeUtils.nanoTime() - speed > timer) {
                    if(MyGdxGame.menuActive){

                    if (MainMenuSelecter <= 3) {
                        MainMenuSelecter++;
                        game.playSound(buttonSwitch);
                    } else {
                        MainMenuSelecter = 1;
                        game.playSound(buttonSwitch);
                    }
                    speed = TimeUtils.nanoTime();
                }
                    if(MyGdxGame.GameOverActive){

                        if (GameOverSelecter <= 2) {
                            GameOverSelecter++;
                            game.playSound(buttonSwitch);
                        } else {
                            GameOverSelecter = 2;
                            game.playSound(buttonSwitch);
                        }
                        speed = TimeUtils.nanoTime();
                    }
                    if(MyGdxGame.HighscoreActive){

                        if (HighscoreSelecter <= 1) {
                            HighscoreSelecter++;
                            game.playSound(buttonSwitch);
                        } else {
                            HighscoreSelecter = 1;
                            game.playSound(buttonSwitch);
                        }
                        speed = TimeUtils.nanoTime();
                    }
                    if(MyGdxGame.SettingsActive){
                        if(SettingSelecter <= 3){
                            SettingSelecter++;
                            game.playSound(buttonSwitch);
                        }else{
                            SettingSelecter = 1;
                            game.playSound(buttonSwitch);

                        }

                    }
                    speed = TimeUtils.nanoTime();
                }
                MyGdxGame.player1.moveRight();
            }
        }
    }
}