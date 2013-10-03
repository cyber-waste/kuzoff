package cyberwaste.kuzoff.core.command;

import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;

public class CommandUnknown implements Command {

    public void setState(Map<String,String> parameters, DatabaseManager databaseManager) { }
    
    @Override
    public void execute(CommandManager ioManager) throws Exception {
        throw new Exception("Unknown command");
    }
}
