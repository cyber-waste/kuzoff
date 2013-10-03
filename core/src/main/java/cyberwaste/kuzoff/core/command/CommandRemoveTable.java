package cyberwaste.kuzoff.core.command;

import java.io.IOException;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;

public class CommandRemoveTable implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(CommandManager ioManager) throws Exception {
        final String tableName = CommandBuilder.getStringParameter(parameters, "name");
        databaseManager.removeTable(tableName);
        ioManager.outputTableRemoved(tableName);
    }

}
