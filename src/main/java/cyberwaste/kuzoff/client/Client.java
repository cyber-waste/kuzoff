package cyberwaste.kuzoff.client;

import cyberwaste.kuzoff.client.shell.ShellManager;

public class Client {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Wrong syntax");
            System.exit(1);
        }
        
        String clientName = args[0];
        
        if ("shell".equals(clientName)) {
            ShellManager.main(args);
        } else {
            System.err.println("Unknown client");
            System.exit(1);
        }
    }
}
