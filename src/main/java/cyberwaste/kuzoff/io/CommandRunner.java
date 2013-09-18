package cyberwaste.kuzoff.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

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
        
        else if ("mktbl".equals(commandName)) {
            String tableName = getStringParameter(parameters, "name");
            List<String> columnTypes = getListParameter(parameters, "column");
            
            createTable(tableName, columnTypes);
        }
        
        else if ("rmtbl".equals(commandName)) {
            String tableName = getStringParameter(parameters, "name");
            
            removeTable(tableName);
        }
        
        else if("addrw".equals(commandName)){
            String tableName = getStringParameter(parameters, "name");
            List<String> columnData = getListParameter(parameters,"column");
            
            addRow(tableName, columnData);
        }
        
        else if("rmvrw".equals(commandName)){
            String tableName = getStringParameter(parameters, "name");
            int numColumns;
            try {
                numColumns = databaseManager.loadTable(tableName).columnTypes().size();
                Map<Integer,String> columnData = getMapParameter(parameters, "column", numColumns);
                removeRow(tableName, columnData);
            } catch (IOException e) {
                outputManager.outputError(e);
            }
        }
        
        else if("drpdb".equals(commandName)){   
            dropDatabase();
        }
        
        else if("swtbl".equals(commandName)){
            String tableName = getStringParameter(parameters, "name");
            showTableData(tableName);
        }
        
        else if("untbl".equals(commandName)){
            String tableName1 = getStringParameter(parameters, "name-1");
            String tableName2 = getStringParameter(parameters, "name-2");
            unionTables(tableName1,tableName2);
        }
        
        else{
            outputManager.outputMessage("UNKNOWN OPERATION");
        }
    }
    
    private void unionTables(String tableName1, String tableName2){

        try {
            Table table1 = databaseManager.loadTable(tableName1);
            Table table2 = databaseManager.loadTable(tableName2);
        
            List<Type> types1 = table1.columnTypes();
            List<Type> types2 = table2.columnTypes();
            if(types1.size() != types2.size()){
                outputManager.outputError(new Exception("Tables have defferent schemes"));
                return;
            }
            
            for(int i=0;i<types1.size();i++){
                if(!types1.get(i).name().equals(types2.get(i).name())){
                    outputManager.outputError(new Exception("Tables have different schemes"));
                    return;
                }
            }
            List<String> typeNames = new ArrayList<String>();
            for(int i=0;i<types1.size();i++) typeNames.add(types1.get(i).name());
            
            String newTableName = tableName1+"-"+tableName2+"-union";
            Table newTable = databaseManager.createTable(newTableName, typeNames);
            Set<Row> newRows = new HashSet<Row>();
            for(Row curRow : databaseManager.loadTableData(tableName1)){
                newRows.add(curRow);
            }
            for(Row curRow : databaseManager.loadTableData(tableName2)){
                newRows.add(curRow);
            }
            for(Row curRow : newRows){
                databaseManager.addRow(newTableName, curRow);
            }
            outputManager.outputMessage("NEW TABLE CREATED: " + newTableName);
        } catch (IOException e) {
            outputManager.outputError(e);
        }
    }
    
    private void showTableData(String tableName) {
        try {
            Table result = databaseManager.loadTable(tableName);
            outputManager.outputTable(result);
            List<Row> tableData = databaseManager.loadTableData(tableName);
            for(Row curRow : tableData){
                outputManager.outputRow(curRow);
            }
        } catch (IOException e) {
            outputManager.outputError(e);
        }
    }

    private void dropDatabase() {
        try{
            databaseManager.dropDatabase();
            outputManager.outputMessage("DATABASE " + databaseManager.getDatabaseName() + " deleted");
        }catch (IOException e) {
            outputManager.outputError(e);
        }
        
    }

    private Map<Integer, String> getMapParameter(Map<String,String> parameters, String key, int numColumns){
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        String columnData;
        for (int index = 1;index<=numColumns;index++) {
            columnData = getStringParameter(parameters, key + "-" + index);
            if(columnData != null) result.put(index, columnData);
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
            Table table = databaseManager.loadTable(tableName);
            if(table.columnTypes().size() != params.size()){
                outputManager.outputError(new Exception("Type of row is not valid"));
                return;
            }
            List<Type> types = table.columnTypes();
            for(int i=0;i<params.size();i++){
                if(!types.get(i).isValid(new Value(params.get(i)))){
                    outputManager.outputError(new Exception("Type of row is not valid"));
                    return;
                }
            }
            Row new_row = databaseManager.addRow(tableName,params);
            outputManager.outputRow(new_row);
            
        }catch(IOException e){
            outputManager.outputError(e);
        }
    }
    
    private void removeRow(String tableName, Map<Integer,String> params){
        try{
            Table table = databaseManager.loadTable(tableName);
            List<Type> types = table.columnTypes();
            for(int curKey : params.keySet()){
                if(!types.get(curKey-1).isValid(new Value(params.get(curKey)))){
                    outputManager.outputError(new Exception("Type of row is not valid"));
                    return;
                }
            }
            
            List<Row> rowList = databaseManager.removeRow(tableName,params);
            outputManager.outputMessage("Deleted Rows:");
            for(Row row : rowList) outputManager.outputRow(row);
        }catch(IOException e){
            outputManager.outputError(e);
        }
    }
}
