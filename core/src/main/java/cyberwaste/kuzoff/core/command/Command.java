package cyberwaste.kuzoff.core.command;

import java.util.HashMap;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.CommandManager;

public interface Command {
    void execute(CommandManager ioManager) throws Exception;
    void setState(Map<String,String> parameters, DatabaseManager databaseManager);
}
