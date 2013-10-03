package cyberwaste.kuzoff.core.command;

import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;

public class CommandDropDatabase implements Command {

    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void execute(CommandManager ioManager ) throws Exception {
        databaseManager.dropDatabase();
        ioManager.outputDatabaseDropped(databaseManager.getDatabaseName());
    }

}
