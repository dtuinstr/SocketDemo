import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Main program. Based on command-line arguments (see USAGE
 * string), creates and starts either a client or a server.
 * To use, create a single server on a given host and port.
 * Clients (possibly multiple) can then be created, each in
 * their own terminal/command window. The client can accept
 * "localhost" as a server name. If a client cannot connect
 * to a server on a remote host, check firewall settings on
 * the host.
 * <p>
 * The usage message assumes this program has been built as
 * a jar file. IDEs vary in how they do this; check the IDE
 * documentation.
 */
public class SocketDemo
{
    private final static String USAGE =
            "Usage: java -jar SocketDemo.jar client <server name> <server port>\n"
                    + "       java -jar SocketDemo.jar server <server port>";

    public static void main(String[] args)
    {
//        System.out.println("Command-line arguments: "
//                + Arrays.toString(args));

        if (args.length < 2) {
            System.err.println(USAGE);
        } else if (args.length == 2
                && args[0].equalsIgnoreCase("server"))
        {
            try {
                Server server = new Server(args[1]);
                server.start();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (args.length == 3
                && args[0].equalsIgnoreCase("client"))
        {
            String hostArg = args[1];
            String portArg = args[2];
            try {
                Client client = new Client(hostArg, Integer.parseInt(portArg));
                client.start();
            } catch (NumberFormatException e) {
                System.err.println("Not an integer: " + portArg);
            } catch (UnknownHostException e) {
                System.err.println("Could not find server \"" + hostArg + "\".");
            } catch (IOException e) {
                System.err.println("IO problem. " + e.getMessage());
            }
        } else {
            System.err.println(USAGE);
        }
    }
}
