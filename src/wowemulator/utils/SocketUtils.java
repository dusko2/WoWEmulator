/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Dusko
 */
public class SocketUtils {

    public static InputStream inputStream(Socket socket) {
        try {
            return socket.getInputStream();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static OutputStream outputStream(Socket socket) {
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
