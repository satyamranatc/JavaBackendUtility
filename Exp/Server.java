package Exp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    ServerSocket Sc;
    Socket socket;

    public void sendRes(String body)
    {
        try
        {
            OutputStream out = socket.getOutputStream();
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write("Content-Type: text/html\r\n".getBytes());
            out.write(("Content-Length: " + body.length() + "\r\n").getBytes());
            out.write("\r\n".getBytes());
            out.write(body.getBytes());
            out.flush();
            socket.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }


    public Server(String body)
    {
        try
        {
            Sc = new ServerSocket(9999);
            System.out.println("Server started at port 9999...");

            while (true) 
            {
                socket = Sc.accept();
                System.out.println("Client connected");
                // sendRes(body);
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = reader.readLine();
                String path = line.split(" ")[1];
                System.out.println(path);

                if (path.equals("/home"))
                {
                    sendRes("<h1>Hello By Home</h1> <ol> <li><a href='/home' >Home</a></li> <li><a href='/service' >Service</a></li> </ol> ");
                }
                else if(path.equals("/service"))
                {
                    sendRes("<h1>Hello By Service</h1> <ol> <li><a href='/home' >Home</a></li> <li><a href='/service' >Service</a></li> </ol> ");
                }
                else
                {
                    sendRes("<h1>404 Not Found</h1>");
                }



            }

        }
        catch(Exception e)
        {
            System.out.println(e);

        }
    }

    
 
}
