package cyberwaste.kuzoff.core.command;

import java.util.Map;
import java.util.List;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public class CommandUniqueTable implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(CommandManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        Table newTable = databaseManager.uniqueTable(tableName);
        List<Row> tableData = databaseManager.loadTableData(newTable.name());
        ioManager.outputTableData(newTable, tableData);
    }

}
