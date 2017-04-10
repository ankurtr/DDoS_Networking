package dos;

import java.io.Serializable;

public class Message implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String message;    
    private String ip;
    private String port;
    private String attack_type;

    public String getAttack_type() {
        return attack_type;
    }

    public void setAttack_type(String attack_type) {
        this.attack_type = attack_type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
