package cyberwaste.kuzoff.io.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.io.IOManager;

public class CommandMakeTable implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        final List<String> columnTypes = CommandBuilder.getListParameter(parameters, "column");
        Table result = databaseManager.createTable(tableName, columnTypes);
        ioManager.outputTableCreated(result);
    }

}
