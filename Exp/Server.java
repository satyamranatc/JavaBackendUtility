package Exp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server 
{
    ServerSocket Sc;
    String reqPath;

    HashMap map = new HashMap();

    public void sendRes(String body,Socket socket)
    {
        try
        {
            OutputStream out = socket.getOutputStream();
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write("Content-Type: application/json\r\n".getBytes());
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




    public void get(String path, String res)
    {
        System.out.println(path);
        System.out.println(res);

        map.put(path, res);
    }

    public void handleRequest(Socket socket) throws IOException
    {        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = reader.readLine();
        reqPath = line.split(" ")[1];
        
        System.out.println(map.keySet());
        System.out.println(map.containsKey(reqPath));
        reqPath = reqPath.substring(1);
        System.out.println(reqPath);

        if(map.containsKey(reqPath))
        {
            System.out.println("yes we have a response");
            sendRes(map.get(reqPath).toString(), socket);
        }
        else{
            System.out.println("no response");
            sendRes("{\"message\":\"not found\"}", socket);
        }



    }

    public void Start(int portNo)
    {
        try
        {
            Sc = new ServerSocket(portNo);
            System.out.println("Server started at port: "+portNo);

            while (true) 
            {
                Socket socket = Sc.accept();
                System.out.println("Client connected");
                handleRequest(socket);
            }

        }
        catch(Exception e)
        {
            System.out.println(e);

        }
    }

    
 
}
