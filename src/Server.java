import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a simple server class for exchanging character (text)
 * information with a client. The exchanges are line-based: client
 * input is expected to be a newline-terminated string of text.
 * This server processes that line and sends back a single-line
 * response. The connection terminates when the client sends an
 * empty string.
 */
public class Server {
    private static final String GREETING =
            "[Echo server listening. Type just ENTER to close connection.]";
    private static final String OUPUT_LABEL = ""; // "[Echo] ";
    private static final String GOOD_BYE = "[Closing connection, good-bye.]";

    private final int port;
    private final String startMessage;

    /**
     * Creates a server for character-based exchanges.
     * @param port the port to listen on.
     */
    public Server(int port) {
        this.port = port;
        this.startMessage = "Server starting, listening on port " + port
        + ". Ctrl+C to exit.";
    }

    /**
     * Starts this server, listening on the port it was
     * constructed with.
     * @throws IOException if ServerSocket creation, connection
     *      acceptance, wrapping, or IO fails.
     */
    public void start() throws IOException {
        System.out.println(startMessage);
        while (true) {
            try (// Create server socket on local port.
                 ServerSocket serverSocket = new ServerSocket(port);
                 Socket clientSocket = serverSocket.accept();
                 // Build buffered reader on client socket.
                 BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         clientSocket.getInputStream()));
                 // Build PrintWriter on client socket.
                 PrintWriter out =
                         new PrintWriter(clientSocket.getOutputStream(), true);
            ) {
                out.println(GREETING);
                String inString = in.readLine();
                while (inString != null && !inString.isEmpty()) {
                    out.println(OUPUT_LABEL + "\"" + inString + "\"");
                    inString = in.readLine();
                }
                out.println(GOOD_BYE);
            }   // Streams and sockets closed by try-with-resources.
        }
    }
}
