package cyberwaste.kuzoff.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.database.DatabaseManager;
import cyberwaste.kuzoff.database.Row;
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
        
        if("addrw".equals(commandName)){
            String tableName = getStringParameter(parameters, "name");
            List<String> columnData = getListParameter(parameters,"column");
            
            addRow(tableName, columnData);
        }
        
        if("rmvrw".equals(commandName)){
            String tableName = getStringParameter(parameters, "name");
            Map<Integer,String> columnData = getMapParameter(parameters, "column");
            
            removeRow(tableName, columnData);
        }
    }
    
    private Map<Integer, String> getMapParameter(Map<String,String> parameters, String key){
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        int index = 1;
        String columnData;
        while ((columnData = getStringParameter(parameters, key + "-" + index)) != null) {
            result.put(index, columnData);
            index++;
        }
        
        return result;
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
    
    private void addRow(String tableName, List<String> params){
        try{
            //Table table = databaseManager.loadTable(tableName);
            // check for types
            Row new_row = databaseManager.addRow(tableName,params);
            outputManager.outputRow(new_row);
            
        }catch(IOException e){
            outputManager.outputError(e);
        }
    }
    
    private void removeRow(String tableName, Map<Integer,String> params){
        
    }
}
