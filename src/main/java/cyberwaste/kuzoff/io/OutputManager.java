package cyberwaste.kuzoff.io;

import java.util.Collection;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public abstract class OutputManager {

    public void outputListTables(Collection<Table> tables) {
        outputMessage("Found " + tables.size() + " tables:");
        
        for (Table table : tables) {
            outputTable(table);
        }
    }

    public void outputTableCreated(Table table) {
        outputMessage("Table created:");
        outputTable(table);
    }

    public void outputTableRemoved(String tableName) {
        outputMessage("Table " + tableName + " removed");
    }

    public void outputError(Exception e) {
        outputMessage("ERROR: "+ e.getMessage());
    }
    
    public void outputRow(Row row) {
        outputMessage("ROW: " + row.toString());
        //empty
        
    }
    
    public abstract void outputTable(Table table);
    public abstract void outputMessage(String message);
}
