package cyberwaste.kuzoff.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
            String tableName = getStringParameter(parameters, "name");
            
            if (tableName != null) {
                showTable(tableName);
            } else {
                listTables();
            }
        }
        
        if ("mktbl".equals(commandName)) {
            String tableName = getStringParameter(parameters, "name");
            List<String> columnTypes = getListParameter(parameters, "column");
            
            createTable(tableName, columnTypes);
        }
        
        if ("rmtbl".equals(commandName)) {
            String tableName = getStringParameter(parameters, "name");
            
            removeTable(tableName);
        }
    }

    private List<String> getListParameter(Map<String, String> parameters, String key) {
        List<String> result = new ArrayList<String>();
        
        int index = 1;
        String columnType;
        while ((columnType = getStringParameter(parameters, key + "-" + index)) != null) {
            result.add(columnType);
            index++;
        }
        
        return result;
    }

    private String getStringParameter(Map<String, String> parameters, String key) {
        return parameters.get(key);
    }

    private void listTables() {
        try {
            Collection<Table> result = databaseManager.listTables();
            outputManager.outputListTables(result);
        } catch (IOException e) {
            outputManager.outputError(e);
        }
    }

    private void createTable(String tableName, List<String> columnTypes) {
        try {
            Table result = databaseManager.createTable(tableName, columnTypes);
            outputManager.outputTableCreated(result);
        } catch (IOException e) {
            outputManager.outputError(e);
        }
    }

    private void removeTable(String tableName) {
        try {
            databaseManager.removeTable(tableName);
            outputManager.outputTableRemoved(tableName);
        } catch (IOException e) {
            outputManager.outputError(e);
        }
    }

    private void showTable(String tableName) {
        try {
            Table result = databaseManager.loadTable(tableName);
            outputManager.outputTable(result);
        } catch (IOException e) {
            outputManager.outputError(e);
        }
    }
}
