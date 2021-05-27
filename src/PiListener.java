import java.io.*;
import java.net.*;

public class PiListener implements Runnable {
  public String Temp;
  public String Press;
  public String Humid;
  public Integer lightSetting;
  public Integer light;
  public Integer tempSetting;
  public String splitter ="|";


  public PiListener() {
    run();
  }

  @Override
  public void run() {
    try {
      light = GetLights.licht;
      lightSetting = PersoonlijkeInstellingen.lightSetting;
      tempSetting = PersoonlijkeInstellingen.tempSetting;//KlimaatBeheer.getIngesteldeTempwaarde();
      Socket clientSocket = new Socket("10.80.17.1", 8080);
      OutputStream send = clientSocket.getOutputStream();

      String setData = light.toString()+splitter+lightSetting.toString()+splitter+tempSetting.toString();

      byte[] a = setData.getBytes();
      send.write(a);


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
