package cyberwaste.kuzoff.io.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.io.IOManager;

public class CommandListTables implements Command {
    
    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        
        if (tableName != null) {
            Table result = databaseManager.loadTable(tableName);
            ioManager.outputTableInfo(result);
        } else {
            Collection<Table> result = databaseManager.listTables();
            ioManager.outputListTables(result);
        }

    }

}
