import java.io.*;
import java.net.*;

public class PiListener implements Runnable {
  public String Temp;
  public String Press;
  public String Humid;
  public Integer lights = 151;

  public PiListener() {
    run();
  }

  @Override
  public void run() {
    try {
      Socket clientSocket = new Socket("192.168.178.46", 8080);
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
        Temp = output.substring(0,17);
        Press = output.substring(output.indexOf("(") + 1, output.indexOf(")"));
        Humid = output.substring(output.indexOf("|") + 1);
        break;
      }
      clientSocket.close();
    } catch (IOException ioe) {

    }
  }
}
