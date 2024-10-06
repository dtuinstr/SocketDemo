import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private String hostname;
    private int port;

    /**
     * Create a client for exchanging text strings with a
     * service (for example, with an echo server).
     * @param hostname the hostname of the server
     * @param port the service's port on the hostname
     */
    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Starts the echo server and allows the user to
     * communicate with it.
     * @throws UnknownHostException if hostname not resolvable.
     * @throws IOException if socket creation, wrapping, or IO fails.
     */
    public void start() throws UnknownHostException, IOException {
        try (// Create server socket on local port.
             Socket socket = new Socket(hostname, port);
             // Build buffered reader on client socket.
             InputStream inStream = socket.getInputStream();
             InputStreamReader inReader = new InputStreamReader(inStream);
             BufferedReader in = new BufferedReader(inReader);
             // Build PrintWriter on client socket.
             OutputStream outStream = socket.getOutputStream();
             OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
             PrintWriter out = new PrintWriter(outWriter, true); // true: autoflush
        ) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println(in.readLine());
            String userInput = keyboard.nextLine();
            while (!userInput.isEmpty()) {
                out.println(userInput);
                System.out.println(in.readLine());
                userInput = keyboard.nextLine();
            }
            System.out.println("Client exiting.");
        }
    }
}

