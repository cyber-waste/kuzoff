package cyberwaste.kuzoff.command;

import java.util.Map;

import cyberwaste.kuzoff.database.*;
import cyberwaste.kuzoff.outputmanager.*;

public class CommandRunner {
    
    private DatabaseManager manager;
    
    public CommandRunner(String databaseFolder) {
        manager = new DatabaseManager(databaseFolder, new ConsoleOutputManager());
    }

    public void runCommand(String commandName, Map<String, String> parameters) {
        
        if(commandName.equals("listTables"))
            manager.listTables();
        else if(commandName.equals("createTable"))
            manager.createTable(parameters.get("name"));
    }
}
