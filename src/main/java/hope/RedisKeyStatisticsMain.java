package hope;

import hope.connection.RedisConnector;
import org.apache.commons.cli.*;


public class RedisKeyStatisticsMain {
    public static void main(String[] args) {

        Options options = new Options();
        Option hostOption = new Option("h", "host", true, "Redis Server Host");
        Option portOption = new Option("p", "port", true, "Redis Server Port");
        Option passwordOption = new Option("P", "password", true, "Redis Server Password");
        Option versionOption = new Option("v", "version", true, "Check RKS Version");
        Option helpOption = new Option("?", "help", true, "Guide RKS");

        // connect option
        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(passwordOption);

        // version option
        options.addOption(versionOption);

        // help option
        options.addOption(helpOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            if(cmd.hasOption("v")) {
                showVersion();
            } else if(cmd.hasOption("?")) {
                showHelp();
            } else if(cmd.hasOption("h") && cmd.hasOption("p")) {
                String host = cmd.getOptionValue("h");
                String port = cmd.getOptionValue("p");
                String password = cmd.hasOption("P") ? cmd.getOptionValue("P") : null;

                RedisConnector.connect(host, Integer.parseInt(port), password);
            } else {
                System.out.println("RKS : Unknown Command. Please try again.");
            }

        } catch (ParseException e) {
            System.out.println("Error parsing command line: " + e.getMessage());
            printUsage();
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number: " + e.getMessage());
        }
    }

    private static void showVersion() {
        System.out.println("RKS Version is 1.0.0");
    }

    private static void showHelp() {
        System.out.println("Help.......개발중");
    }

    private static void printUsage() {
        System.out.println(": rks --host <REDIS_HOST> --port <REDIS_PORT> [--password <REDIS_PASSWORD>]");
    }

}
