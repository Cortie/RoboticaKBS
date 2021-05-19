import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import com.jcraft.jsch.*;

public class getSensors {
  String host = "10.80.17.1";
  String user = "pi";
  String password = "pi";
  public static String Temperature = "15";
  public static String Humidity = "20";
  public static String Pressure = "50";

  public getSensors() {
    getTemperature();
    getHumidity();
    getPressure();
  }

  public void getTemperature() {
    //define command to fetch temperature
    String command1 = "sudo python3 Desktop/readTemp.py";

    try {
      //establish connection with raspPi
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();

      //execute command to fetch temperature
      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      //store printed result in byte
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        //while loop to check if byte is occupied
        while (in.available() > 0) {
          //convert byte to int
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
          //store byte contents in new String to be stored in String Temperature
          Temperature = new String(tmp, 0, i);
        }
        if (channel.isClosed()) {
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {
        }
      }
      //disconnect program from raspPi
      channel.disconnect();
      session.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getHumidity() {
    //define command to fetch temperature
    String command1 = "sudo python3 Desktop/readHumid.py";

    try {
      //establish connection with raspPi
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();

      //execute command to fetch temperature
      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

       //store printed result in byte
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        //while loop to check if byte is occupied
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
          //store byte contents in new String to be stored in String Humidity
          Humidity = new String(tmp, 0, i);
        }
        if (channel.isClosed()) {
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {
        }
      }
      //disconnect program from raspPi
      channel.disconnect();
      session.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getPressure() {
    //define command to fetch temperature
    String command1 = "sudo python3 Desktop/readPress.py";

    try {
      //establish connection with raspPi
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();

      //execute command to fetch temperature
      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      //store printed result in byte
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        //while loop to check if byte is occupied
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
          //store byte contents in new String to be stored in String Pressure
          Pressure = new String(tmp, 0, i);
        }
        if (channel.isClosed()) {
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {
        }
      }
      //disconnect program from raspPi
      channel.disconnect();
      session.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
