package network;

import game.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import jline.ConsoleReader;
import actor.Actor;


public class ServerCli extends Thread {
    private InputStream in;
    private PrintWriter out;
    private DedicatedServer server;

    /*
     * Constructor with an input and output stream so we can use over the network
     */
    public ServerCli(DedicatedServer server, OutputStream out, InputStream in) {
        this(server, new PrintWriter(out), in);
    }

    public ServerCli(DedicatedServer server, PrintWriter out, InputStream in) {
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

    /*
     * Print all currently running threads
     * based on http://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
     */
    private void displayThreads() {
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null )
            rootGroup = parentGroup;

        Thread[] threads = new Thread[rootGroup.activeCount()];
        while (rootGroup.enumerate(threads, true) == threads.length)
            threads = new Thread[threads.length * 2];

        out.println("Name\t\t\t\t\tPrior\tState");
        for (int i = 0; i < threads.length && threads[i] != null; i ++) {
            Thread t = threads[i];
            String line = t.getName();
            
            while (line.length() < 40)
                line += " ";
            
            line += t.getPriority() + "\t" + t.getState();
            
            out.println(line);
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

    private void displayActors() {
        for (Actor a: server.getActors())
            out.println(a);
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
                out.println("exiting ...");
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
            case THREADS:
                displayThreads();
                break;
            case ACTORS:
                displayActors();
                break;
            default:
                displayHelp(tokenizer);
                break;
        }
    }

    public void run() {
        /* Run our little CLI */
        ConsoleReader reader;
                 
        try {
            reader = new ConsoleReader(in, out);
        } catch (IOException e) {
            out.println("Unable to initialize console.");
            e.printStackTrace(out);
            return;
        }
        
        reader.addCompletor (new jline.SimpleCompletor (new String [] { "quit", "help", "exit", "status", "kick", "new", "list", "list", "threads", "ps", "actors" }));

        out.println("Server started");

        while (server.isRunning()) {
            try {
                processCliCommand(reader.readLine("server# "));
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
        if (token.equalsIgnoreCase("threads"))
            return ServerCommand.THREADS;
        if (token.equalsIgnoreCase("ps"))
            return ServerCommand.THREADS;
        if (token.equalsIgnoreCase("actors"))
            return ServerCommand.ACTORS;


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
        THREADS, ACTORS,
    }
}
