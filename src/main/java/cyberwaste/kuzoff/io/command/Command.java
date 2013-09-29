package cyberwaste.kuzoff.io.command;

import java.util.HashMap;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.io.IOManager;

public interface Command {
    void execute(IOManager ioManager) throws Exception;
    void setState(Map<String,String> parameters, DatabaseManager databaseManager);
}
