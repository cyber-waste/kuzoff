package cyberwaste.kuzoff.outputmanager;

import cyberwaste.kuzoff.database.Table;

public class ConsoleOutputManager extends OutputManager {
    
    @Override
    protected void outputMessage(String message) {
        System.err.println(message);
    }
    
    @Override
    protected void outputTable(Table table) {
        System.out.println(table.name());
    }
}
