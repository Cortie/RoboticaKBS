import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class GetLights {
  private int lightvalue;
  public int lichtwaarde;
  public SerialPort port;
  public Scanner data;

  public GetLights() {
    lichtwaarde = Lights();
  }

  public int Lights() {
    SerialPort[] availablePorts = SerialPort.getCommPorts();
    for (SerialPort comPort : availablePorts) {
      if (comPort.getDescriptivePortName().length() > 10) {
        String naam = comPort.getDescriptivePortName().substring(0, 11);
        if (naam.equalsIgnoreCase("Arduino Uno")) {
          this.port = comPort;
        }
      }
    }

    if (port.isOpen()) {
      System.out.println("Port Already Opened");
    } else {
      for (SerialPort comPort : availablePorts) {
        if (comPort.getDescriptivePortName().length() > 10) {
          String naam = comPort.getDescriptivePortName().substring(0, 11);
          if (naam.equalsIgnoreCase("Arduino Uno")) {
            this.port = comPort;
          }
        }
      }

      if (port.openPort()) {
        System.out.println("Successfully opened the port.");
      } else {
        System.out.println("Unable to open the port.");
        return 151;
      }
    }

    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    data = new Scanner(port.getInputStream());
    if (data.hasNextLine()) {
      lightvalue = 0;

      try {
        if (data.nextLine().length() == 2 || data.nextLine().length() == 3) {
          lightvalue = Integer.parseInt(data.nextLine());

        }

        if (lightvalue == 0 || data.nextLine().length() == 1) {
          lightvalue = Integer.parseInt(data.nextLine());
        }

      } catch (Exception e) {
        System.out.println("lichtwaarde kon niet worden uitgelezen!");
        lightvalue = 0;
      }

    }
    port.closePort();
    return lightvalue;
  }
}
