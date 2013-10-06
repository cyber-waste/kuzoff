package cyberwaste.kuzoff.core.command;

import java.util.Map;
import java.util.List;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public class CommandUnionTables implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(CommandManager ioManager) throws Exception {
        final String tableName1 = CommandBuilder.getStringParameter(parameters, "name-1");
        final String tableName2 = CommandBuilder.getStringParameter(parameters, "name-2"); 
        Table newTable = databaseManager.unionTable(tableName1, tableName2);
        List<Row> tableData = databaseManager.loadTableData(newTable.getName());
        ioManager.outputTableData(newTable, tableData);
    }

}
