package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lyen on 16-9-1.
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in_cli;
    private BufferedReader in_sys;
    private PrintWriter out;
    private String line;

    public Server() throws IOException {
        serverSocket = new ServerSocket(9999);
        System.out.println("ready to go ........................");
        //accept()：主要用于服务器端产生“阻塞”，等待客户端的链接请求，并且返回一个客户端的Socket实例
        socket = serverSocket.accept();
        //读取客户端数据
        in_cli = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //读取控制台数据
        in_sys = new BufferedReader(new InputStreamReader(System.in));
        //建立输出流
        out = new PrintWriter(socket.getOutputStream());
        line = in_sys.readLine();
        System.out.println("get from client:  " + in_cli.readLine());
        while (!line.equals("end")) {
            //向客户端传递字符串
            out.write(line);
            out.flush();
            System.out.println("Send to client:  " + line);
            System.out.println("get from client:  " + in_cli.readLine());
            line = in_sys.readLine();
        }
        out.close();
        in_cli.close();
        socket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }


}
