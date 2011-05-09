package network;

import game.Player;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ServerCli extends Thread {
    private InputStream in;
    private PrintStream out;
    private DedicatedServer server;

    /*
     * Constructor with an input and output stream so we can use over the network
     */
    public ServerCli(DedicatedServer server, OutputStream out, InputStream in) {
        this(server, new PrintStream(out), in);
    }

    public ServerCli(DedicatedServer server, PrintStream out, InputStream in) {
        this.server = server;
        this.in = in;
        this.out = out;
    }

    private void displayHelp(StringTokenizer tokenizer) {
        out.println("Avaliable commands:\n" +
                "\thelp\n" +
                "\tstatus\n" +
                "\tkick <player name>\n" +
                "\tnew - restart map\n" +
                "\tquit");
    }

    private void displayList(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            out.println("usage: list players|actors");
            return;
        }
    }

    private void displayStatus(StringTokenizer tokenizer) {
        long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        long freeMemory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;
        int processors = Runtime.getRuntime().availableProcessors();
        List<Player> players = server.getPlayers();
        int playerCount = players.size();

        out.println(server.getActors().size() + " actors");
        out.println(playerCount + " players");
        for (Player p: players)
            out.println("\t" + p);
        out.println(processors + " processors");
        out.println("Memory:\t" + usedMemory + "MB used\t" + freeMemory + "MB free\t" + totalMemory + "MB total");
    }

    private void kickPlayer(StringTokenizer tokenizer) {
        // TODO
    }

    private void processCliCommand(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        // Don't crash if they entered a blank line
        if (! tokenizer.hasMoreTokens())
            return;

        String token = tokenizer.nextToken();

        switch(tokenType(token)) {
            case QUIT:
                if (out != System.out)
                    out.println("exiting ...");
                System.out.println("exiting ...");
                server.setRunning(false);
                break;
            case KICK:
                kickPlayer(tokenizer);
                break;
            case STATUS:
                displayStatus(tokenizer);
                break;
            case LIST:
                displayList(tokenizer);
                break;
            default:
                displayHelp(tokenizer);
                break;
        }
    }

    public void run() {
        /* Run our little CLI */
        Scanner kbd = new Scanner(in);

        out.println("Server started");

        while (server.isRunning()) {
            out.print("server# ");
            try {
                processCliCommand(kbd.nextLine());
            } catch (Exception e) {
                out.println("Exception: " + e);
                e.printStackTrace(out);
            }
        }
    }

    private ServerCommand tokenType(String token) {
        if (token.equalsIgnoreCase("quit"))
            return ServerCommand.QUIT;
        if (token.equalsIgnoreCase("help"))
            return ServerCommand.HELP;
        if (token.equalsIgnoreCase("exit"))
            return ServerCommand.QUIT;
        if (token.equalsIgnoreCase("status"))
            return ServerCommand.STATUS;
        if (token.equalsIgnoreCase("kick"))
            return ServerCommand.KICK;
        if (token.equalsIgnoreCase("new"))
            return ServerCommand.NEWMAP;
        if (token.equalsIgnoreCase("list"))
            return ServerCommand.LIST;
        if (token.equalsIgnoreCase("ls"))
            return ServerCommand.LIST;

        return ServerCommand.UNKNOWN;
    }

    private enum ServerCommand {
        HELP,
        KICK,
        LIST,
        NEWMAP,
        QUIT,
        STATUS,
        UNKNOWN,
    }
}
