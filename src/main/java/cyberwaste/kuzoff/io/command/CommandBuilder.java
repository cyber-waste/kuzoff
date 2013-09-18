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
    
    private String databaseFolder;
    private Map<String, String> parameters;

    public static CommandBuilder command(String commandName) {
        return new CommandBuilder(commandName);
    }

    private CommandBuilder(String commandName) {
        this.commandName = commandName;
    }

    public CommandBuilder forDatabase(String databaseFolder) {
        this.databaseFolder = databaseFolder;
        return this;
    }

    public CommandBuilder withParameters(Map<String, String> parameters) {
        this.parameters = new HashMap<String, String>(parameters);
        return this;
    }

    public Command build() throws IOException {
        if (databaseFolder == null) {
            throw new RuntimeException("Database folder is not specified");
        }
        
        final DatabaseManager databaseManager = new DatabaseManager(databaseFolder);
        
        if ("lstbl".equals(commandName)) {
            final String tableName = getStringParameter(parameters, "name");
            
            if (tableName != null) {
                return new Command() {

                    @Override
                    public void execute(IOManager ioManager) throws IOException {
                        Table result = databaseManager.loadTable(tableName);
                        ioManager.outputTable(result);
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
                    
                    Row new_row = databaseManager.addRow(tableName,columnData);
                    ioManager.outputRow(new_row);
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
                    
                    
                    List<Row> rowList = databaseManager.removeRow(tableName,columnData);
                    ioManager.outputMessage("Deleted Rows:");
                    for(Row row : rowList) ioManager.outputRow(row);
                }
            };
        }
        
        else if("drpdb".equals(commandName)){
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws IOException {
                    databaseManager.dropDatabase();
                    ioManager.outputMessage("DATABASE " + databaseManager.getDatabaseName() + " deleted");
                }
            };
        }
        
        else if("swtbl".equals(commandName)){
            final String tableName = getStringParameter(parameters, "name");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws IOException {
                    Table result = databaseManager.loadTable(tableName);
                    ioManager.outputTable(result);
                    List<Row> tableData = databaseManager.loadTableData(tableName);
                    for(Row curRow : tableData){
                        ioManager.outputRow(curRow);
                    }
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
                    ioManager.outputTable(newTable);
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
                    ioManager.outputTable(newTable);
                }
            };
        }
        
        else if("uqtbl".equals(commandName)){
            final String tableName = getStringParameter(parameters, "name");
            
            return new Command() {

                @Override
                public void execute(IOManager ioManager) throws Exception {
                    Table newTable = databaseManager.uniqueTable(tableName);
                    ioManager.outputTable(newTable);
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
