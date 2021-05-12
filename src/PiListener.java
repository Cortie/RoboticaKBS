import java.io.*;
import java.net.*;

public class PiListener implements Runnable
{  
  public String Temp;
  public String Press;
  public String Humid;

  public PiListener(){
    run();
  }

    @Override
    public void run()
    {
        try{
            Socket clientSocket = new Socket("192.168.0.124", 8080);
            InputStream is = clientSocket.getInputStream();
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println("GET / HTTP/1.0");
            pw.println();
            pw.flush();
            byte[] buffer = new byte[1024];
            int read;
            while((read = is.read(buffer)) != -1) {
              String output = new String(buffer, 0, read);
              if(output.contains("Temperatuur:")){
                Temp = output;
              }
              else if(output.contains("Pressure:")){
                Press = output;
              }
              else if(output.contains("Humidity:")){
                Humid = output;
              }
              break;
            };
            clientSocket.close();
        }catch(IOException ioe)
        {
        
        }
    }
}
