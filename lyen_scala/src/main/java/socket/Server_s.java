package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lyen on 16-9-1.
 */
public class Server_s {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in_cli;
    private PrintWriter out;

    public Server_s() throws IOException {
        serverSocket = new ServerSocket(9999);
        System.out.println("ready to go ........................");
        //accept()：主要用于服务器端产生“阻塞”，等待客户端的链接请求，并且返回一个客户端的Socket实例
        socket = serverSocket.accept();
        //读取客户端数据
        in_cli = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Messages from client:" + in_cli.readLine());
        //建立输出流
        out = new PrintWriter(socket.getOutputStream());
        out.print("the same you are");
        out.flush();
        in_cli.close();
        out.close();
        socket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        new Server_s();
    }


}
