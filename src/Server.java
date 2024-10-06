import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;

    /**
     * Creates an echo server listening on the given port.
     * @port the port to listen on.
     */
    public Server(int port) {
        this.port = port;
    }

    public void start() throws RuntimeException {
        // Create server socket on this.port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Accept a connection and build a buffered reader on it.
            try (Socket clientSocket = serverSocket.accept();
                 // Build buffered reader on client socket
                 InputStream inStream = clientSocket.getInputStream();
                 InputStreamReader inReader = new InputStreamReader(inStream);
                 BufferedReader in = new BufferedReader(inReader);
                 // Build PrintWriter on client socket
                 OutputStream outStream = clientSocket.getOutputStream();
                 OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
                 PrintWriter out = new PrintWriter(outWriter, true); // true: autoflush
            ) {
                String inString = in.readLine();
                while (!inString.isEmpty()) {
                    out.println("Echo: \"" + inString + "\"");
                    inString = in.readLine();
                }
            } catch (IOException e) { // serverSocket.accept()
                throw new RuntimeException("Error accepting connection, "
                        + "getting its input or output stream, "
                        + "or doing IO on it. "
                        + e.getMessage());
            }
        } catch (IOException e) {   // new ServerSocket(port)
            throw new RuntimeException("Could not create ServerSocket. "
                    + e.getMessage());
        }
    }
}
