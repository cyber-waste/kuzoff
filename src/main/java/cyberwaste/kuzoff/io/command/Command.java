package cyberwaste.kuzoff.io.command;

import cyberwaste.kuzoff.io.IOManager;

public interface Command {

    void execute(IOManager ioManager) throws Exception;
}
