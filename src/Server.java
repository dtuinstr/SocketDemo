import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class Server
{
    // For strings sent to client.
    private static final String GREETING =
            "[Server listening. Type just ENTER to close connection.]";
    private static final String GOOD_BYE = "[Closing connection, good-bye.]";

    // Object variables.
    private final int port;

    /**
     * Creates a server for character-based exchanges.
     *
     * @param port the port to listen on.
     * @throws IllegalArgumentException if port not in range [1024, 49151].
     */
    public Server(int port)
            throws IllegalArgumentException
    {
        if (port < 1024 || port > 49151) {
            throw new IllegalArgumentException(
                    "Port " + port + " not in range 1024-49151.");
        }
        this.port = port;
    }

    /**
     * Starts this server, listening on the port it was
     * constructed with.
     *
     * @throws IOException if ServerSocket creation, connection
     *                     acceptance, wrapping, or IO fails.
     */
    public void start() throws IOException
    {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server starting on port " + port + ".");
            System.out.println("Ctrl + C to exit.");
            while (true) {
                try (
                        // Wait for connection.
                        Socket clientSocket = serverSocket.accept();
                        // Build buffered reader on client socket.
                        BufferedReader inReader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                clientSocket.getInputStream()));
                        // Build PrintWriter on client socket.
                        PrintWriter outWriter =
                                new PrintWriter(clientSocket.getOutputStream(),
                                        true)
                )
                {
                    // Connection made. Greet client.
                    outWriter.println(GREETING);
                    // Converse with client.
                    String inString = inReader.readLine();
                    while (inString != null && !inString.isEmpty()) {
                        System.out.println(inString);   // DEBUG
                        outWriter.println("Echo: \"" + inString + "\"");
                        inString = inReader.readLine();
                    }
                    outWriter.println(GOOD_BYE);
                    System.out.println("Client terminated connection.");
                }   // Streams closed by try-with-resources.
            }
        } // Socket closed by try-with-resources.
    }
}
