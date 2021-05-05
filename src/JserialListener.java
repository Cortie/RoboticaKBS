import java.util.Scanner;
import com.fazecast.jSerialComm.*;


public class JserialListener implements Runnable  {


        public SerialPort port;
        public Scanner data;
        private int lichtwaarde;




    @Override
        public void run() {
            {
                SerialPort[] availablePorts = SerialPort.getCommPorts();
                for (SerialPort comPort : availablePorts) {
                    String naam = comPort.getDescriptivePortName().substring(0, 11);
                    if (naam.equalsIgnoreCase("Arduino Uno")) {
                        this.port = comPort;
                    }
                }

                if (port.openPort()) {
                    System.out.println("Successfully opened the port.");
                } else {
                    System.out.println("Unable to open the port.");
                    return;
                }
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                data = new Scanner(port.getInputStream());
                while (data.hasNextLine()) {
                    lichtwaarde = 0;
                    try {
                        lichtwaarde = Integer.parseInt(data.nextLine());
                    } catch (Exception e) {
                        System.out.println("lichtwaarde kon niet worden uitgelezen!");
                        lichtwaarde = 0;
                    }
                    System.out.println(getLichtwaarde());
                }
            }

        }
            public int getLichtwaarde() {
                return lichtwaarde;
            }
    }


