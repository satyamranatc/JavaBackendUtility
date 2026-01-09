import Exp.Server;

public class Main 
{
    public static void main(String[] args) {

        Server s = new Server();

        s.get("service","{\"name\":\"Bye\"}");

        s.Start(9999);
    
    }
}
