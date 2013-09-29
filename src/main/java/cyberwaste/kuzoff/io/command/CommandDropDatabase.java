package cyberwaste.kuzoff.io.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.io.IOManager;

public class CommandDropDatabase implements Command {

    private Map<String,String> parameters;
    private DatabaseManager databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(IOManager ioManager ) throws Exception {
        databaseManager.dropDatabase();
        ioManager.outputDatabaseDropped(databaseManager.getDatabaseName());
    }

}
