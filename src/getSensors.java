import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import com.jcraft.jsch.*;

public class getSensors {
  String host = "192.168.0.124";
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
    String command1 = "sudo python3 Desktop/readTemp.py";

    try {
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();

      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
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
      channel.disconnect();
      session.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getHumidity() {
    String command1 = "sudo python3 Desktop/readHumid.py";

    try {
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();

      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
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
      channel.disconnect();
      session.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getPressure() {
    String command1 = "sudo python3 Desktop/readPress.py";

    try {
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();

      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
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
      channel.disconnect();
      session.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
