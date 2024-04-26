package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimServer {
    //port number
    private int port;
    //server socket
    private ServerSocket serverSocket;
    //client socket
    private Socket clientSocket;
    //input stream
    private BufferedReader in;
    //output stream
    private PrintWriter out;


    public SimServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Client connected!");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                out.println("You said: " + inputLine);
            }
        } catch (IOException e) {
            System.err.println("Exception caught: " + e);
        }
    }






}



