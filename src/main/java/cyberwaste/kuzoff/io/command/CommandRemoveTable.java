package cyberwaste.kuzoff.io.command;

import java.io.IOException;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.io.IOManager;

public class CommandRemoveTable implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        databaseManager.removeTable(tableName);
        ioManager.outputTableRemoved(tableName);
    }

}
