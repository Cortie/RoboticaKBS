import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import com.jcraft.jsch.*;

public class getTemp {
  public static String Temperature;

  public getTemp() {
    getTemperature();
  }

  public void getTemperature() {
    String host = "192.168.0.124";
    String user = "pi";
    String password = "pi";
    String command1 = "sudo python3 Desktop/read.py";

    try {
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();
      // System.out.println("Connected");

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
          // System.out.print(new String());
          Temperature = new String(tmp, 0, i);
        }
        if (channel.isClosed()) {
          // System.out.println("exit-status: " + channel.getExitStatus());
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {
        }
      }
      channel.disconnect();
      session.disconnect();
      // System.out.println("DONE");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
