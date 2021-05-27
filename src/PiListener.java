import java.io.*;
import java.lang.ref.Cleaner.Cleanable;
import java.net.*;

public class PiListener implements Runnable {
  public String Temp;
  public String Press;
  public String Humid;
  public Integer lights = 151;
  public Integer counter = 0;

  public PiListener() {
    run();
  }

  @Override
  public void run() {
    try {
      Socket clientSocket = new Socket("192.168.0.124", 8080);
      clientSocket.setTcpNoDelay(true);
      OutputStream send = clientSocket.getOutputStream();
      byte b = lights.byteValue();
      send.write(b);
      InputStream is = clientSocket.getInputStream();
      PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
      pw.println("GET / HTTP/1.0");
      pw.println();
      pw.flush();
      byte[] buffer = new byte[1024];
      int read;
      while ((read = is.read(buffer)) != -1) {
        String output = new String(buffer, 0, read);
        Temp = output.substring(output.indexOf("[") + 1, output.indexOf("]"));
        Press = output.substring(output.indexOf("(") + 1, output.indexOf(")"));
        Humid = output.substring(output.indexOf("|") + 1);
        break;
      }
      clientSocket.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
