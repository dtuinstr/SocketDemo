import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;

    /**
     * Creates an echo server listening on the given port.
     * @param port the port to listen on.
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Starts the echo server.
     * @throws IOException if ServerSocket cannot be created,
     *              its accept() call fails, wrapping it for
     *              IO fails, or actual IO fails.
     */
    public void start() throws IOException {
        try (// Create server socket on local port.
             ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             // Build buffered reader on client socket.
             InputStream inStream = clientSocket.getInputStream();
             InputStreamReader inReader = new InputStreamReader(inStream);
             BufferedReader in = new BufferedReader(inReader);
             // Build PrintWriter on client socket.
             OutputStream outStream = clientSocket.getOutputStream();
             OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
             PrintWriter out = new PrintWriter(outWriter, true) // true: autoflush
        ) {
            out.println("Echo server. Type text to echo; or just Enter to exit.");
            String inString = in.readLine();
            while (!inString.isEmpty()) {
                out.println("Echo: \"" + inString + "\"");
                inString = in.readLine();
            }
            out.println("Server exiting. Good bye.");
        }
    }
}
