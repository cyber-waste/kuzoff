package cyberwaste.kuzoff.io.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;
import cyberwaste.kuzoff.io.IOManager;

public class CommandBuilder {
    
    private final String commandName;
    
    private DatabaseManager databaseManager;
    private Map<String, String> parameters;

    public static CommandBuilder command(String commandName) {
        return new CommandBuilder(commandName);
    }

    private CommandBuilder(String commandName) {
        this.commandName = commandName;
    }
    
    public CommandBuilder usingDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        return this;
    }

    public CommandBuilder forDatabase(String databaseFolder) {
        this.databaseManager.forDatabaseFolder(databaseFolder);
        return this;
    }

    public CommandBuilder withParameters(Map<String, String> parameters) {
        this.parameters = new HashMap<String, String>(parameters);
        return this;
    }

    public Command build() throws IOException {
        if (databaseManager.getDatabaseName() == null) {
            throw new RuntimeException("Database folder is not specified");
        }
        
        if ("lstbl".equals(commandName)) {
            final String tableName = getStringParameter(parameters, "name");
            
            if (tableName != null) {
                return new Command() {

                    @Override
                    public void execute(IOManager ioManager) throws IOException {
                        Table result = databaseManager.loadTable(tableName);
                        ioManager.outputTableInfo(result);
                    }
                };
            } else {
                return new Command() {

                    @Override
                    public void execute(IOManager ioManager) throws IOException {
                        Collection<Table> result = databaseManager.listTables();
                        ioManager.outputListTables(result);
                    }
                };
            }
        }
        
        else if ("mktbl".equals(commandName)) {
            final String tableName = getStringParameter(parameters, "name");
            final List<String> columnTypes = getListParameter(parameters, "column");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws IOException {
                    Table result = databaseManager.createTable(tableName, columnTypes);
                    ioManager.outputTableCreated(result);
                }
            };
        }
        
        else if ("rmtbl".equals(commandName)) {
            final String tableName = getStringParameter(parameters, "name");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws IOException {
                    databaseManager.removeTable(tableName);
                    ioManager.outputTableRemoved(tableName);
                }
            };
        }
        
        else if("addrw".equals(commandName)){
            final String tableName = getStringParameter(parameters, "name");
            final List<String> columnData = getListParameter(parameters,"column");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    Table table = databaseManager.loadTable(tableName);
                    if(table.columnTypes().size() != columnData.size()){
                        throw new Exception("Type of row is not valid");
                    }
                    List<Type> types = table.columnTypes();
                    for(int i=0;i<columnData.size();i++){
                        if(!types.get(i).isValid(new Value(columnData.get(i)))){
                            throw new Exception("Type of row is not valid");
                        }
                    }
                    Row new_row = databaseManager.addRow(tableName,columnData);
                    ioManager.outputRowAdded(new_row);
                }
            };
        }
        
        else if("rmvrw".equals(commandName)){
            final String tableName = getStringParameter(parameters, "name");
            int numColumns = databaseManager.loadTable(tableName).columnTypes().size();
            final Map<Integer,String> columnData = getMapParameter(parameters, "column", numColumns);
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    Table table = databaseManager.loadTable(tableName);
                    List<Type> types = table.columnTypes();
                    for(int curKey : columnData.keySet()){
                        if(!types.get(curKey-1).isValid(new Value(columnData.get(curKey)))){
                            throw new Exception("Type of row is not valid");
                        }
                    }
                    
                    List<Row> rowList = databaseManager.removeRow(tableName,columnData);
                    ioManager.outputRowDeleted(rowList);
                }
            };
        }
        
        else if("drpdb".equals(commandName)){
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws IOException {
                    databaseManager.dropDatabase();
                    ioManager.outputDatabaseDropped(databaseManager.getDatabaseName());
                }
            };
        }
        
        else if("swtbl".equals(commandName)){
            final String tableName = getStringParameter(parameters, "name");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws IOException {
                    List<Row> tableData = databaseManager.loadTableData(tableName);
                    Table result = databaseManager.loadTable(tableName);
                    ioManager.outputTableData(result, tableData);
                }
            };
        }
        
        else if("untbl".equals(commandName)){
            final String tableName1 = getStringParameter(parameters, "name-1");
            final String tableName2 = getStringParameter(parameters, "name-2");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    Table newTable = databaseManager.unionTable(tableName1, tableName2);
                    List<Row> tableData = databaseManager.loadTableData(newTable.name());
                    ioManager.outputTableData(newTable, tableData);
                }
            };
        }
        
        else if("dftbl".equals(commandName)){
            final String tableName1 = getStringParameter(parameters, "name-1");
            final String tableName2 = getStringParameter(parameters, "name-2");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    Table newTable = databaseManager.differenceTable(tableName1, tableName2);
                    List<Row> tableData = databaseManager.loadTableData(newTable.name());
                    ioManager.outputTableData(newTable, tableData);
                }
            };
        }
        
        else if("uqtbl".equals(commandName)){
            final String tableName = getStringParameter(parameters, "name");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    Table newTable = databaseManager.uniqueTable(tableName);
                    List<Row> tableData = databaseManager.loadTableData(newTable.name());
                    ioManager.outputTableData(newTable, tableData);
                }
            };
        }
        
        else{
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    throw new Exception("Unknown command");
                }
            };
        }
    }

    private String getStringParameter(Map<String, String> parameters, String key) {
        return parameters.get(key);
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

    private Map<Integer, String> getMapParameter(Map<String,String> parameters, String key, int numColumns){
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        String columnData;
        for (int index = 1;index<=numColumns;index++) {
            columnData = getStringParameter(parameters, key + "-" + index);
            if(columnData != null) result.put(index, columnData);
        }
        
        return result;
    }
}
