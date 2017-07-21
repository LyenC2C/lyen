package socket;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lyen on 16-9-3.
 */
public class Server_t {
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public Server_t(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
        System.out.println("waiting for client to connect..................");
        this.socket = serverSocket.accept();
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        getMessage("been accepted you message!");

    }

    public void getMessage(String resMessage) throws IOException {
        String gfcMessage = dataInputStream.readUTF();
        System.out.println(gfcMessage);
        sendMessage(resMessage);
        end();
    }

    public void sendMessage(String message) throws IOException {
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }

    public void end() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server_t server = new Server_t(9999);
    }
}
