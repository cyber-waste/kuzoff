package cyberwaste.kuzoff.command;

import java.util.Collection;
import java.util.Map;

import cyberwaste.kuzoff.database.DatabaseManager;
import cyberwaste.kuzoff.database.Table;
import cyberwaste.kuzoff.outputmanager.OutputManager;

public class CommandRunner {
    
    private final DatabaseManager databaseManager;
    private final OutputManager outputManager;
    
    public CommandRunner(String databaseFolder, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.databaseManager = new DatabaseManager(databaseFolder);
    }

    public void runCommand(String commandName, Map<String, String> parameters) {
        if ("lstbl".equals(commandName)) {
            listTables();
        }
        if ("mktbl".equals(commandName)) {
            String tableName = parameters.get("name");
            createTable(tableName);
        }
    }

    private void listTables() {
        Collection<Table> result = databaseManager.listTables();
        outputManager.output(result);
    }

    private void createTable(String tableName) {
        Table result = databaseManager.createTable(tableName);
        outputManager.output(result);
    }
}
