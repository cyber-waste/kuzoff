package cyberwaste.kuzoff.core.command;

import java.util.Map;
import java.util.List;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;
import cyberwaste.kuzoff.core.domain.Row;

public class CommandRemoveRow implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(CommandManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        int numColumns = databaseManager.loadTable(tableName).getColumnTypes().size();
        final Map<Integer,String> columnData = CommandBuilder.getMapParameter(parameters, "column", numColumns);
        List<Row> rowList = databaseManager.removeRow(tableName,columnData);
        ioManager.outputRowDeleted(rowList);
    }

}
