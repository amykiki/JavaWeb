package test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Amysue on 2016/2/26.
 */
public class socketHttp {
    public static void main(String[] args) {
        Socket s = null;
        try {
            s = new Socket(InetAddress.getLocalHost(), 8080);
//            s = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println("GET /first?username=amy HTTP/1.1");
            out.println("Host: localhost");
            out.println("contentType:text/html");
            out.println();

            String str = null;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            System.out.println("END");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
