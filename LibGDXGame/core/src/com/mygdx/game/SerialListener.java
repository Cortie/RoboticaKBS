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
    
    @Override
    public void run()
    {
        Sound buttonSwitch = Gdx.audio.newSound(Gdx.files.internal("Game_menu.wav"));
        Sound buttonPress = Gdx.audio.newSound(Gdx.files.internal("game_lvlup.wav"));
        SerialPort[] availablePorts = SerialPort.getCommPorts();
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
            long timer = 500000000;
            if(number == 500)
            {
                MyGdxGame.player1.moveLeft();
                if(TimeUtils.nanoTime() - speed > timer /5) {
                    Click = true;
                    if(MyGdxGame.menuActive || MyGdxGame.SettingsActive || MyGdxGame.GameOverActive || MyGdxGame.HighscoreActive){
                        MyGdxGame.playSound(buttonPress);
                    }
                }
                speed = TimeUtils.nanoTime();
            }
            if(number == 1000)
            {

                if(TimeUtils.nanoTime() - speed > timer) {
                    if(MyGdxGame.menuActive){

                    if (MainMenuSelecter <= 3) {
                        MainMenuSelecter++;
                        MyGdxGame.playSound(buttonSwitch);
                    } else {
                        MainMenuSelecter = 1;
                        MyGdxGame.playSound(buttonSwitch);
                    }
                    speed = TimeUtils.nanoTime();
                }
                    if(MyGdxGame.GameOverActive){

                        if (GameOverSelecter <= 2) {
                            GameOverSelecter++;
                            MyGdxGame.playSound(buttonSwitch);
                        } else {
                            GameOverSelecter = 2;
                            MyGdxGame.playSound(buttonSwitch);
                        }
                        speed = TimeUtils.nanoTime();
                    }
                    if(MyGdxGame.HighscoreActive){

                        if (HighscoreSelecter <= 2) {
                            HighscoreSelecter++;
                            MyGdxGame.playSound(buttonSwitch);
                        } else {
                            HighscoreSelecter = 1;
                            MyGdxGame.playSound(buttonSwitch);
                        }
                        speed = TimeUtils.nanoTime();
                    }
                    if(MyGdxGame.SettingsActive){
                        if(SettingSelecter <= 3){
                            SettingSelecter++;
                            MyGdxGame.playSound(buttonSwitch);
                        }else{
                            SettingSelecter = 1;
                            MyGdxGame.playSound(buttonSwitch);

                        }

                    }
                    speed = TimeUtils.nanoTime();
                }
                MyGdxGame.player1.moveRight();
            }
        }
    }
}