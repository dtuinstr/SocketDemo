import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This is a simple client class for exchanging character (text)
 * information with a server. The exchanges are line-based: user
 * input is sent to the server when the user hits ENTER at the
 * end of a line of input. The server's response is assumed to be
 * a single line of text, which is printed when received.
 */
public class Client
{
    // %s:%d renders as hostname:port
    private static final String START_FMT =
            "Attempting connection to %s:%d";
    private static final String PROMPT_FMT = "%s:%d> ";
    private static final String END_FMT =
            "Connection to %s:%d closed, exiting."; //

    private final String hostname;
    private final int port;

    /**
     * Creates a client for character-based exchanges with a server.
     *
     * @param hostname the hostname of the server.
     * @param port     the service's port on the server.
     */
    public Client(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Starts this client, connecting to the server and port that
     * it was given when constructed.
     *
     * @throws UnknownHostException if hostname is not resolvable.
     * @throws IOException          if socket creation, wrapping, or IO fails.
     */
    public void start() throws UnknownHostException, IOException
    {
        System.out.format(START_FMT, hostname, port);
        Scanner keyboard = new Scanner(System.in);

        try (
                // Create client socket on local port.
                Socket socket = new Socket(hostname, port);
                // Build buffered reader on client socket.
                BufferedReader inReader =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                // Build PrintWriter on client socket.
                PrintWriter outWriter =
                        new PrintWriter(socket.getOutputStream(), true)
        )
        {
            String userInput;
            System.out.println(inReader.readLine());    // Get server hello.
            do {
                System.out.format(PROMPT_FMT, hostname, port);
                userInput = keyboard.nextLine();
                outWriter.println(userInput);
                System.out.println(inReader.readLine());
            } while (!userInput.isEmpty());

        }   // Streams and sockets closed by try-with-resources
        System.out.format(END_FMT, hostname, port);
    }
}

