package socket;


import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by lyen on 16-9-3.
 */
public class Client_t {
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    DataInputStream sys_in;

    public Client_t(String hostName, int port) throws IOException {
        this.socket = new Socket(hostName, port);
        this.sys_in = new DataInputStream(System.in);
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());

        sendMessage();
        getMessage();
        end();
    }

    public void getMessage() throws IOException {
        String gfcMessage = dataInputStream.readUTF();
        System.out.println(gfcMessage);
        end();
    }

    public void sendMessage() throws IOException {
        String lines = sys_in.readUTF();
        dataOutputStream.writeUTF(lines);
        dataOutputStream.flush();
    }

    public void end() throws IOException {
        dataOutputStream.close();
        dataInputStream.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Client_t client = new Client_t("localhost", 9999);

    }
}
