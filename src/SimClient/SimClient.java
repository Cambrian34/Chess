package SimClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public SimClient(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void startInteraction() throws IOException {
        Scanner scanner = new Scanner(System.in);

        String userInput;
        while ((userInput = scanner.nextLine()) != null) {
            out.println(userInput);
            String serverResponse = in.readLine();
            System.out.println("Server response: " + serverResponse);
        }
    }

    public void stopClient() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

//    public static void main(String[] args) throws IOException {
//        SimClient client = new SimClient("localhost", 8080); // Adjust address and port
//        client.startInteraction();
//        client.stopClient();
//    }
}
