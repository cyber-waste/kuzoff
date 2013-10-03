package cyberwaste.kuzoff.io.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.io.IOManager;

public class CommandAddRow implements Command {
    
    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    public void execute(IOManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        final List<String> columnData = CommandBuilder.getListParameter(parameters,"column");
        Row new_row = databaseManager.addRow(tableName,columnData);
        ioManager.outputRowAdded(new_row);
    }

}
