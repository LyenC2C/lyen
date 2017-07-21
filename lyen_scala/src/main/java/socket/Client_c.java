package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lyen on 16-9-1.
 */
public class Client_c {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client_c() throws Exception {
        socket = new Socket("127.0.0.1", 9999);
        //建立输出流
        out = new PrintWriter(socket.getOutputStream());
        out.print("life is short you need spark!");
        out.flush();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(in.readLine());
        out.close();
        in.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        new Client_c();
    }


}
