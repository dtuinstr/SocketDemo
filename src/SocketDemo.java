import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class SocketDemo {
    private final static String USAGE =
            "Usage: java SocketDemo client <server name> <server port>\n"
                    + "       java SocketDemo server <server port>";

    public static void main(String[] args) {
        System.out.println("Starting SocketDemo with arguments "
                + Arrays.toString(args));
        if (args.length < 2) {
            System.err.println(USAGE);
        } else if (args.length == 2
                && args[0].equalsIgnoreCase("server")) {
            try {
                Server server = new Server(Integer.parseInt(args[1]));
                server.start();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else if (args.length == 3
                && args[0].equalsIgnoreCase("client")) {
            try {
                Client client = new Client(args[1], Integer.parseInt(args[2]));
                client.start();
            } catch (UnknownHostException e) {
                System.err.println("Could not find server \"" + args[1] + "\"."
                        + e.getMessage());
            } catch (IOException e) {
                System.err.println("IO problem. " + e.getMessage());
            }
        } else {
            System.err.println(USAGE);
        }
        System.out.println("Ending SocketDemo.");
    }
}
