package socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by lyen on 16-9-1.
 */
public class Client {
    private Socket socket;
    private BufferedReader in_ser;
    private BufferedReader in_sys;
    private PrintWriter out;
    private String line;

    public Client() throws Exception {

        socket = new Socket("127.0.0.1", 9999);
        //读取服务端数据
        in_ser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //读取控制台数据
        in_sys = new BufferedReader(new InputStreamReader(System.in));
        //建立输出流
        out = new PrintWriter(socket.getOutputStream());
        line = in_sys.readLine();
        while (!line.equals("end")){
            //向服务端传递字符串
            out.write(line);
            out.flush();
            System.out.println("send to server:   " + line);
            System.out.println("get from server:   " + in_ser.readLine());
            line = in_sys.readLine();
        }
        out.close();
        in_ser.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        new Client();
    }


}
